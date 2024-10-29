package se233.asteroids.model;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import se233.asteroids.controller.GameStageController;
import se233.asteroids.util.SpriteUtil;

import java.util.Random;

public class EliteEnemies extends Character {
    private static final String ELITE_FLYING = "/se233/asteroids/assets/elite/Flying.png";
    private static final String ELITE_HURTING = "/se233/asteroids/assets/elite/Hurt.png";
    private static final String ELITE_DEATH = "/se233/asteroids/assets/elite/Death.png";

    private final double shootCooldown = 0.5;
    public double timeSinceLastShot = 0;

    private final double iframe = 1.0;
    public double timeSinceLastHit = 0;

    private Random random = new Random();
    private boolean isMarkForRemove = false;

    private boolean isDead = false;

    public Rectangle outline;

    public EliteEnemies(double width, double height, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double gameWidth, double gameHeight) {
        super(ELITE_FLYING, width, height, 0, 0, initialSpeed, maxSpeed, acceleration, rotationSpeed, friction, health, gameWidth, gameHeight);

        loadAnimation();

        currentAnimations.add("flying");
        this.imageView.setImage(animations.get("flying").getImage());
    }

    public boolean isDead() {
        return isDead;
    }

    public void loadAnimation() {
        animations.put("flying", SpriteUtil.createAnimatedSprite(ELITE_FLYING, 4, 4, 1, 81, 71, 50, 50));
        animations.put("hurting", SpriteUtil.createAnimatedSprite(ELITE_HURTING, 4, 4, 1, 81, 71, 50, 50));
        animations.put("death", SpriteUtil.createAnimatedSprite(ELITE_DEATH, 7, 7, 1, 81, 71, 50, 50));

        animations.get("hurting").setPlayOnce(true);
        animations.get("death").setPlayOnce(true);
    }

    public void spawn() {
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
        Bounds eliteBounds = this.getImageView().getBoundsInParent();
        Bounds playerShipBounds = playerShip.getImageView().getBoundsInParent();

        if (eliteBounds.intersects(playerShipBounds)) {
            playerShip.collided(false);
            return true;
        }

        return false;
    }

    public void randomWarp() {
        double x = Math.random() * gameWidth;
        double y = Math.random() * gameHeight;
        this.setX(x);
        this.setY(y);
    }

    public void shoot(GameStageController gameStageController) {
        if (timeSinceLastShot < shootCooldown) {
            return;
        }
        int numberOfObjects = random.nextInt(3) + 1;

        int edge = random.nextInt(4);

        for (int i = 0; i < numberOfObjects; i++) {
            double x = 0;
            double y = 0;
            double angle = 0;

            switch (edge) {
                case 0: // Top
                    x = random.nextDouble() * gameWidth;
                    y = 0;
                    angle = 90;
                    break;
                case 1: // Bottom
                    x = random.nextDouble() * gameWidth;
                    y = gameHeight;
                    angle = -90;
                    break;
                case 2: // Left
                    x = 0;
                    y = random.nextDouble() * gameHeight;
                    angle = 0;
                    break;
                case 3: // Right
                    x = gameWidth;
                    y = random.nextDouble() * gameHeight;
                    angle = 180;
                    break;
            }

            EliteAttack attack = new EliteAttack(x, y, angle, 1, 1,1 ,1, gameWidth, gameHeight);
            attack.setFather(this);

            gameStageController.getEliteAttackController().addEliteAttack(attack);
        }
        this.timeSinceLastShot = 0;
    }

    @Override
    public void collided(boolean player) {
        if (!player) {
            return;
        }
        if (timeSinceLastHit < iframe) {
            return;
        }
        if (health > 0) {
            currentAnimations.add("hurting");
            health--;
            if (health == 0) {
                this.maxSpeed = 0;
                this.currentAnimations.remove("flying");
                this.currentAnimations.add("death");
                this.isDead = true;
            }
            if (!isDead) {
                randomWarp();
            }
            timeSinceLastHit = 0;
        }
    }

    @Override
    public void update() {
        moveForward();
        super.update();
        checkWallCollisions();

        if (timeSinceLastHit < iframe) {
            timeSinceLastHit += 0.016;
        }

        if (timeSinceLastShot < shootCooldown) {
            timeSinceLastShot += 0.016;
        }
    }
}
