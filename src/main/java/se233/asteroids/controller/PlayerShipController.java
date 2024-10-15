package se233.asteroids.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import se233.asteroids.model.PlayerShip;

import java.util.HashSet;
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
        updateShipMovement();
    }

    public void handleKeyReleased(KeyEvent event) {
        pressedKeys.remove(event.getCode());
    }

    public void updateShipMovement() {
        if (pressedKeys.contains(KeyCode.UP) || pressedKeys.contains(KeyCode.W)) {
            playerShip.moveForward();
        }
        if (pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A)) {
            playerShip.rotate(-5);
        }
        if (pressedKeys.contains(KeyCode.RIGHT) || pressedKeys.contains(KeyCode.D)) {
            playerShip.rotate(5);
        }
    }

    public void update() {
        updateShipMovement();
        playerShip.updateShipPosition();
    }
}
