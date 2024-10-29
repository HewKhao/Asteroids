package se233.asteroids;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class NormalAttackTest {
    private GameStageController mockGameStageController;
    private NormalAttack normalAttack;
    private ImageView mockImageView;

    @BeforeEach
    public void setUp() {
        mockGameStageController = Mockito.mock(GameStageController.class);
        normalAttack = new NormalAttack(100, 100, 0, 1, 5, 0.2, 0.99, 64, 32);

        // Initialize outline for collision checks
        normalAttack.outline = new Rectangle(100, 100, 10, 10);

        // Create a mock ImageView with predefined bounds
        mockImageView = mock(ImageView.class);
        when(mockImageView.getBoundsInParent()).thenReturn(new Rectangle(100, 100, 10, 10).getBoundsInParent());
    }

    @Test
    public void testCollisionWithAsteroidIncrementsScore() {
        mockGameStageController = Mockito.mock(GameStageController.class);
        normalAttack = new NormalAttack(100, 100, 0, 1, 5, 0.2, 0.99, 64, 32);

        // Initialize outline for collision checks
        normalAttack.outline = new Rectangle(100, 100, 10, 10);

        // Create a mock ImageView with predefined bounds
        mockImageView = mock(ImageView.class);
        when(mockImageView.getBoundsInParent()).thenReturn(new Rectangle(100, 100, 10, 10).getBoundsInParent());

        Asteroid asteroid = Mockito.mock(Asteroid.class);
        when(asteroid.getImageView()).thenReturn(mockImageView); // Return the mocked ImageView

        boolean collided = normalAttack.checkCollision(List.of(asteroid), mockGameStageController);

        assertTrue(collided, "NormalAttack should collide with the asteroid.");
        verify(mockGameStageController, times(1)).incrementScore(1);
        verify(asteroid, times(1)).collided(true);
        assertTrue(normalAttack.isMarkForRemove(), "NormalAttack should be marked for removal after collision with asteroid.");
    }

    @Test
    public void testCollisionWithNormalEnemyIncrementsScore() {
        mockGameStageController = Mockito.mock(GameStageController.class);
        normalAttack = new NormalAttack(100, 100, 0, 1, 5, 0.2, 0.99, 64, 32);

        // Initialize outline for collision checks
        normalAttack.outline = new Rectangle(100, 100, 10, 10);

        // Create a mock ImageView with predefined bounds
        mockImageView = mock(ImageView.class);
        when(mockImageView.getBoundsInParent()).thenReturn(new Rectangle(100, 100, 10, 10).getBoundsInParent());

        NormalEnemies normalEnemy = Mockito.mock(NormalEnemies.class);
        when(normalEnemy.getImageView()).thenReturn(mockImageView); // Return the mocked ImageView

        boolean collided = normalAttack.checkCollision(List.of(normalEnemy), mockGameStageController);

        assertTrue(collided, "NormalAttack should collide with the normal enemy.");
        verify(mockGameStageController, times(1)).incrementScore(2);
        verify(normalEnemy, times(1)).collided(true);
        assertTrue(normalAttack.isMarkForRemove(), "NormalAttack should be marked for removal after collision with normal enemy.");
    }

    @Test
    public void testCollisionWithEliteEnemyIncrementsScoreIfDead() {
        mockGameStageController = Mockito.mock(GameStageController.class);
        normalAttack = new NormalAttack(100, 100, 0, 1, 5, 0.2, 0.99, 64, 32);

        // Initialize outline for collision checks
        normalAttack.outline = new Rectangle(100, 100, 10, 10);

        // Create a mock ImageView with predefined bounds
        mockImageView = mock(ImageView.class);
        when(mockImageView.getBoundsInParent()).thenReturn(new Rectangle(100, 100, 10, 10).getBoundsInParent());

        EliteEnemies eliteEnemy = Mockito.mock(EliteEnemies.class);
        when(eliteEnemy.getImageView()).thenReturn(mockImageView); // Return the mocked ImageView
        when(eliteEnemy.isDead()).thenReturn(true);

        boolean collided = normalAttack.checkCollision(List.of(eliteEnemy), mockGameStageController);

        assertTrue(collided, "NormalAttack should collide with the elite enemy.");
        verify(mockGameStageController, times(1)).incrementScore(5);
        verify(eliteEnemy, times(1)).collided(true);
        assertTrue(normalAttack.isMarkForRemove(), "NormalAttack should be marked for removal after collision with elite enemy.");
    }

    @Test
    public void testNormalAttackMarkedForRemovalAfterMaxLifeTime() {
        mockGameStageController = Mockito.mock(GameStageController.class);
        normalAttack = new NormalAttack(100, 100, 0, 1, 5, 0.2, 0.99, 64, 32);

        // Initialize outline for collision checks
        normalAttack.outline = new Rectangle(100, 100, 10, 10);

        // Create a mock ImageView with predefined bounds
        mockImageView = mock(ImageView.class);
        when(mockImageView.getBoundsInParent()).thenReturn(new Rectangle(100, 100, 10, 10).getBoundsInParent());

        for (int i = 0; i < (int) (0.75 / 0.016) + 1; i++) {
            normalAttack.update();
        }

        assertTrue(normalAttack.isMarkForRemove(), "NormalAttack should be marked for removal after exceeding max life time.");
    }
}

