package se233.asteroids;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import se233.asteroids.view.GameStage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        Pane mainView = new GameStage();

        Scene scene = new Scene(mainView);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        mainView.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
