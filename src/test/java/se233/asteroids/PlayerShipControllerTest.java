package se233.asteroids;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.controller.PlayerShipController;
import se233.asteroids.model.PlayerShip;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerShipControllerTest {
    private PlayerShip playerShip;
    private PlayerShipController playerShipController;
    private GameStageController gameStageController;

    @BeforeEach
    public void setUp() {
        playerShip = mock(PlayerShip.class);
        gameStageController = mock(GameStageController.class);
        playerShipController = new PlayerShipController(playerShip, gameStageController);
    }

    @Test
    public void testMoveForward() {
        KeyEvent keyEventW = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);
        playerShipController.handleKeyPressed(keyEventW);
        playerShipController.updateShipMovement();

        verify(playerShip, times(1)).moveForward();
    }
    @Test
    public void testMoveBackward() {
        KeyEvent keyEventS = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false);
        playerShipController.handleKeyPressed(keyEventS);
        playerShipController.updateShipMovement();

        verify(playerShip, times(1)).moveBackward();
    }
    @Test
    public void testMoveLeft() {
        KeyEvent keyEventA = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);
        playerShipController.handleKeyPressed(keyEventA);
        playerShipController.updateShipMovement();

        verify(playerShip, times(1)).moveLeft();
    }
    @Test
    public void testMoveRight() {
        KeyEvent keyEventD = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.D, false, false, false, false);
        playerShipController.handleKeyPressed(keyEventD);
        playerShipController.updateShipMovement();

        verify(playerShip, times(1)).moveRight();
    }
    @Test
    public void testRotateLeft() {
        KeyEvent keyEventQ = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.Q, false, false, false, false);
        playerShipController.handleKeyPressed(keyEventQ);
        playerShipController.updateShipMovement();

        verify(playerShip, times(1)).rotate(-playerShip.getRotationSpeed());
    }
    @Test
    public void testRotateRight() {
        KeyEvent keyEventE = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.E, false, false, false, false);
        playerShipController.handleKeyPressed(keyEventE);
        playerShipController.updateShipMovement();

        verify(playerShip, times(1)).rotate(playerShip.getRotationSpeed());
    }
    @Test
    public void testRandomWarp() {
        KeyEvent keyEventShift = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SHIFT, false, false, false, false);
        playerShipController.handleKeyPressed(keyEventShift);
        playerShipController.updateShipMovement();

        verify(playerShip, times(1)).randomWarp();
    }

}
