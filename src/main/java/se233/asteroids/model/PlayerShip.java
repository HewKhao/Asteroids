package se233.asteroids.model;

import javafx.scene.shape.Polygon;

public class PlayerShip extends Character {
    private final Polygon shipPolygon;

    private double velocityX;
    private double velocityY;
    private static final double ACCELERATION = 0.1;
    private static final double FRICTION = 0.98;
    private static final double ROTATION_SPEED = 3.0;
    private static final double MAX_SPEED = 5.0;

    private int width;
    private int height;

    public PlayerShip(int x, int y, int speed, int health, int width, int height) {
        super(x, y, speed, health);
        this.shipPolygon = new Polygon(-5, -5, 10, 0, -5, 5);
        this.shipPolygon.setTranslateX(x);
        this.shipPolygon.setTranslateY(y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.width = width;
        this.height = height;
    }

    public Polygon getNode() {
        return shipPolygon;
    }

    public void setTranslateX(double x) {
        this.shipPolygon.setTranslateX(x);
    }

    public double getTranslateX() {
        return this.shipPolygon.getTranslateX();
    }

    public void setTranslateY(double y) {
        this.shipPolygon.setTranslateY(y);
    }

    public double getTranslateY() {
        return this.shipPolygon.getTranslateY();
    }

    public void setRotate(double angle) {
        this.shipPolygon.setRotate(angle);
    }

    public double getRotate() {
        return this.shipPolygon.getRotate();
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
        if (getTranslateX() < 0) setTranslateX(width);
        if (getTranslateX() > width) setTranslateX(0);
        if (getTranslateY() < 0) setTranslateY(height);
        if (getTranslateY() > height) setTranslateY(0);
    }

    public void updateShipPosition() {
        setTranslateX(getTranslateX() + velocityX);
        setTranslateY(getTranslateY() + velocityY);
        applyFriction();
        checkWallCollisions();
    }
}