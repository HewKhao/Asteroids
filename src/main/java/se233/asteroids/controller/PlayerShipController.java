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

    public PlayerShipController(PlayerShip playerShip) {
        this.playerShip = playerShip;
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
            playerShip.shoot();
        } else {
            playerShip.stopShooting();
        }
    }

    public void updateAnimationVisibility() {
        Map<String, AnimatedSprite> animations = playerShip.getAnimations();
        List<String> currentAnimations = playerShip.getCurrentAnimations();

        for (Map.Entry<String, AnimatedSprite> entry : animations.entrySet()) {
            String animationKey = entry.getKey();
            AnimatedSprite animation = entry.getValue();
            animation.setVisible(currentAnimations.contains(animationKey));
        }

        if (currentAnimations.contains("boost")) {
            animations.get("boost").tick();
        }
        if (currentAnimations.contains("shoot")) {
            animations.get("shoot").tick();
        }
    }

    public void update() {
        updateShipMovement();
        playerShip.updateShipPosition();
        updateAnimationVisibility();
    }
}
