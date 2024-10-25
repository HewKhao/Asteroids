package se233.asteroids.model;

import se233.asteroids.util.SpriteUtil;

public class Explosion {
    private static final String FIREBALL_EXPLODE = "/se233/asteroids/assets/projectile/Explosion.png";
    private AnimatedSprite animatedSprite;
    private boolean isMarkForRemove;

    public Explosion(double x, double y) {
        this.animatedSprite = SpriteUtil.createAnimatedSprite(FIREBALL_EXPLODE, 14, 4, 4, 64, 64, 40, 40);
        this.animatedSprite.setX(x);
        this.animatedSprite.setY(y);
        this.animatedSprite.setVisible(true);
        this.isMarkForRemove = false;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }

    public void markForRemoval() {
        this.isMarkForRemove = true;
    }

    public boolean isMarkForRemove() {
        return isMarkForRemove;
    }
}
