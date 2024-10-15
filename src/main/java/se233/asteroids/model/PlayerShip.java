package se233.asteroids.model;

import javafx.scene.shape.Polygon;

public class PlayerShip extends Character {
    private Polygon shipPolygon;

    private double velocityX;
    private double velocityY;
    private static final double ACCELERATION = 0.1;
    private static final double FRICTION = 0.98;
    private static final double MAX_SPEED = 5.0;

    public PlayerShip(int x, int y, int speed, int health) {
        super(x, y, speed, health);
        this.shipPolygon = new Polygon(-5, -5, 10, 0, -5, 5);
        this.shipPolygon.setTranslateX(x);
        this.shipPolygon.setTranslateY(y);
        this.velocityX = 0;
        this.velocityY = 0;
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

    public void moveForward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;

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

    public void updateShipPosition() {
        setTranslateX(getTranslateX() + velocityX);
        setTranslateY(getTranslateY() + velocityY);
        applyFriction();
    }
}