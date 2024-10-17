package se233.asteroids.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerShip extends Character {
    private static final Logger logger = LogManager.getLogger(PlayerShip.class);

    private double velocityX;
    private double velocityY;
    private static final double ACCELERATION = 0.1;
    private static final double FRICTION = 0.99;
    private static final double ROTATION_SPEED = 3.0;
    private static final double MAX_SPEED = 3.0;

    private final int GAME_WIDTH;
    private final int GAME_HEIGHT;

    public PlayerShip(double x, double y, int speed, int health, int width, int height) {
        super("/se233/asteroids/assets/playerShip/Idle.png",100 ,100, x, y, speed, health);
        logger.info("PlayerShip created at X: {}, Y: {}", x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.GAME_WIDTH = width;
        this.GAME_HEIGHT = height;
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
        return this.ROTATION_SPEED;
    }

    public void moveForward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();
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

    public void updateShipPosition() {
        setX(getX() + velocityX);
        setY(this.getY() + velocityY);
        applyFriction();
        checkWallCollisions();

        logger.info("PlayerShip Position - X: {}, Y: {}", getX(), this.getY());
    }
}