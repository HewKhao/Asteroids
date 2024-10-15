package se233.asteroids;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import se233.asteroids.view.MainView;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        Pane mainView = new MainView();
        mainView.getChildren().add(new Circle(30, 50, 10));

        Scene scene = new Scene(mainView);
        stage.setTitle("Asteroids");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
