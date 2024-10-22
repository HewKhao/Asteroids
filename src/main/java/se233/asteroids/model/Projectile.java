package se233.asteroids.model;

public abstract class Projectile {
    private double x;
    private double y;
    private double speed;

    public Projectile(int x, int y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
