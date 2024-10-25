package se233.asteroids.model;

import java.util.Random;

public class Asteroid extends Character{

    private static final String ASTEROID_IDLE = "/se233/asteroids/assets/asteroids/Asteroid_Idle.png";
    private static final String ASTEROID_EXPLODE = "/se233/asteroids/assets/asteroids/Asteroid_Explode.png";

    private Random random;

    public Asteroid(double width, double height, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double gameWidth, double gameHeight){
        super(ASTEROID_IDLE,width,height,0,0, initialSpeed, maxSpeed, acceleration, rotationSpeed, friction, health, gameWidth, gameHeight);
        random = new Random();
    }

    public void randomSpawn(double buffer) {
        double[] xPositions = {
                random.nextDouble() * gameWidth,  // Random X along the top or bottom edge
                random.nextDouble() * gameWidth,  // Random X along the top or bottom edge
                -buffer,  // Left edge (spawn off the left side)
                gameWidth + buffer  // Right edge (spawn off the right side)
        };

        double[] yPositions = {
                -buffer,  // Top edge (spawn above the screen)
                gameHeight + buffer,  // Bottom edge (spawn below the screen)
                random.nextDouble() * gameHeight,  // Random Y along the left or right edge
                random.nextDouble() * gameHeight   // Random Y along the left or right edge
        };

        // Randomly select one of the four borders (0 = top, 1 = bottom, 2 = left, 3 = right)
        int borderIndex = random.nextInt(4);
        this.x = xPositions[borderIndex];
        this.y = yPositions[borderIndex];

        setX(x);
        setY(y);
    }

    public void initializeRandomDirection() {
        double targetX = random.nextDouble() * gameWidth;  // Random target point inside the screen
        double targetY = random.nextDouble() * gameHeight;

        // Calculate direction towards the target point
        double angleToTarget = Math.atan2(targetY - this.y, targetX - this.x);

        // Set velocity based on the direction towards the target
        velocityX = Math.cos(angleToTarget) * maxSpeed;
        velocityY = Math.sin(angleToTarget) * maxSpeed;

        // Optionally set random rotation for the asteroid
        setRotate(random.nextDouble() * 360);
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
