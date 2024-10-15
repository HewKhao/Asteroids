package se233.asteroids.view;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import se233.asteroids.controller.PlayerShipController;
import se233.asteroids.model.PlayerShip;

public class GameStage extends Pane {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private PlayerShip playerShip;
    private PlayerShipController controller;

    public GameStage() {
        setPrefSize(WIDTH, HEIGHT);
        playerShip = new PlayerShip(300, 200, 1, 1);
        controller = new PlayerShipController(playerShip);

        getChildren().add(playerShip.getNode());

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
