package se233.asteroids.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.NormalAttack;
import se233.asteroids.model.PlayerShip;
import se233.asteroids.view.GameStage;

import java.util.Map;

public class GameStageController {
    private PlayerShip playerShip;
    private PlayerShipController playerShipController;
    private NormalAttack normalAttack;

    public GameStageController(GameStage gameStage) {
        double centerX = (double) gameStage.getWidthValue() / 2;
        double centerY = (double) gameStage.getHeightValue() / 2;

        this.playerShip = new PlayerShip(centerX, centerY, 5,  0.2, 3.0, 0.99, 1, gameStage.getWidthValue(), gameStage.getHeightValue());
        this.playerShip.setRotate(-90);
        this.playerShipController = new PlayerShipController(playerShip);

        this.normalAttack = new NormalAttack(playerShip.getX(), playerShip.getY() - 50, 5, 0.3, 0.99, gameStage.getWidthValue(), gameStage.getHeightValue());
        this.normalAttack.setRotate(-90);

        gameStage.getChildren().addAll(
                playerShip.getAnimations().get("idle"),
                playerShip.getAnimations().get("boost"),
                playerShip.getAnimations().get("shoot")
        );

        gameStage.getChildren().add(
                normalAttack.getImageView()
        );
    }

    public void update() {
        playerShipController.update();
        updateSpritePositions();
    }

    private void updateSpritePositions() {
        double x = playerShip.getX() - 50;
        double y = playerShip.getY() - 50;
        double rotation = playerShip.getRotate();

        double offsetX = 0;
        double offsetY = 0;

        Map<String, AnimatedSprite> animations = playerShip.getAnimations();
        Map<String, double[]> offsets = playerShip.getAnimationOffsets();
        for (Map.Entry<String, AnimatedSprite> entry : animations.entrySet()) {
            String key = entry.getKey();
            AnimatedSprite sprite = entry.getValue();

            // If broken fix it yourself I forgot how trigonometry work
            if (offsets.containsKey(key)) {
                double[] offset = offsets.get(key);
                double distanceX = offset[0];
                double distanceY = offset[1];

                double radians = Math.toRadians(rotation);
                offsetX = distanceX * Math.cos(radians) - distanceY * Math.sin(radians);
                offsetY = distanceX * Math.sin(radians) + distanceY * Math.cos(radians);
            }

            sprite.setX(x + offsetX);
            sprite.setY(y + offsetY);
            sprite.setRotate(rotation);
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        playerShipController.handleKeyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        playerShipController.handleKeyReleased(event);
    }

    public void startGameLoop() {
        final double frameRate = 60.0;
        final double interval = 1000 / frameRate;

        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(interval), event -> {
            update();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

//    public void startGameLoop() {
//        final double frameRate = 60.0;
//        final long interval = (long) (1_000_000_000 / frameRate);
//
//        AnimationTimer gameLoop = new AnimationTimer() {
//            private long lastUpdate = 0;
//
//            @Override
//            public void handle(long now) {
//                if (now - lastUpdate >= interval) {
//                    update();
//                    lastUpdate = now;
//                }
//            }
//        };
//
//        gameLoop.start();
//    }
}