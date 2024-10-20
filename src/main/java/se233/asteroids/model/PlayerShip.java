package se233.asteroids.model;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.Launcher;

public class PlayerShip extends Character {
    private static final Logger logger = LogManager.getLogger(PlayerShip.class);

    private static final double ACCELERATION = 0.1;
    private static final double FRICTION = 0.99;
    private static final double ROTATION_SPEED = 3.0;
    private static final double MAX_SPEED = 3.0;

    private static final String IDLE_SPRITE = "/se233/asteroids/assets/playerShip/Idle.png";
    private static final String BOOST_SPRITE = "/se233/asteroids/assets/playerShip/Boost.png";
    private static final String MOVE_LEFT_SPRITE = "/se233/asteroids/assets/playerShip/Turn_1.png";

    private final int GAME_WIDTH;
    private final int GAME_HEIGHT;
    private double velocityX;
    private double velocityY;


    private Image idleImage;
    private AnimatedSprite boostAnimation;

    private boolean isMovingForward = false;
    private boolean isMovingLeft = false;

    private String currentAnimation = "idle";

    public PlayerShip(double x, double y, int speed, int health, int width, int height) {
        super(IDLE_SPRITE,100 ,100, x, y, speed, health);
        logger.info("PlayerShip created at X: {}, Y: {}", x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.GAME_WIDTH = width;
        this.GAME_HEIGHT = height;

        Image boostSprites = new Image(Launcher.class.getResourceAsStream(BOOST_SPRITE));
        boostAnimation = new AnimatedSprite(boostSprites, 5, 5, 1, 0, 0, 192, 192);
        boostAnimation.setFitWidth(100);
        boostAnimation.setFitHeight(100);

        idleImage = new Image(Launcher.class.getResourceAsStream(IDLE_SPRITE));
        this.imageView.setImage(idleImage);
    }

    public void setRotate(double angle) {
        this.imageView.setRotate(angle);
    }

    public double getRotate() {
        return this.imageView.getRotate();
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getRotationSpeed() {
        return ROTATION_SPEED;
    }

    public void moveForward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();
        isMovingForward = true;
        currentAnimation = "boost";
    }

    public void stopMoveForward() {
        isMovingForward = false;
    }

    public void moveBackward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX -= Math.cos(angle) * ACCELERATION;
        velocityY -= Math.sin(angle) * ACCELERATION;
        limitSpeed();
    }

    public void moveLeft() {
        double angle = Math.toRadians(this.getRotate() - 90);
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();
        isMovingLeft = true;
    }

    public void stopMoveLeft() {
        isMovingLeft = false;
    }

    public void moveRight() {
        double angle = Math.toRadians(this.getRotate() + 90);
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();
    }

    private void limitSpeed() {
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

    private void checkWallCollisions() {
        if (getX() < 0) setX(GAME_WIDTH);
        if (getX() > GAME_WIDTH) setX(0);
        if (getY() < 0) setY(GAME_HEIGHT);
        if (getY() > GAME_HEIGHT) setY(0);
    }

    public String getCurrentAnimation() {
        return currentAnimation;
    }

//    private void updateAnimation() {
//        if (isMovingForward) {
//            boostAnimation.tick();
//            this.imageView.setImage(boostAnimation.getImage());
//            this.imageView.setViewport(boostAnimation.getViewport());
//        } else {
//            this.imageView.setImage(new Image(Launcher.class.getResourceAsStream(IDLE_SPRITE)));
//            this.imageView.setViewport(new Rectangle2D(0, 0, idleImage.getWidth(), idleImage.getHeight()));
//        }
//    }

    private void updateAnimation() {
        if (isMovingForward) {
            currentAnimation = "boost";
        } else {
            currentAnimation = "idle";
        }
    }

    public void updateShipPosition() {
        setX(getX() + velocityX);
        setY(this.getY() + velocityY);
        applyFriction();
        checkWallCollisions();

        updateAnimation();

        logger.info("PlayerShip Position - X: {}, Y: {}", getX(), getY());
        logger.info(currentAnimation);
    }
}