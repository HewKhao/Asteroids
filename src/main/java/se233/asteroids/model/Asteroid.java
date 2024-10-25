package se233.asteroids.model;

import se233.asteroids.view.GameStage;

import java.util.Random;

public class Asteroid extends Character{

    private static final String ASTEROID_IDLE = "/se233/asteroids/assets/asteroids/Asteroid_Idle.png";
    private static final String ASTEROID_EXPLODE = "/se233/asteroids/assets/asteroids/Asteroid_Explode.png";

    private Random random;

    public Asteroid(double width, double height, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double gameWidth, double gameHeight){
        super(ASTEROID_IDLE,width,height,0,0, initialSpeed, maxSpeed, acceleration, rotationSpeed, friction, health, gameWidth, gameHeight);
        random = new Random();
    }

    public void randomSpawn() {
        boolean spawnHorizontally = random.nextBoolean();

        if (spawnHorizontally) {
            // Spawn to the left or right of the screen
            this.x = random.nextBoolean() ? -random.nextDouble() * 100 : gameWidth + random.nextDouble() * 100;
            this.y = random.nextDouble() * gameHeight;
        } else {
            // Spawn above or below the screen
            this.y = random.nextBoolean() ? -random.nextDouble() * 100 : gameHeight + random.nextDouble() * 100;
            this.x = random.nextDouble() * gameWidth;
        }

        setX(x);
        setY(y);
    }

    public void initializeRandomDirection() {
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
