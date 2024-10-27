package se233.asteroids.model;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import se233.asteroids.util.SpriteUtil;

import java.util.List;
import java.util.Random;

public class NormalEnemies extends Character {
    private static final String SLIME_IDLE = "/se233/asteroids/assets/slime/SlimeIdle.png";
    private static final String SLIME_DIE = "/se233/asteroids/assets/slime/SlimeDie.png";

    private Random random = new Random();
    private boolean isMarkForRemove = false;

    public Rectangle outline;

    public NormalEnemies(double width, double height, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double gameWidth, double gameHeight) {
        super(SLIME_DIE, width, height, 0, 0, initialSpeed, maxSpeed, acceleration, rotationSpeed, friction, health, gameWidth, gameHeight);

        loadAnimation();

        currentAnimations.add("idle");
        this.imageView.setImage(animations.get("idle").getImage());

        double radian = random.nextDouble() * 2 * Math.PI;
        this.imageView.setRotate(radian);
    }

    public void loadAnimation() {
        animations.put("idle", SpriteUtil.createAnimatedSprite(SLIME_IDLE, 4, 4, 1, 32, 25, 30, 30));
        animations.put("die", SpriteUtil.createAnimatedSprite(SLIME_DIE, 4, 4, 1, 32, 25, 30, 30));

        animations.get("die").setPlayOnce(true);
    }

    public void randomSpawn() {
        boolean spawnHorizontally = random.nextBoolean();

        if (spawnHorizontally) {
            this.x = random.nextBoolean() ? -random.nextDouble() * 100 : gameWidth + random.nextDouble() * 100;
            this.y = random.nextDouble() * gameHeight;
        } else {
            this.y = random.nextBoolean() ? -random.nextDouble() * 100 : gameHeight + random.nextDouble() * 100;
            this.x = random.nextDouble() * gameWidth;
        }

        setX(x);
        setY(y);
    }

    public void initializeRandomDirection() {
        velocityX = (random.nextDouble() - 0.5) * 2 * maxSpeed;
        velocityY = (random.nextDouble() - 0.5) * 2 * maxSpeed;
        double randomAngle = random.nextDouble() * 360;
        setRotate(randomAngle);
    }

    public void markForRemove() {
        this.isMarkForRemove = true;
    }

    public boolean isMarkForRemove() {
        return isMarkForRemove;
    }

    public Boolean checkPlayerShipCollision(PlayerShip playerShip) {
        Bounds slimeBounds = this.getImageView().getBoundsInParent();
        Bounds playerShipBounds = playerShip.getImageView().getBoundsInParent();

        if (slimeBounds.intersects(playerShipBounds)) {
            playerShip.collided();
            return true;
        }

        return false;
    }

    @Override
    public void collided() {
        this.maxSpeed = 0;
        this.currentAnimations.remove("idle");
        this.currentAnimations.add("die");
    }

    @Override
    public void update() {
        moveForward();
        super.update();
        checkWallCollisions();
    }

}
