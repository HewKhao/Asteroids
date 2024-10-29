package se233.asteroids;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.controller.PlayerShipController;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.PlayerShip;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerShipControllerTest {

    private PlayerShip playerShip;
    private PlayerShipController playerShipController;
    private GameStageController gameStageController;
    private ImageView mockImageView;

    @BeforeEach
    public void setUp() {
        playerShip = mock(PlayerShip.class);
        gameStageController = mock(GameStageController.class);
        playerShipController = new PlayerShipController(playerShip, gameStageController);

        // Set up mock ImageView and Bounds
        mockImageView = mock(ImageView.class);
        Bounds mockBounds = mock(Bounds.class);

        // Mock methods in Bounds to prevent NullPointerException
        when(mockBounds.getMinX()).thenReturn(0.0);
        when(mockBounds.getMinY()).thenReturn(0.0);
        when(mockBounds.getWidth()).thenReturn(50.0);
        when(mockBounds.getHeight()).thenReturn(50.0);

        // Attach the mock ImageView and its bounds to the player ship
        when(playerShip.getImageView()).thenReturn(mockImageView);
        when(mockImageView.getBoundsInParent()).thenReturn(mockBounds);
    }

    @Test
    public void testHandleKeyPressed_MovementKeys() {
        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).moveForward();

        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).moveBackward();

        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).moveLeft();

        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.D, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).moveRight();
    }

    @Test
    void testRotateLeftAndRight() {
        when(playerShip.getRotationSpeed()).thenReturn(10.0);

        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.Q, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).rotate(-10.0);

        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.E, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).rotate(10.0);
    }

    @Test
    void testHandleKeyPressed_ActionKeys() {
        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).shoot(gameStageController);

        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SHIFT, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).randomWarp();

        playerShipController.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.R, false, false, false, false));
        playerShipController.updateShipMovement();
        verify(playerShip, times(1)).specialShoot(gameStageController);
    }


}
