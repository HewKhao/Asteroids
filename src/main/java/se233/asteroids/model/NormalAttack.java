package se233.asteroids.model;

public class NormalAttack extends Projectile {
    private static final String LASER_SPRITE = "/se233/asteroids/assets/projectile/Charge_1.png";
    private final double MAX_LIFE_TIME = 0.75;
    private double timeAlive;
    private boolean isMarkForRemove;

    public NormalAttack(double x, double y, double rotation, double initialSpeed, double maxSpeed, double acceleration, double friction, double width, double height) {
        super(LASER_SPRITE, 20, 20, x, y, rotation, initialSpeed, maxSpeed, acceleration, friction, width, height);
        this.timeAlive = 0;
        this.isMarkForRemove = false;
    }

    private void markForRemoval() {
        isMarkForRemove = true;
    }

    public boolean isMarkForRemove() {
        return isMarkForRemove;
    }

    public void update() {
        super.update();
        timeAlive += 0.016;

        if (timeAlive >= MAX_LIFE_TIME) {
            markForRemoval();
        }
    }
}
