package se233.asteroids.model;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.asteroids.util.SpriteUtil;

import java.util.Random;

public class Asteroid extends Character{

    private static final String ASTEROID_IDLE = "/se233/asteroids/assets/asteroids/Asteroid_Idle.png";
    private static final String ASTEROID_EXPLODE = "/se233/asteroids/assets/asteroids/Asteroid_Explode.png";

    private Random random = new Random();
    private boolean isMarkForRemove = false;

    public Rectangle outline;

    public Asteroid(double width, double height, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double gameWidth, double gameHeight){
        super(ASTEROID_IDLE, width, height,0,0, initialSpeed, maxSpeed, acceleration, rotationSpeed, friction, health, gameWidth, gameHeight);

        loadAnimations();

        currentAnimations.add("idle");
        this.imageView.setImage(animations.get("idle").getImage());

        double radian = random.nextDouble() * 2 * Math.PI;
        this.imageView.setRotate(radian);
    }

    public void loadAnimations(){
//        animations.put("idle", SpriteUtil.createAnimatedSpriteWithOffset(ASTEROID_IDLE, 1, 1, 1, 29, 34, 38, 38, 38, 38));
        animations.put("idle", SpriteUtil.createAnimatedSprite(ASTEROID_IDLE, 1, 1, 1, 96, 96, 96, 96));
        animations.put("explode", SpriteUtil.createAnimatedSprite(ASTEROID_EXPLODE, 8, 8, 1, 96, 96, 96, 96));

        animations.get("explode").setPlayOnce(true);
    }

    public void randomSpawn() {
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

    @Override
    public void collided() {
        this.maxSpeed = 0;
        this.currentAnimations.remove("idle");
        this.currentAnimations.add("explode");
    }

    @Override
    public void update() {
        moveForward();
        super.update();
        checkWallCollisions();
    }
}
