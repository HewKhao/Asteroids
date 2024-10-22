package se233.asteroids.model;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.Launcher;
import se233.asteroids.util.SpriteUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerShip extends Character {
    private static final Logger logger = LogManager.getLogger(PlayerShip.class);

    private static final double ACCELERATION = 0.1;
    private static final double FRICTION = 0.99;
    private static final double ROTATION_SPEED = 3.0;
    private static final double MAX_SPEED = 3.0;

    private static final String IDLE_SPRITE = "/se233/asteroids/assets/playerShip/Idle.png";
    private static final String BOOST_SPRITE = "/se233/asteroids/assets/playerShip/Boost.png";
    private static final String SHOOT_SPRITE = "/se233/asteroids/assets/playerShip/Attack_1.png";

    private final int GAME_WIDTH;
    private final int GAME_HEIGHT;
    private double velocityX;
    private double velocityY;

    private final List<String> currentAnimations;
    private final Map<String, AnimatedSprite> animations;
    private final Map<String, double[]> animationOffsets;

    private final double shootCooldown = 0.5;
    private double timeSinceLastShot = 0.5;

    public PlayerShip(double x, double y, int speed, int health, int width, int height) {
        super(IDLE_SPRITE,100 ,100, x, y, speed, health);
        logger.info("PlayerShip created at X: {}, Y: {}", x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.GAME_WIDTH = width;
        this.GAME_HEIGHT = height;

        animations = new HashMap<>();
        animationOffsets = new HashMap<>();
        loadAnimations();

        currentAnimations = new ArrayList<>();
        currentAnimations.add("idle");
        this.imageView.setImage(animations.get("idle").getImage());
    }

    public void setRotate(double angle) {
        this.imageView.setRotate(angle);
    }

    public double getRotate() {
        return this.imageView.getRotate();
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getRotationSpeed() {
        return ROTATION_SPEED;
    }

    private void loadAnimations() {
        animations.put("idle", SpriteUtil.createAnimatedSprite(IDLE_SPRITE, 1, 1, 1, 192, 192));
        animations.put("boost", SpriteUtil.createAnimatedSprite(BOOST_SPRITE, 5, 5, 1, 192, 192));
        animations.put("shoot", SpriteUtil.createAnimatedSprite(SHOOT_SPRITE, 4, 4, 1, 192, 192));

        animationOffsets.put("shoot", new double[]{-2.6, 0});
    }

    public void moveForward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();

        if (!currentAnimations.contains("boost")) {
            currentAnimations.add("boost");
        }
    }

    public void stopMoveForward() {
        currentAnimations.remove("boost");
    }

    public void moveBackward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX -= Math.cos(angle) * ACCELERATION;
        velocityY -= Math.sin(angle) * ACCELERATION;
        limitSpeed();
    }

    public void moveLeft() {
        double angle = Math.toRadians(this.getRotate() - 90);
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();
    }

    public void moveRight() {
        double angle = Math.toRadians(this.getRotate() + 90);
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();
    }

    private void limitSpeed() {
        this.setSpeed(Math.sqrt((velocityX * velocityX) + (velocityY * velocityY)));
        if (this.getSpeed() > MAX_SPEED) {
            velocityX = (velocityX / speed) * MAX_SPEED;
            velocityY = (velocityY / speed) * MAX_SPEED;
        }
    }

    public void rotate(double angle) {
        setRotate(this.getRotate() + angle);
    }

    public void shoot() {
        if (timeSinceLastShot >= shootCooldown) {
            currentAnimations.add("shoot");
            AnimatedSprite shootSprite = animations.get("shoot");
            shootSprite.reset();

            logger.info("PlayerShip is shooting!");

            timeSinceLastShot = 0;
        }
    }

    private void applyFriction() {
        velocityX *= FRICTION;
        velocityY *= FRICTION;
    }

    private void checkWallCollisions() {
        if (getX() < 0) setX(GAME_WIDTH);
        if (getX() > GAME_WIDTH) setX(0);
        if (getY() < 0) setY(GAME_HEIGHT);
        if (getY() > GAME_HEIGHT) setY(0);
    }

    public List<String> getCurrentAnimations() {
        return currentAnimations;
    }

    public Map<String, AnimatedSprite> getAnimations() {
        return animations;
    }

    public Map<String, double[]> getAnimationOffsets() {
        return animationOffsets;
    }

    public void updateShip() {
        setX(getX() + velocityX);
        setY(this.getY() + velocityY);
        applyFriction();
        checkWallCollisions();

        if (timeSinceLastShot < shootCooldown) {
            timeSinceLastShot += 0.016;
        }

        if (currentAnimations.contains("shoot")) {
            AnimatedSprite shootSprite = animations.get("shoot");
            if (shootSprite.getCurrentFrame() == shootSprite.getTotalFrames() - 1) {
                currentAnimations.remove("shoot");
            }
        }

        if (Math.abs(velocityX) > 0.1 || Math.abs(velocityY) > 0.1) {
            logger.info("PlayerShip Position - X: {}, Y: {}", getX(), getY());
        }
    }
}