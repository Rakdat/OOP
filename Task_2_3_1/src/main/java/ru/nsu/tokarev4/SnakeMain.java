package ru.nsu.tokarev4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Главный класс клиента игры "Змейка".
 * Является точкой входа для JavaFX приложения. Отвечает за загрузку
 * графического интерфейса из файла FXML и настройку главного окна программы.
 */
public class SnakeMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/snake_view.fxml"));
        Parent root = loader.load();
        SnakeController controller = loader.getController();

        Scene scene = new Scene(root, 600, 500);

        scene.setOnKeyPressed(event -> {
            controller.handleKeyPress(event.getCode().toString());
        });

        primaryStage.setTitle("Змейка");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(400);  // Минимальная ширина
        primaryStage.setMinHeight(500); // Минимальная высота

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}