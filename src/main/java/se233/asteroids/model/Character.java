package se233.asteroids.model;

public abstract class Character {
    protected int x;
    protected int y;
    protected double speed;
    protected int health;

    public Character(int x, int y, double speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
    }
}
