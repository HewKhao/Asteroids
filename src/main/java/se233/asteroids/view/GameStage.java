package se233.asteroids.view;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import se233.asteroids.controller.PlayerShipController;
import se233.asteroids.model.PlayerShip;

public class GameStage extends Pane {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private PlayerShip playerShip;
    private PlayerShipController controller;

    public GameStage() {
        setPrefSize(WIDTH, HEIGHT);
        double centerX = (double) WIDTH / 2;
        double centerY = (double) HEIGHT / 2;

        playerShip = new PlayerShip(centerX, centerY, 1, 1, WIDTH, HEIGHT);
        controller = new PlayerShipController(playerShip);

        getChildren().add(playerShip.getImageView());

        setOnKeyPressed(event -> {
            controller.handleKeyPressed(event);
        });

        setOnKeyReleased(event -> {
            controller.handleKeyReleased(event);
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.update();
            }
        }.start();
    }
}
