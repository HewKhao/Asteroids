package se233.asteroids.model;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class EnemiesAttack extends Projectile {
    private static final String ATTACK_SPRITE = "/se233/asteroids/assets/projectile/Charge_2.png";
    private final double MAX_LIFE_TIME = 3;
    private double timeAlive;
    private boolean isMarkForRemove;

    private NormalEnemies father;

    public Rectangle outline;

    public EnemiesAttack(double x, double y, double rotation, double initialSpeed, double maxSpeed, double acceleration, double friction, double width, double height) {
        super(ATTACK_SPRITE, 1, 1, 1, 12, 12, 30, 20, x, y, rotation, initialSpeed, maxSpeed, acceleration, friction, width, height);
        this.timeAlive = 0;
        this.isMarkForRemove = false;
    }

    public void setFather(NormalEnemies father) {
        this.father = father;
    }

    public void markForRemoval() {
        this.isMarkForRemove = true;
    }

    public boolean isMarkForRemove() {
        return isMarkForRemove;
    }

    public boolean checkCharacterCollision(Character character) {
        Bounds projectileBounds = this.outline.getBoundsInParent();
        Bounds characterBounds = character.getImageView().getBoundsInParent();

        return projectileBounds.intersects(characterBounds);
    }

    public Boolean checkPlayerShipCollision(PlayerShip playerShip) {
        Bounds asteroidBounds = this.outline.getBoundsInParent();
        Bounds playerShipBounds = playerShip.getImageView().getBoundsInParent();

        if (asteroidBounds.intersects(playerShipBounds)) {
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

    public void update() {
        super.update();
        timeAlive += 0.016;

        if (timeAlive >= MAX_LIFE_TIME) {
            markForRemoval();
        }

        if (timeAlive > 1) {
            this.father = null;
        }
    }
}
