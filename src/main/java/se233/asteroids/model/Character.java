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
    protected double maxSpeed;
    protected double acceleration;
    protected double rotationSpeed;
    protected double friction;
    protected double velocityX;
    protected double velocityY;
    protected double gameWidth;
    protected double gameHeight;

    protected int health;
    protected ImageView imageView;

    protected final List<String> currentAnimations;
    protected final Map<String, AnimatedSprite> animations;
    protected final Map<String, double[]> animationOffsets;
    protected final Map<String, Double> animationRotates;

    public Character(String imagePath, double width, double height, double x, double y, double initialSpeed, double maxSpeed, double acceleration, double rotationSpeed, double friction, int health, double gameWidth, double gameHeight) {
        this.x = x;
        this.y = y;
        this.speed = initialSpeed;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.rotationSpeed = rotationSpeed;
        this.friction = friction;
        this.health = health;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.velocityX = 0;
        this.velocityY = 0;

        this.currentAnimations = new ArrayList<>();
        this.animations = new HashMap<>();
        this.animationOffsets = new HashMap<>();
        this.animationRotates = new HashMap<>();

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
        return rotationSpeed;
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

    public Map<String, Double> getAnimationRotates() {return animationRotates;}

    public ImageView getImageView() {return this.imageView;}

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getGameWidth() {
        return gameWidth;
    }

    public void setGameWidth(double gameWidth) {
        this.gameWidth = gameWidth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getGameHeight() {
        return gameHeight;
    }

    public void setGameHeight(double gameHeight) {
        this.gameHeight = gameHeight;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void moveForward() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * acceleration;
        velocityY += Math.sin(angle) * acceleration;
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
        velocityX -= Math.cos(angle) * acceleration;
        velocityY -= Math.sin(angle) * acceleration;
        limitSpeed();
    }

    public void moveLeft() {
        double angle = Math.toRadians(this.getRotate() - 90);
        velocityX += Math.cos(angle) * acceleration;
        velocityY += Math.sin(angle) * acceleration;
        limitSpeed();
    }

    public void moveRight() {
        double angle = Math.toRadians(this.getRotate() + 90);
        velocityX += Math.cos(angle) * acceleration;
        velocityY += Math.sin(angle) * acceleration;
        limitSpeed();
    }

    public void rotate(double angle) {
        setRotate(this.getRotate() + angle);
    }

    private void limitSpeed() {
        this.setSpeed(Math.sqrt((velocityX * velocityX) + (velocityY * velocityY)));
        if (this.getSpeed() > maxSpeed) {
            velocityX = (velocityX / speed) * maxSpeed;
            velocityY = (velocityY / speed) * maxSpeed;
        }
    }

    private void applyFriction() {
        velocityX *= friction;
        velocityY *= friction;
    }

    public void checkWallCollisions() {
        if (getX() < 0) setX(gameWidth);
        if (getX() > gameWidth) setX(0);
        if (getY() < 0) setY(gameHeight);
        if (getY() > gameHeight) setY(0);
    }

    public void update() {
        setX(this.getX() + velocityX);
        setY(this.getY() + velocityY);
        applyFriction();
        checkWallCollisions();
    }
}
