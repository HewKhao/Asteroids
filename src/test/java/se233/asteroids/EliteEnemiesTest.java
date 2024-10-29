package se233.asteroids;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se233.asteroids.controller.EliteAttackController;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.model.EliteEnemies;
import se233.asteroids.model.PlayerShip;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EliteEnemiesTest {

    private EliteEnemies eliteEnemy;
    @Mock
    private GameStageController gameStageController;
    private PlayerShip playerShip;

    private final double WIDTH = 50;
    private final double HEIGHT = 50;
    private final double INITIAL_SPEED = 1;
    private final double MAX_SPEED = 2;
    private final double ACCELERATION = 0.05;
    private final double ROTATION_SPEED = 2;
    private final double FRICTION = 0.98;
    private final int HEALTH = 3;
    private final double GAME_WIDTH = 800;
    private final double GAME_HEIGHT = 600;

    @BeforeEach
    public void setUp() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);

        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);
    }

    @Test
    public void testInitialHealth() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        assertEquals(HEALTH, eliteEnemy.getHealth(), "Initial health should match assigned value");
    }

    @Test
    public void testIsDeadInitially() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        assertFalse(eliteEnemy.isDead(), "EliteEnemies should not be dead initially");
    }

    @Test
    public void testCollidedWithPlayer() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        eliteEnemy.timeSinceLastHit = 1; // Bypass iframe
        eliteEnemy.collided(true);

        assertEquals(HEALTH - 1, eliteEnemy.getHealth(), "Health should decrease by 1 after collision with player");
    }

    @Test
    public void testDeathTransitionAfterHealthDepletion() {
        // Mock dependencies
        gameStageController = mock(GameStageController.class);

        // Initialize PlayerShip at the center
        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        // Initialize EliteEnemies with health and outline for collision detection
        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        // Deplete health and check health decrement at each step
        for (int i = 0; i < HEALTH; i++) {
            eliteEnemy.timeSinceLastHit = 1; // Bypass iframe each time
            eliteEnemy.collided(true);

            int expectedHealth = HEALTH - i - 1;
            assertEquals(expectedHealth, eliteEnemy.getHealth(), "Health should decrease by 1 after each collision");
        }

        // Verify that the enemy is dead after health is depleted
        assertTrue(eliteEnemy.isDead(), "EliteEnemies should be dead after health depletion");
    }


    @Test
    public void testMarkForRemoveWhenDead() {
        // Mock dependencies
        gameStageController = mock(GameStageController.class);

        // Initialize PlayerShip at the center
        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        // Initialize EliteEnemies with health and outline for collision detection
        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        // Iterate to deplete health and check health decrement at each step
        for (int i = 0; i < HEALTH; i++) {  // Changed to i < HEALTH
            eliteEnemy.timeSinceLastHit = 1; // Bypass iframe
            eliteEnemy.collided(true);

            int expectedHealth = HEALTH - i - 1;
            assertEquals(expectedHealth, eliteEnemy.getHealth(), "Health should decrease by 1 after each collision, expected: " + expectedHealth);
        }

        // Verify that the enemy is dead and marked for removal after all health is depleted
        assertTrue(eliteEnemy.isDead(), "EliteEnemies should be dead after health is depleted");
        assertTrue(eliteEnemy.isMarkForRemove(), "EliteEnemies should be marked for removal when dead");
    }


    @Test
    public void testSpawnPosition() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        eliteEnemy.spawn();
        double x = eliteEnemy.getX();
        double y = eliteEnemy.getY();
        assertTrue(x >= -100 && x <= GAME_WIDTH + 100, "X coordinate should be within reasonable spawn bounds");
        assertTrue(y >= -100 && y <= GAME_HEIGHT + 100, "Y coordinate should be within reasonable spawn bounds");
    }

    @Test
    public void testRandomWarp() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        eliteEnemy.randomWarp();
        double x = eliteEnemy.getX();
        double y = eliteEnemy.getY();
        assertTrue(x >= 0 && x <= GAME_WIDTH, "X coordinate should be within game bounds after warp");
        assertTrue(y >= 0 && y <= GAME_HEIGHT, "Y coordinate should be within game bounds after warp");
    }

    @Test
    public void testShootCooldown() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        EliteAttackController eliteAttackControllerMock = mock(EliteAttackController.class);
        when(gameStageController.getEliteAttackController()).thenReturn(eliteAttackControllerMock);

        eliteEnemy.timeSinceLastShot = 1;
        eliteEnemy.shoot(gameStageController);

        verify(eliteAttackControllerMock, atLeastOnce()).addEliteAttack(any());
    }

    @Test
    public void testCheckPlayerShipCollision_NoCollision() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        eliteEnemy.setX(100);
        eliteEnemy.setY(100);
        assertFalse(eliteEnemy.checkPlayerShipCollision(playerShip), "Collision should not occur when player is away from enemy");
    }

    @Test
    public void testCheckPlayerShipCollision_WithCollision() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        playerShip.setX(eliteEnemy.getX());
        playerShip.setY(eliteEnemy.getY());
        assertTrue(eliteEnemy.checkPlayerShipCollision(playerShip), "Collision should occur when player intersects with enemy");
    }

    @Test
    public void testUpdateIncreasesTimeSinceLastHit() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        double initialTime = eliteEnemy.timeSinceLastHit;
        eliteEnemy.update();
        assertTrue(eliteEnemy.timeSinceLastHit > initialTime, "Time since last hit should increase on update");
    }

    @Test
    public void testUpdateIncreasesTimeSinceLastShot() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        double initialTime = eliteEnemy.timeSinceLastShot;
        eliteEnemy.update();
        assertTrue(eliteEnemy.timeSinceLastShot > initialTime, "Time since last shot should increase on update");
    }

    @Test
    public void testRemoveIfMarkedForRemoval() {
        gameStageController = mock(GameStageController.class);

        double centerX = GAME_WIDTH / 2;
        double centerY = GAME_HEIGHT / 2;
        playerShip = new PlayerShip(centerX, centerY, 1, 5, 0.2, 3.0, 0.99, 1, GAME_WIDTH, GAME_HEIGHT, gameStageController);
        playerShip.setRotate(-90);

        eliteEnemy = new EliteEnemies(WIDTH, HEIGHT, INITIAL_SPEED, MAX_SPEED, ACCELERATION, ROTATION_SPEED, FRICTION, HEALTH, GAME_WIDTH, GAME_HEIGHT);
        eliteEnemy.outline = new Rectangle(WIDTH, HEIGHT);

        eliteEnemy.markForRemove();
        assertTrue(eliteEnemy.isMarkForRemove(), "EliteEnemies should be marked for removal when `markForRemove` is called");
    }
}


