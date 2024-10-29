package se233.asteroids;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se233.asteroids.controller.EnemiesAttackController;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.model.NormalEnemies;
import se233.asteroids.model.PlayerShip;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NormalEnemiesTest {
    private NormalEnemies normalEnemy;
    @Mock
    private GameStageController gameStageController;
    @Mock
    private EnemiesAttackController enemiesAttackController;

    @BeforeEach
    public void setUp() {
        gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);
    }

    @Test
    public void testRandomSpawn() {
        gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);

        normalEnemy.randomSpawn();
        double x = normalEnemy.getX();
        double y = normalEnemy.getY();

        assertTrue(x >= -100 && x <= 900);
        assertTrue(y >= -100 && y <= 700);
    }

    @Test
    public void testInitializeRandomDirection() {
        gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);

        normalEnemy.initializeRandomDirection();
        assertNotEquals(0, normalEnemy.getVelocityX());
        assertNotEquals(0, normalEnemy.getVelocityY());
    }

    @Test
    public void testMarkForRemove() {gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);

        assertFalse(normalEnemy.isMarkForRemove());
        normalEnemy.markForRemove();
        assertTrue(normalEnemy.isMarkForRemove());
    }

    @Test
    public void testCheckPlayerShipCollision() {
        gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);

        PlayerShip mockPlayerShip = mock(PlayerShip.class);
        ImageView mockImageView = mock(ImageView.class);
        when(mockPlayerShip.getImageView()).thenReturn(mockImageView);

        Bounds enemyBounds = normalEnemy.getImageView().getBoundsInParent();
        Bounds playerBounds = mockPlayerShip.getImageView().getBoundsInParent();
        when(mockImageView.getBoundsInParent()).thenReturn(playerBounds);

        boolean collided = normalEnemy.checkPlayerShipCollision(mockPlayerShip);

        assertFalse(collided, "The enemy and player ship should not collide in this configuration");
    }

    @Test
    public void testShootCooldown() {
        gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);

        normalEnemy.timeSinceLastShot = 0;

        normalEnemy.shoot(gameStageController);
        assertTrue(normalEnemy.getTimeSinceLastShot() < 0.5, "Enemy should not be able to shoot again before cooldown.");

        normalEnemy.timeSinceLastShot = normalEnemy.shootCooldown;

        normalEnemy.shoot(gameStageController);
        assertTrue(normalEnemy.getTimeSinceLastShot() < 0.5, "Enemy should be able to shoot after cooldown.");

        verify(enemiesAttackController, atLeastOnce()).addEnemiesAttack(any());
    }

    @Test
    public void testDeathAfterCollisionWithPlayer() {
        gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);

        assertFalse(normalEnemy.isDead());
        normalEnemy.collided(true);

        assertTrue(normalEnemy.isDead());
        assertEquals(1, NormalEnemies.getAmountDeath());
    }

    @Test
    public void testUpdateMovement() {
        gameStageController = mock(GameStageController.class);
        enemiesAttackController = mock(EnemiesAttackController.class);

        when(gameStageController.getEnemiesAttackController()).thenReturn(enemiesAttackController);

        normalEnemy = new NormalEnemies(30, 30, 2, 3, 0.1, 1, 0.98, 100, 800, 600);

        double initialX = normalEnemy.getX();
        double initialY = normalEnemy.getY();

        normalEnemy.update();

        assertNotEquals(initialX, normalEnemy.getX(), "Enemy X position should change after update.");
        assertNotEquals(initialY, normalEnemy.getY(), "Enemy Y position should change after update.");
    }
}

