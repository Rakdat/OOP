package ru.nsu.tokarev4;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;
import java.io.File;
import java.net.URL;
import java.util.Map;

public class Config {

    public static int SERVER_PORT = 8080;
    public static int TICK_RATE_MS = 150;

    public static int TICKS_BEFORE_CHANGE = 67;
    public static int TRANSITION_TICKS = 20;
    public static int BLINK_TICKS = 5;

    public static String COLOR_BG = "#1e1e1e";
    public static String COLOR_GRID = "#ffffff";
    public static String COLOR_SNAKE_HEAD = "#27ae60";
    public static String COLOR_SNAKE_BODY = "#2ecc71";
    public static String COLOR_FOOD = "#e74c3c";
    public static String COLOR_OBSTACLE = "#7f8c8d";
    public static String COLOR_OBSTACLE_WARN = "#f39c12";
    public static String COLOR_TAIL_WARN1 = "#ff0000";
    public static String COLOR_TAIL_WARN2 = "#2ecc71";

    public static String TITLE = "Змейка";
    public static String BTN_START = "Старт";
    public static String BTN_RESTART = "Рестарт";
    public static String BTN_SETTINGS = "Настройки";
    public static String GAMEOVER_TEXT = "ИГРА ОКОНЧЕНА! Счет: ";

    static {
        try {
            ConfigObject config = null;

            File externalConfig = new File("config.groovy");

            if (externalConfig.exists()) {
                System.out.println("Найден внешний файл конфигурации! Загружаем...");
                config = new ConfigSlurper().parse(externalConfig.toURI().toURL());
            } else {
                System.out.println("Внешний конфиг не найден. Загружаем стандартный из ресурсов...");
                URL configURL = Config.class.getResource("/config.groovy");
                if (configURL != null) {
                    config = new ConfigSlurper().parse(configURL);
                }
            }

            if (config != null) {
                Map server = (Map) config.get("server");
                SERVER_PORT = (Integer) server.get("port");
                TICK_RATE_MS = (Integer) server.get("tickRateMs");

                Map game = (Map) config.get("game");
                Map logic = (Map) game.get("logic");
                TICKS_BEFORE_CHANGE = (Integer) logic.get("ticksBeforeChange");
                TRANSITION_TICKS = (Integer) logic.get("transitionTicks");
                BLINK_TICKS = (Integer) logic.get("blinkTicks");

                Map colors = (Map) config.get("colors");
                COLOR_BG = (String) colors.get("background");
                COLOR_GRID = (String) colors.get("grid");
                COLOR_SNAKE_HEAD = (String) colors.get("snakeHead");
                COLOR_SNAKE_BODY = (String) colors.get("snakeBody");
                COLOR_FOOD = (String) colors.get("food");
                COLOR_OBSTACLE = (String) colors.get("obstacle");
                COLOR_OBSTACLE_WARN = (String) colors.get("obstacleWarning");
                COLOR_TAIL_WARN1 = (String) colors.get("tailWarning1");
                COLOR_TAIL_WARN2 = (String) colors.get("tailWarning2");

                Map ui = (Map) config.get("ui");
                TITLE = (String) ui.get("title");
                BTN_START = (String) ui.get("buttonStart");
                BTN_RESTART = (String) ui.get("buttonRestart");
                BTN_SETTINGS = (String) ui.get("textSettings");
                GAMEOVER_TEXT = (String) ui.get("textGameOver");

                System.out.println("Конфигурация DSL успешно применена!");
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка чтения DSL. Используются стандартные настройки.");
            e.printStackTrace();
        }
    }
}