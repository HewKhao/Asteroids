package se233.asteroids.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.util.ImageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Character {
    private static final Logger logger = LogManager.getLogger(Character.class);

    protected double x;
    protected double y;
    protected double speed;
    protected double MAX_SPEED;
    protected double ACCELERATION;
    protected double ROTATION_SPEED;
    protected double FRICTION;
    protected double velocityX;
    protected double velocityY;
    protected double GAME_WIDTH;
    protected double GAME_HEIGHT;

    protected int health;
    protected ImageView imageView;

    protected final List<String> currentAnimations;
    protected final Map<String, AnimatedSprite> animations;
    protected final Map<String, double[]> animationOffsets;

    public Character(String imagePath, double width, double height, double x, double y, double MAX_SPEED, double ACCELERATION, double ROTATION_SPEED, double FRICTION, int health, double GAME_WIDTH, double GAME_HEIGHT) {
        this.x = x;
        this.y = y;
        this.MAX_SPEED = MAX_SPEED;
        this.ACCELERATION = ACCELERATION;
        this.ROTATION_SPEED = ROTATION_SPEED;
        this.FRICTION = FRICTION;
        this.health = health;
        this.GAME_WIDTH = GAME_WIDTH;
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.speed = 0;
        this.velocityX = 0;
        this.velocityY = 0;

        this.currentAnimations = new ArrayList<>();
        this.animations = new HashMap<>();
        this.animationOffsets = new HashMap<>();

        Image image = ImageUtil.loadImage(imagePath);
        if (image != null) {
            this.imageView = new ImageView(image);
            this.imageView.setFitWidth(width);
            this.imageView.setFitHeight(height);
            setX(x);
            setY(y);
            logger.info("Image successfully loaded: {} (Width = {}, Height = {})", imagePath, width, height);
        } else {
            logger.error("Failed to load image: {}", imagePath);
        }
    }

    public void updatePosition() {
        this.imageView.setX(x - imageView.getFitWidth() / 2.0);
        this.imageView.setY(y - imageView.getFitHeight() / 2.0);
    }

    public void setX(double x) {
        this.x = x;
        updatePosition();
    }

    public void setY(double y) {
        this.y = y;
        updatePosition();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

    public void setRotate(double angle) {
        this.imageView.setRotate(angle);
    }

    public double getRotate() {
        return this.imageView.getRotate();
    }

    public List<String> getCurrentAnimations() {
        return currentAnimations;
    }

    public Map<String, AnimatedSprite> getAnimations() {
        return animations;
    }

    public Map<String, double[]> getAnimationOffsets() {
        return animationOffsets;
    }

    public void moveForward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();

        if (!currentAnimations.contains("boost")) {
            currentAnimations.add("boost");
        }
    }

    public void stopMoveForward() {
        currentAnimations.remove("boost");
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
    }

    public void moveRight() {
        double angle = Math.toRadians(this.getRotate() + 90);
        velocityX += Math.cos(angle) * ACCELERATION;
        velocityY += Math.sin(angle) * ACCELERATION;
        limitSpeed();
    }

    public void rotate(double angle) {
        setRotate(this.getRotate() + angle);
    }

    private void limitSpeed() {
        this.setSpeed(Math.sqrt((velocityX * velocityX) + (velocityY * velocityY)));
        if (this.getSpeed() > MAX_SPEED) {
            velocityX = (velocityX / speed) * MAX_SPEED;
            velocityY = (velocityY / speed) * MAX_SPEED;
        }
    }

    private void applyFriction() {
        velocityX *= FRICTION;
        velocityY *= FRICTION;
    }

    public void checkWallCollisions() {
        if (getX() < 0) setX(GAME_WIDTH);
        if (getX() > GAME_WIDTH) setX(0);
        if (getY() < 0) setY(GAME_HEIGHT);
        if (getY() > GAME_HEIGHT) setY(0);
    }

    public void update() {
        setX(this.getX() + velocityX);
        setY(this.getY() + velocityY);
        applyFriction();
        checkWallCollisions();
    }
}
