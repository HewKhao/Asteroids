package se233.asteroids.model;

public class NormalLaser extends Projectile {
    private static final String LASER_SPRITE = "/se233/asteroids/assets/projectile/Charge_1.png";

    public NormalLaser(double x, double y, double speed) {
        super(LASER_SPRITE, 20, 20, x, y, speed);
    }
}
