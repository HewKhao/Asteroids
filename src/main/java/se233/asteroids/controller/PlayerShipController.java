package se233.asteroids.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.PlayerShip;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerShipController {
    private PlayerShip playerShip;
    private Set<KeyCode> pressedKeys;
    private GameStageController gameStageController;

    public PlayerShipController(PlayerShip playerShip, GameStageController gameStageController) {
        this.playerShip = playerShip;
        this.gameStageController = gameStageController;
        this.pressedKeys = new HashSet<>();
    }

    public void handleKeyPressed(KeyEvent event) {
        pressedKeys.add(event.getCode());
    }

    public void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    public void updateShipMovement() {
        if (pressedKeys.contains(KeyCode.W)) {
            playerShip.moveForward();
        } else {
            playerShip.stopMoveForward();
        }
        if (pressedKeys.contains(KeyCode.S)) {
            playerShip.moveBackward();
        }
        if (pressedKeys.contains(KeyCode.A)) {
            playerShip.moveLeft();
        }
        if (pressedKeys.contains(KeyCode.D)) {
            playerShip.moveRight();
        }
        if (pressedKeys.contains(KeyCode.Q)) {
            playerShip.rotate(-playerShip.getRotationSpeed());
        }
        if (pressedKeys.contains(KeyCode.E)) {
            playerShip.rotate(playerShip.getRotationSpeed());
        }
        if (pressedKeys.contains(KeyCode.SPACE)) {
            playerShip.shoot(gameStageController);
        }
    }

    public void updateAnimationVisibility() {
        Map<String, AnimatedSprite> animations = playerShip.getAnimations();
        List<String> currentAnimations = playerShip.getCurrentAnimations();

        for (Map.Entry<String, AnimatedSprite> entry : animations.entrySet()) {
            String animationKey = entry.getKey();
            AnimatedSprite animation = entry.getValue();
            animation.setVisible(currentAnimations.contains(animationKey));

            if (animation.isVisible()) {
                animation.tick();
            }
        }
    }

    private void updateAnimationPositions() {
        double x = playerShip.getX() - 50;
        double y = playerShip.getY() - 50;
        double rotation = playerShip.getRotate();

        double offsetX = 0;
        double offsetY = 0;

        Map<String, AnimatedSprite> animations = playerShip.getAnimations();
        Map<String, double[]> offsets = playerShip.getAnimationOffsets();
        Map<String, Double> rotates = playerShip.getAnimationRotates();
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

            if (rotates.containsKey(key)) {
                rotation += rotates.get(key);
            }

            sprite.setX(x + offsetX);
            sprite.setY(y + offsetY);
            sprite.setRotate(rotation);
            offsetX = 0;
            offsetY = 0;
            rotation = playerShip.getRotate();
        }
    }

    public void update() {
        updateShipMovement();
        playerShip.update();
        updateAnimationVisibility();
        updateAnimationPositions();
    }
}
