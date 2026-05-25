package ru.nsu.tokarev4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Главная логическая модель игры "Змейка".
 * Отвечает за математику, перемещение объектов, генерацию еды и препятствий.
 * Не содержит никакой графики или сетевого кода.
 */
public class SnakeModel {
    private final int columns;
    private final int rows;
    /**
     * Сколько еды должно быть
     */
    private final int targetFoodCount;
    /**
     * Динамическое количество камней
     */
    private final int obstaclesCount;

    private final LinkedList<Point> snakeBody = new LinkedList<>();
    private final List<Point> foods = new ArrayList<>();

    private final List<Point> obstacles = new ArrayList<>();
    private final List<Point> nextObstacles = new ArrayList<>();

    private final List<Point> severedBody = new ArrayList<>();
    private int severedBlinkTicks = Config.BLINK_TICKS;

    private Direction currentDirection = Direction.RIGHT;
    private Direction lastMovedDirection = Direction.RIGHT;
    private boolean isGameOver = false;

    private int tickCounter = 0;
    private boolean isTransitioning = false;
    private static final int TICKS_BEFORE_CHANGE = Config.TICKS_BEFORE_CHANGE;
    private static final int TICKS_TRANSITION = Config.TRANSITION_TICKS;

    /**
     * Создает новую игровую сессию.
     * @param columns   Ширина игрового поля в клетках.
     * @param rows      Высота игрового поля в клетках.
     * @param foodCount Количество еды, которое всегда должно быть на поле.
     */
    public SnakeModel(int columns, int rows, int foodCount) {
        this.columns = columns;
        this.rows = rows;
        this.targetFoodCount = foodCount;

        // Считаем камни: 4% от площади поля (но не меньше 0)
        this.obstaclesCount = Math.max(0, (int)(columns * rows * 0.04));

        snakeBody.add(new Point(columns / 2, rows / 2));

        generateObstacles(obstacles);
        for (int i = 0; i < targetFoodCount; i++) spawnFood();
    }

    /**
     * Генерирует случайные препятствия на поле, следя за тем,
     * чтобы они не появились на змее или еде.
     * @param targetList Список, в который будут добавлены сгенерированные камни.
     */
    private void generateObstacles(List<Point> targetList) {
        targetList.clear();
        Random rand = new Random();
        for (int i = 0; i < obstaclesCount; i++) { // Используем вычисленное значение
            Point obs;
            do {
                obs = new Point(rand.nextInt(columns), rand.nextInt(rows));
            } while (snakeBody.contains(obs) || targetList.contains(obs) || obstacles.contains(obs) ||
                    foods.contains(obs) ||
                    (Math.abs(obs.x - snakeBody.getFirst().x) < 4 && Math.abs(obs.y - snakeBody.getFirst().y) < 4));
            targetList.add(obs);
        }
    }

