package ru.nsu.tokarev4;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Серверная часть игры "Змейка".
 * Поднимает TCP-сервер на порту 8080 и ожидает подключения одного клиента.
 * Включает в себя игровой цикл (метроном), который обновляет состояние логической модели (SnakeModel)
 * и отправляет новые координаты клиенту каждые 150 миллисекунд.
 */
public class SnakeServer {
    private static SnakeModel model;
    private static boolean pause = false;

    /**
     * Точка входа для серверного приложения.
     */
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("=== СЕРВЕР ЗАПУЩЕН ===");
            try {
                // Пытаемся узнать локальный IP-адрес компьютера в сети
                String ip = InetAddress.getLocalHost().getHostAddress();
                System.out.println("Твой IP-адрес для друга: " + ip);
            } catch (Exception e) {
                System.out.println("Не удалось определить IP автоматически.");
            }
            System.out.println("Ждем подключения клиента на порту 8080...\n");

            Socket client = server.accept();
            System.out.println("Клиент успешно подключился!");

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Thread listener = new Thread(() -> {
                try {
                    while (true) {
                        String com = in.readLine();
                        if (com == null) break;

                        if (com.startsWith("INIT:")) {
                            String[] parts = com.split(":");
                            int size = Integer.parseInt(parts[1]);
                            int amountOfFood = Integer.parseInt(parts[2]);
                            model = new SnakeModel(size, size, amountOfFood);
                            pause = true;
                            System.out.println("Новая игра создана! Размер: " + size);

                        } else if (com.startsWith("CMD:")) {
                            String[] parts = com.split(":");
                            if (model != null) {
                                try {
                                    model.setDirection(Direction.valueOf(parts[1]));
                                    pause = Boolean.parseBoolean(parts[2]);
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Неверное направление: " + parts[1]);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Связь с клиентом потеряна");
                }
            });
            listener.start();

            while (true) {
                if (!pause && model != null && !model.isGameOver()){
                    model.update();
                }

                if (model != null) {
                    out.println(buildState());
                }
                Thread.sleep(150);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Собирает текущее состояние игры в единую строку для отправки по сети.
     * Формат: STATE;GAMEOVER:false;SCORE:10;DIR:UP;BODY:x,y|x,y;FOOD:x,y|...
     * @return Закодированная строка состояния игрового поля.
     */
    private static String buildState() {
        StringBuilder state = new StringBuilder("STATE;");
        state.append("GAMEOVER:").append(model.isGameOver()).append(";");
        state.append("SCORE:").append(model.getScore()).append(";");
        state.append("DIR:").append(model.getCurrentDirection()).append(";");

        state.append("BODY:");
        for (SnakeModel.Point p : model.getSnakeBody()) {
            state.append(p.x).append(",").append(p.y).append("|");
        }
        state.append(";");

        state.append("FOOD:");
        for (SnakeModel.Point p : model.getFoods()) {
            state.append(p.x).append(",").append(p.y).append("|");
        }
        state.append(";");

        state.append("OBSTACLES:");
        for (SnakeModel.Point p : model.getObstacles()){
            state.append(p.x).append(",").append(p.y).append("|");
        }
        state.append(";");

        state.append("NEXT_OBS:");
        for (SnakeModel.Point p : model.getNextObstacles()) {
            state.append(p.x).append(",").append(p.y).append("|");
        }
        state.append(";");

        state.append("SEVERED:");
        for (SnakeModel.Point p : model.getSeveredBody()) {
            state.append(p.x).append(",").append(p.y).append("|");
        }
        state.append(";");

        return state.toString();
    }
}