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

        if (event.getCode() == KeyCode.W) {
            playerShip.stopMoveForward();
        }

        if (event.getCode() == KeyCode.A) {
            playerShip.stopMoveLeft();
        }
    }

    public void updateShipMovement() {
        if (pressedKeys.contains(KeyCode.W)) {
            playerShip.moveForward();
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
    }

    public String getCurrentAnimation(){
        return playerShip.getCurrentAnimation();
    }

    public void update() {
        updateShipMovement();
        playerShip.updateShipPosition();
    }
}
