package ru.nsu.tokarev4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер графического интерфейса пользователя (GUI).
 * Отвечает за отрисовку игрового поля, меню настроек, таймеров отсчета,
 * а также за сетевое взаимодействие с сервером (отправка нажатий клавиш и прием координат).
 * Работает как "глупый монитор": сам ничего не считает, только рисует то, что скажет сервер.
 */
public class SnakeController {

    @FXML private StackPane canvasContainer;
    @FXML private Canvas gameCanvas;
    @FXML private VBox settingsBox;
    @FXML private TextField ipInput;
    @FXML private TextField mapSizeInput;
    @FXML private TextField foodCountInput;
    @FXML private Label scoreLabel;
    @FXML private Button actionButton;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private int columns = 20;
    private int rows = 20;
    private boolean isGameOver = false;
    private int currentScore = 0;
    private Direction currentDirection = Direction.RIGHT;

    private List<SnakeModel.Point> snakeBody = new ArrayList<>();
    private List<SnakeModel.Point> foods = new ArrayList<>();
    private List<SnakeModel.Point> obstacles = new ArrayList<>();
    private List<SnakeModel.Point> nextObstacles = new ArrayList<>();
    private List<SnakeModel.Point> severedBody = new ArrayList<>();

    private enum State { SETTINGS, PLAYING, GAME_OVER, PAUSED, COUNTDOWN }
    private State gameState = State.SETTINGS;

    public void initialize() {
        canvasContainer.widthProperty().addListener((obs, oldVal, newVal) -> resizeCanvas());
        canvasContainer.heightProperty().addListener((obs, oldVal, newVal) -> resizeCanvas());
        drawBlankGrid();
    }

    private void resizeCanvas() {
        double size = Math.min(canvasContainer.getWidth(), canvasContainer.getHeight());
        gameCanvas.setWidth(size);
        gameCanvas.setHeight(size);
        if (gameState == State.PLAYING || gameState == State.GAME_OVER || gameState == State.PAUSED) {
            drawGame();
        } else {
            drawBlankGrid();
        }
    }

    /**
     * Обрабатывает нажатие кнопки "СТАРТ" / "РЕСТАРТ".
     * Считывает параметры из полей ввода и инициирует подключение к серверу.
     */
    @FXML
    private void handleActionButton() {
        if (gameState == State.SETTINGS || gameState == State.GAME_OVER) {
            int foodCount;
            try {
                columns = Math.max(10, Integer.parseInt(mapSizeInput.getText()));
                rows = columns;
                foodCount = Math.max(1, Integer.parseInt(foodCountInput.getText()));
            } catch (NumberFormatException e) {
                columns = 20; rows = 20; foodCount = 5;
            }

            String ipAddress = ipInput.getText().trim();
            if (ipAddress.isEmpty()) ipAddress = "127.0.0.1";

            settingsBox.setVisible(false);
            actionButton.setDisable(true);
            isGameOver = false;

            try {
                if (out == null) {
                    socket = new Socket(ipAddress, 8080);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println("INIT:" + columns + ":" + foodCount);
                    startServerListener();
                } else {
                    out.println("INIT:" + columns + ":" + foodCount);
                }

                // Сразу запускаем красивый отсчет перед стартом
                startCountdown();

            } catch (Exception e) {
                System.out.println("Не удалось подключиться к серверу по IP: " + ipAddress);
                e.printStackTrace();
                settingsBox.setVisible(true);
                actionButton.setDisable(false);
            }
        }
    }

