package se233.asteroids.view;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import se233.asteroids.Launcher;
import se233.asteroids.controller.PlayerShipController;
import se233.asteroids.model.PlayerShip;

public class GameStage extends Pane {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private PlayerShip playerShip;
    private PlayerShipController controller;

    private long lastUpdateTime = System.nanoTime();

    public GameStage() {
        setPrefSize(WIDTH, HEIGHT);

        Image backgroundImage = new Image(Launcher.class.getResourceAsStream("/se233/asteroids/assets/background/background.png"));

        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        setBackground(new Background(bgImage));

        double centerX = (double) WIDTH / 2;
        double centerY = (double) HEIGHT / 2;

        playerShip = new PlayerShip(centerX, centerY, 1, 1, WIDTH, HEIGHT);
        playerShip.setRotate(-90);
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
