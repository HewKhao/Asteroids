package se233.asteroids;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.model.Asteroid;
import se233.asteroids.model.Character;
import se233.asteroids.model.PlayerShip;
import se233.asteroids.view.GameStage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AsteroidTest {

    private Asteroid asteroid;
    @Mock
    private GameStageController gameStageController;
    @Mock
    private GameStage gameStage;

    @BeforeAll
    public static void initJFX() {
        CompletableFuture<Void> startUpLatch = new CompletableFuture<>();
        Platform.startup(() -> startUpLatch.complete(null));
        try {
            startUpLatch.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        // Mock the GameStage and GameStageController
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);

        // Create an instance of Asteroid
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight()
        );
    }

    @Test
    public void testRandomSpawn() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        asteroid.randomSpawn();
        assertTrue(asteroid.getX() >= -100 && asteroid.getX() <= gameStage.getWidth() + 100, "X position should be within spawn bounds.");
        assertTrue(asteroid.getY() >= -100 && asteroid.getY() <= gameStage.getHeight() + 100, "Y position should be within spawn bounds.");
    }

    @Test
    public void testInitializeRandomDirection() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        asteroid.initializeRandomDirection();
        assertTrue(Math.abs(asteroid.getVelocityX()) <= asteroid.getMaxSpeed(), "VelocityX should be within max speed limits.");
        assertTrue(Math.abs(asteroid.getVelocityY()) <= asteroid.getMaxSpeed(), "VelocityY should be within max speed limits.");
    }

    @Test
    public void testMarkForRemove() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        assertFalse(asteroid.isMarkForRemove(), "Asteroid should not be marked for removal initially.");
        asteroid.markForRemove();
        assertTrue(asteroid.isMarkForRemove(), "Asteroid should be marked for removal after calling markForRemove().");
    }

    @Test
    public void testCheckCharacterCollision_NoCollision() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        Character anotherAsteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        anotherAsteroid.setX(500);
        anotherAsteroid.setY(500);

        asteroid.setX(100);
        asteroid.setY(100);

        assertFalse(asteroid.checkCharacterCollision(anotherAsteroid), "There should be no collision between asteroids at different locations.");
    }

    @Test
    public void testCheckCharacterCollision_WithCollision() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        Character anotherAsteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        anotherAsteroid.setX(100);
        anotherAsteroid.setY(100);

        asteroid.setX(100);
        asteroid.setY(100);

        assertTrue(asteroid.checkCharacterCollision(anotherAsteroid), "Asteroids at the same location should collide.");
    }

    @Test
    public void testCheckPlayerShipCollision() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        PlayerShip playerShip = new PlayerShip(400, 300, 1, 5, 0.2, 3.0, 0.99, 1, gameStage.getWidth(), gameStage.getHeight(), gameStageController);

        // Set positions to test collision
        asteroid.setX(400);
        asteroid.setY(300);
        playerShip.setX(400);
        playerShip.setY(300);

        assertTrue(asteroid.checkPlayerShipCollision(playerShip), "Asteroid should collide with player ship when overlapping.");
    }

    @Test
    public void testUpdate() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        double initialX = asteroid.getX();
        double initialY = asteroid.getY();

        asteroid.moveForward();  // Update position based on velocity
        asteroid.update();       // Apply further updates (e.g., friction, rotation)

        assertNotEquals(initialX, asteroid.getX(), "Asteroid X position should change after update.");
        assertNotEquals(initialY, asteroid.getY(), "Asteroid Y position should change after update.");
    }

    @Test
    public void testExplodeAnimationOnCollision() {
        gameStage = mock(GameStage.class);
        gameStageController = mock(GameStageController.class);
        Mockito.when(gameStageController.getGameStage()).thenReturn(gameStage);
        Mockito.when(gameStage.getWidth()).thenReturn(800.0);
        Mockito.when(gameStage.getHeight()).thenReturn(600.0);
        asteroid = new Asteroid(38, 38, 1, 2, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());
        asteroid.collided(false); // Simulate a collision

        assertTrue(asteroid.getCurrentAnimations().contains("explode"), "Explode animation should be triggered on collision.");
        assertFalse(asteroid.getCurrentAnimations().contains("idle"), "Idle animation should be removed on collision.");
    }
}
