package se233.asteroids;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.controller.NormalAttackController;
import se233.asteroids.controller.SpecialAttackController;
import se233.asteroids.model.NormalAttack;
import se233.asteroids.model.PlayerShip;
import se233.asteroids.view.GameStage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PlayerShipTest {
    private PlayerShip playerShip;
    private GameStage gameStage;
    private GameStageController gameStageController;
    private NormalAttackController normalAttackController;
    private SpecialAttackController specialAttackController;

    @BeforeEach
    public void setUp() {

        gameStage = Mockito.mock(GameStage.class);
        Mockito.when(gameStage.getWidthValue()).thenReturn(800);
        Mockito.when(gameStage.getHeightValue()).thenReturn(600);
        Mockito.when(gameStage.getChildren()).thenReturn(FXCollections.observableArrayList());

        gameStageController = Mockito.mock(GameStageController.class);
        normalAttackController = Mockito.mock(NormalAttackController.class);
        specialAttackController = Mockito.mock(SpecialAttackController.class);

        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStageController.getNormalAttackController()).thenReturn(normalAttackController);
        Mockito.when(gameStageController.getSpecialAttackController()).thenReturn(specialAttackController);

        double centerX = (double) gameStage.getWidthValue() / 2;
        double centerY = (double) gameStage.getHeightValue() / 2;

        playerShip = new PlayerShip(centerX, centerY, 1, 5,  0.2, 3.0, 0.99, 1, gameStage.getWidthValue(), gameStage.getHeightValue(),gameStageController);
        playerShip.setRotate(-90);
    }

    @Test
    public void testMoveForward(){
        double initialY = playerShip.getY();

        playerShip.moveForward();
        playerShip.update();

        assertTrue(playerShip.getY() < initialY);
    }

    @Test
    public void testMoveBackward(){
        double initialY = playerShip.getY();

        playerShip.moveBackward();
        playerShip.update();

        assertTrue(playerShip.getY() > initialY);
    }

    @Test
    public void testMoveLeft(){
        double initialX = playerShip.getX();

        playerShip.moveLeft();
        playerShip.update();

        assertTrue(playerShip.getX() < initialX);
    }

    @Test
    public void testMoveRight(){
        double initialX = playerShip.getX();

        playerShip.moveRight();
        playerShip.update();

        assertTrue(playerShip.getX() > initialX);
    }

    @Test
    public void testRotateLeft() {
        double initialRotation = playerShip.getRotate();

        playerShip.rotate(-15);

        assertEquals(initialRotation - 15, playerShip.getRotate());
    }

    @Test
    public void testRotateRight() {
        double initialRotation = playerShip.getRotate();

        playerShip.rotate(15);

        assertEquals(initialRotation + 15, playerShip.getRotate());
    }
    @Test
    public void testShoot() {
        playerShip.timeSinceLastShot = 0.5;

        playerShip.shoot(gameStageController);

        verify(normalAttackController, times(1)).addNormalAttack(any());
        assertEquals(0, playerShip.timeSinceLastShot, "Shoot cooldown should reset after shooting.");
    }

    @Test
    public void testSpecialShoot() {
        playerShip.timeSinceLastSpecialShot = 2.0;

        playerShip.specialShoot(gameStageController);

        verify(specialAttackController, times(1)).addSpecialAttack(any());
        assertEquals(0, playerShip.timeSinceLastSpecialShot, "Special shoot cooldown should reset after special shooting.");
    }

    @Test
    public void testRandomWarp() {
        playerShip.timeSinceLastWarp = 6.0;

        double initialX = playerShip.getX();
        double initialY = playerShip.getY();

        playerShip.randomWarp();

        assertNotEquals(initialX, playerShip.getX(), "X position should change after warp.");
        assertNotEquals(initialY, playerShip.getY(), "Y position should change after warp.");
        assertTrue(playerShip.getX() >= 0 && playerShip.getX() <= 800, "Warped X should be within stage bounds.");
        assertTrue(playerShip.getY() >= 0 && playerShip.getY() <= 600, "Warped Y should be within stage bounds.");
        assertEquals(0, playerShip.timeSinceLastWarp, "Warp cooldown should reset after warping.");
    }

    @Test
    public void testCollided() {
        playerShip.timeSinceShield = 1.0;
        playerShip.isDestroyed = false;
        int initialLives = playerShip.getLives();

        playerShip.collided();

        assertEquals(initialLives - 1, playerShip.getLives(), "Lives should decrease after collision.");
        assertTrue(playerShip.isDestroyed(), "PlayerShip should be marked as destroyed after collision.");
    }

    @Test
    public void testRespawn() {
        playerShip.respawn(100, 100);

        assertEquals(100, playerShip.getX(), "Player X should reset to respawn point.");
        assertEquals(100, playerShip.getY(), "Player Y should reset to respawn point.");
        assertEquals(0, playerShip.velocityX, "VelocityX should reset to 0 on respawn.");
        assertEquals(0, playerShip.velocityY, "VelocityY should reset to 0 on respawn.");
        assertFalse(playerShip.isDestroyed(), "PlayerShip should not be destroyed after respawning.");
    }
}
