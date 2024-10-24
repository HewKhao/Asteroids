package se233.asteroids.model;

import java.util.Random;

public class Asteroid extends Character{

    private static final String ASTEROID_IDLE = "/se233/asteroids/assets/asteroids/Asteroid_Idle.png";
    private static final String ASTEROID_EXPLODE = "/se233/asteroids/assets/asteroids/Asteroid_Explode.png";

    private Random random;

    public Asteroid(double width, double height, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double gameWidth, double gameHeight){
        super(ASTEROID_IDLE,width,height,0,0, initialSpeed, maxSpeed, acceleration, rotationSpeed, friction, health, gameWidth, gameHeight);
        random = new Random();
        randomSpawn();
        initializeRandomDirection();
    }

    private void randomSpawn() {
        this.x = random.nextDouble() * gameWidth;
        this.y = random.nextDouble() * gameHeight;
        setX(x);
        setY(y);
    }

    private void initializeRandomDirection() {
        // Random velocity in all directions (up, down, left, right)
        velocityX = (random.nextDouble() - 0.5) * 2 * maxSpeed;
        velocityY = (random.nextDouble() - 0.5) * 2 * maxSpeed;
        double randomAngle = random.nextDouble() * 360;
        setRotate(randomAngle);
    }

    public void moveRandomly() {
        // Adjust velocity slightly in random directions
        velocityX += (random.nextDouble() - 0.5) * 0.1;
        velocityY += (random.nextDouble() - 0.5) * 0.1;
    }

    @Override
    public void update() {
        moveRandomly();
        setX(this.getX() + velocityX);
        setY(this.getY() + velocityY);
        checkWallCollisions();
    }
}
