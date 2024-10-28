package se233.asteroids.model;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class EliteAttack extends Projectile {
    private static final String ATTACK_SPRITE = "/se233/asteroids/assets/projectile/EliteAttack.png";

    private boolean isMarkForRemove = false;

    private EliteEnemies father;

    public Rectangle outline;

    public EliteAttack(double x, double y, double rotation, double initialSpeed, double maxSpeed, double acceleration, double friction, double width, double height) {
        super(ATTACK_SPRITE, 30, 30, 1, 100, 100, 50, 25, x, y, rotation, initialSpeed, maxSpeed, acceleration, friction, width, height);
        this.isMarkForRemove = false;
    }

    public void setFather(EliteEnemies father) {
        this.father = father;
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

    public Boolean checkPlayerShipCollision(PlayerShip playerShip) {
        Bounds attackBounds = this.outline.getBoundsInParent();
        Bounds playerShipBounds = playerShip.getImageView().getBoundsInParent();

        if (attackBounds.intersects(playerShipBounds)) {
            playerShip.collided(false);
            return true;
        }

        return false;
    }

    public boolean checkCollision(List<? extends Character> characters) {
        for (Character character : characters) {
            if (character == father) {
                return false;
            }
            if (checkCharacterCollision(character)) {
                character.collided(false);
                return true;
            }
        }

        return false;
    }

    public void checkWallCollisions() {
        if (getX() < 0) markForRemove();
        if (getX() > gameWidth) markForRemove();
        if (getY() < 0) markForRemove();
        if (getY() > gameHeight) markForRemove();
    }

    public void update() {
        super.update();
        checkWallCollisions();
    }
}