    /**
     * Выполняет один логический шаг (тик) игры.
     * Двигает змею, проверяет столкновения со стенами, хвостом и препятствиями,
     * а также управляет фазами смены камней на поле.
     */
    public void update() {
        if (isGameOver) return;

        Point head = snakeBody.getFirst();
        Point nextHead = new Point(head.x, head.y);

        switch (currentDirection) {
            case UP -> nextHead.y--;
            case DOWN -> nextHead.y++;
            case LEFT -> nextHead.x--;
            case RIGHT -> nextHead.x++;
        }

        if (nextHead.x < 0 || nextHead.x >= columns || nextHead.y < 0 || nextHead.y >= rows || obstacles.contains(nextHead)) {
            isGameOver = true;
            return;
        }

        Point eatenFood = null;
        for (Point f : foods) {
            if (f.x == nextHead.x && f.y == nextHead.y) {
                eatenFood = f;
                break;
            }
        }
        boolean ateFood = (eatenFood != null);

        if (!ateFood) snakeBody.removeLast();

        if (snakeBody.contains(nextHead)) {
            isGameOver = true;
            return;
        }

        snakeBody.addFirst(nextHead);

        if (ateFood) {
            foods.remove(eatenFood);
            spawnFood();
        }

        lastMovedDirection = currentDirection;

        //ЛОГИКА СМЕНЫ КАМНЕЙ И ОТРЕЗАНИЯ ХВОСТА
        tickCounter++;
        if (!isTransitioning && tickCounter >= TICKS_BEFORE_CHANGE) {
            isTransitioning = true;
            generateObstacles(nextObstacles);
        } else if (isTransitioning && tickCounter >= (TICKS_BEFORE_CHANGE + TICKS_TRANSITION)) {
            isTransitioning = false;
            tickCounter = 0;
            obstacles.clear();
            obstacles.addAll(nextObstacles);
            nextObstacles.clear();

            // Ищем, какое звено первым попало в камень
            int cutIndex = -1;
            for (int i = 0; i < snakeBody.size(); i++) {
                if (obstacles.contains(snakeBody.get(i))) {
                    cutIndex = i;
                    break;
                }
            }

            if (cutIndex == 0) {
                // В камень попала ГОЛОВА - это смерть
                isGameOver = true;
            } else if (cutIndex > 0) {
                // В камень попало ТЕЛО - отрезаем всё, что было за ним (включая его самого)
                severedBody.clear();
                severedBody.addAll(snakeBody.subList(cutIndex, snakeBody.size())); // Сохраняем для мигания
                snakeBody.subList(cutIndex, snakeBody.size()).clear(); // Удаляем из живой змеи

                severedBlinkTicks = 5; // Устанавливаем таймер мигания (~750 миллисекунд)
            }
        }

        // Обновляем таймер мигания отрезанного хвоста
        if (severedBlinkTicks > 0) {
            severedBlinkTicks--;
            if (severedBlinkTicks == 0) {
                severedBody.clear(); // Полностью удаляем останки
            }
        }
    }


    private void spawnFood() {
        Random rand = new Random();
        Point newFood;
        do {
            newFood = new Point(rand.nextInt(columns), rand.nextInt(rows));
        } while (snakeBody.contains(newFood) || obstacles.contains(newFood) || nextObstacles.contains(newFood) || foods.contains(newFood));
        foods.add(newFood);
    }

    /**
     * Устанавливает новое направление движения змеи.
     * Блокирует попытки развернуться на 180 градусов (например, пойти ВЛЕВО, если шли ВПРАВО).
     * @param dir Новое направление из Enum {@link Direction}.
     */
    public void setDirection(Direction dir) {
        if (lastMovedDirection == Direction.UP && dir == Direction.DOWN) return;
        if (lastMovedDirection == Direction.DOWN && dir == Direction.UP) return;
        if (lastMovedDirection == Direction.LEFT && dir == Direction.RIGHT) return;
        if (lastMovedDirection == Direction.RIGHT && dir == Direction.LEFT) return;
        this.currentDirection = dir;
    }

    // Геттеры
    public LinkedList<Point> getSnakeBody() { return snakeBody; }
    public List<Point> getFoods() { return foods; }
    public List<Point> getObstacles() { return obstacles; }
    public List<Point> getNextObstacles() { return nextObstacles; }
    public List<Point> getSeveredBody() { return severedBody; }
    public int getSeveredBlinkTicks() { return severedBlinkTicks; }
    public boolean isTransitioning() { return isTransitioning; }
    public Direction getCurrentDirection() { return currentDirection; }
    public boolean isGameOver() { return isGameOver; }
    public int getScore() { return snakeBody.size() - 1; }

    /**
     * Вспомогательный класс для хранения координат на поле.
     */
    public static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Point p)) return false;
            return this.x == p.x && this.y == p.y;
        }
    }

    /**
     * Перечисление возможных направлений движения змейки на игровом поле.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
}