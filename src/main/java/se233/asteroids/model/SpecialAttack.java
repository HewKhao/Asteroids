package se233.asteroids.model;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import se233.asteroids.controller.GameStageController;

import java.util.List;

public class SpecialAttack extends Projectile {
    private static final String WINDBLADE_SPRITE = "/se233/asteroids/assets/projectile/Windblade.png";
    private boolean isMarkForRemove;

    private double iframe = 0.5;
    private double timeSinceLastHit = 0;

    public Rectangle outline;

    public SpecialAttack(double x, double y, double rotation, double initialSpeed, double maxSpeed, double acceleration, double friction, double width, double height) {
        super(WINDBLADE_SPRITE, 6, 3, 2, 32, 32, 40, 40, x, y, rotation, initialSpeed, maxSpeed, acceleration, friction, width, height);
        this.isMarkForRemove = false;
    }

    public void markForRemove() {
        this.isMarkForRemove = true;
    }

    public boolean isMarkForRemove() {
        return this.isMarkForRemove;
    }

    public boolean checkCharacterCollision(Character character) {
        Bounds projectileBounds = this.outline.getBoundsInParent();
        Bounds characterBounds = character.getImageView().getBoundsInParent();

        return projectileBounds.intersects(characterBounds);
    }

    public boolean checkCollision(List<? extends Character> characters, GameStageController gameStageController) {
        for (Character character : characters) {
            if (checkCharacterCollision(character) && character instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) character;
                if (!asteroid.isCollided) {
                    gameStageController.incrementScore(1);
                    asteroid.isCollided = true;
                }
                asteroid.collided(true);
                return true;
            }
            if (character instanceof NormalEnemies && checkCharacterCollision(character)) {
                NormalEnemies normalEnemies = (NormalEnemies) character;
                if (!normalEnemies.isCollided) {
                    gameStageController.incrementScore(2);
                    normalEnemies.isCollided = true;
                }
                normalEnemies.collided(true);
                return true;
            }
            if (character instanceof EliteEnemies && checkCharacterCollision(character)) {
                EliteEnemies eliteEnemies = (EliteEnemies) character;
                eliteEnemies.collided(true);
                return true;
            }
        }

        return false;
    }

    @Override
    public void checkWallCollisions() {
        if (getX() < 0) markForRemove();
        if (getX() > gameWidth) markForRemove();
        if (getY() < 0) markForRemove();
        if (getY() > gameHeight) markForRemove();
    }

    public void update() {
        super.update();
        if (timeSinceLastHit < iframe) {
            timeSinceLastHit += 0.016;
        }
    }
}
