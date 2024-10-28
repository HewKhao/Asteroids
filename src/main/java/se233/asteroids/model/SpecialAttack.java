package se233.asteroids.model;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import se233.asteroids.controller.GameStageController;

import java.awt.*;
import java.util.List;

public class SpecialAttack extends Projectile {
    private static final String WINDBLADE_SPRITE = "/se233/asteroids/assets/projectile/Windblade.png";
    private boolean isMarkForRemove;

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
                gameStageController.incrementScore(1);
                asteroid.collided();
                return true;
            } else if (character instanceof NormalEnemies && checkCharacterCollision(character)) {
                NormalEnemies normalEnemies = (NormalEnemies) character;
                gameStageController.incrementScore(1);
                normalEnemies.collided();
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
    }
}