    /**
     * Запускает фоновый поток для прослушивания входящих сообщений от сервера.
     * Расшифровывает полученную строку (STATE;...) и отдает команду на перерисовку поля.
     */
    private void startServerListener() {
        Thread listener = new Thread(() -> {
            try {
                while (true) {
                    String line = in.readLine();
                    if (line == null) break;

                    if (line.startsWith("STATE;")) {
                        try {
                            String[] info = line.split(";");
                            for (String part : info) {
                                if (part.startsWith("GAMEOVER:")) isGameOver = Boolean.parseBoolean(part.split(":")[1]);
                                else if (part.startsWith("SCORE:")) currentScore = Integer.parseInt(part.split(":")[1]);
                                else if (part.startsWith("DIR:")) currentDirection = Direction.valueOf(part.split(":")[1]);
                                else if (part.startsWith("BODY:")) snakeBody = parsePoints(part);
                                else if (part.startsWith("FOOD:")) foods = parsePoints(part);
                                else if (part.startsWith("OBSTACLES:")) obstacles = parsePoints(part);
                                else if (part.startsWith("NEXT_OBS:")) nextObstacles = parsePoints(part);
                                else if (part.startsWith("SEVERED:")) severedBody = parsePoints(part);
                            }

                            Platform.runLater(() -> {
                                if (isGameOver && gameState != State.GAME_OVER) {
                                    showGameOverScreen();
                                } else if (gameState == State.PLAYING) {
                                    scoreLabel.setText("Счет: " + currentScore);
                                    drawGame();
                                }
                            });
                        } catch (Exception e) {
                            System.out.println("Ошибка парсинга: " + line);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Отключено от сервера");
            }
        });
        listener.setDaemon(true);
        listener.start();
    }

    /**
     * Вспомогательный метод для парсинга списка координат из текстовой строки.
     * @param data Строка формата "ПРЕФИКС:x,y|x,y|x,y|"
     * @return Список объектов Point с восстановленными координатами.
     */
    private List<SnakeModel.Point> parsePoints(String data) {
        List<SnakeModel.Point> result = new ArrayList<>();
        String[] parts = data.split(":");
        if (parts.length < 2) return result;

        String[] pairs = parts[1].split("\\|");
        for (String pair : pairs) {
            if (pair.isEmpty()) continue;
            String[] coords = pair.split(",");
            if (coords.length < 2) continue;
            result.add(new SnakeModel.Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
        }
        return result;
    }

    /**
     * Обрабатывает нажатие клавиш на клавиатуре.
     * @param code Строковый код нажатой клавиши (например, "W", "UP", "SPACE").
     */
    public void handleKeyPress(String code) {
        if (out == null) return;

        switch (code) {
            case "W", "UP" -> { if (gameState == State.PLAYING) out.println("CMD:UP:false"); }
            case "S", "DOWN" -> { if (gameState == State.PLAYING) out.println("CMD:DOWN:false"); }
            case "A", "LEFT" -> { if (gameState == State.PLAYING) out.println("CMD:LEFT:false"); }
            case "D", "RIGHT" -> { if (gameState == State.PLAYING) out.println("CMD:RIGHT:false"); }
            case "SPACE", "P", "ESCAPE" -> {
                if (gameState == State.PLAYING) {
                    gameState = State.PAUSED;
                    out.println("CMD:" + currentDirection + ":true"); // Отправляем сервер на паузу
                    drawGame();
                    drawBigText("ПАУЗА");
                } else if (gameState == State.PAUSED) {
                    startCountdown(); // Запускаем отсчет перед снятием с паузы
                }
            }
        }
    }

    private void startCountdown() {
        gameState = State.COUNTDOWN;

        final int[] count = {3};
        Timeline countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (count[0] > 0) {
                drawGame();
                drawBigText(String.valueOf(count[0]));
                count[0]--;
            } else {
                gameState = State.PLAYING;
                out.println("CMD:" + currentDirection + ":false"); // Снимаем сервер с паузы
            }
        }));
        countdownTimeline.setCycleCount(3);

        drawGame();
        drawBigText("3");
        count[0]--;
        countdownTimeline.play();
    }

    private void showGameOverScreen() {
        gameState = State.GAME_OVER;
        actionButton.setText("РЕСТАРТ");
        actionButton.setDisable(false);
        settingsBox.setVisible(true);
        scoreLabel.setText("ИГРА ОКОНЧЕНА! Счет: " + currentScore);
        scoreLabel.setTextFill(Color.web("#e74c3c"));
    }

    private void drawBlankGrid() {
        if (gameCanvas.getWidth() == 0) return;
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.web("#1e1e1e"));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    private void drawBigText(String text) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.web("#000000", 0.5));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("System Bold", 100));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, gameCanvas.getWidth() / 2, gameCanvas.getHeight() / 2 + 30);
    }

    private void drawGame() {
        if (gameCanvas.getWidth() == 0 || gameCanvas.getHeight() == 0 || snakeBody.isEmpty()) return;

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        double cellW = gameCanvas.getWidth() / columns;
        double cellH = gameCanvas.getHeight() / rows;

        gc.setFill(Color.web("#1e1e1e"));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setStroke(Color.web("#ffffff", 0.05));
        gc.setLineWidth(1);
        for (int i = 0; i <= columns; i++) gc.strokeLine(i * cellW, 0, i * cellW, gameCanvas.getHeight());
        for (int i = 0; i <= rows; i++) gc.strokeLine(0, i * cellH, gameCanvas.getWidth(), i * cellH);

        // Постоянные камни
        gc.setFill(Color.web("#7f8c8d"));
        for (SnakeModel.Point obs : obstacles) {
            gc.fillRect(obs.x * cellW, obs.y * cellH, cellW - 1, cellH - 1);
        }

        // Будущие камни (Мигающие оранжевым)
        gc.setFill(Color.web("#f39c12", 0.8));
        for (SnakeModel.Point obs : nextObstacles) {
            if (System.currentTimeMillis() % 400 < 200) {
                gc.fillRect(obs.x * cellW, obs.y * cellH, cellW - 1, cellH - 1);
            }
        }

        // Оторванный хвост (Мигающий красно-зеленым)
        for (SnakeModel.Point p : severedBody) {
            if (System.currentTimeMillis() % 200 < 100) gc.setFill(Color.RED);
            else gc.setFill(Color.web("#2ecc71", 0.5));
            gc.fillOval(p.x * cellW, p.y * cellH, cellW - 1, cellH - 1);
        }

        // Еда
        gc.setFill(Color.web("#e74c3c"));
        for (SnakeModel.Point food : foods) {
            gc.fillRoundRect(food.x * cellW + 2, food.y * cellH + 2, cellW - 4, cellH - 4, 15, 15);
        }

        // Тело змеи
        gc.setFill(Color.web("#2ecc71"));
        for (int i = 1; i < snakeBody.size(); i++) {
            gc.fillOval(snakeBody.get(i).x * cellW, snakeBody.get(i).y * cellH, cellW - 1, cellH - 1);
        }

        // Голова змеи
        SnakeModel.Point head = snakeBody.get(0);
        gc.setFill(Color.web("#27ae60"));
        gc.fillOval(head.x * cellW, head.y * cellH, cellW - 1, cellH - 1);

        // Глаза
        gc.setFill(Color.BLACK);
        double eyeSize = cellW / 5;
        double headX = head.x * cellW;
        double headY = head.y * cellH;

        switch (currentDirection) {
            case RIGHT -> {
                gc.fillOval(headX + cellW * 0.6, headY + cellH * 0.15, eyeSize, eyeSize);
                gc.fillOval(headX + cellW * 0.6, headY + cellH * 0.55, eyeSize, eyeSize);
            }
            case LEFT -> {
                gc.fillOval(headX + cellW * 0.2, headY + cellH * 0.15, eyeSize, eyeSize);
                gc.fillOval(headX + cellW * 0.2, headY + cellH * 0.55, eyeSize, eyeSize);
            }
            case UP -> {
                gc.fillOval(headX + cellW * 0.2, headY + cellH * 0.2, eyeSize, eyeSize);
                gc.fillOval(headX + cellW * 0.6, headY + cellH * 0.2, eyeSize, eyeSize);
            }
            case DOWN -> {
                gc.fillOval(headX + cellW * 0.2, headY + cellH * 0.6, eyeSize, eyeSize);
                gc.fillOval(headX + cellW * 0.6, headY + cellH * 0.6, eyeSize, eyeSize);
            }
        }
    }
}