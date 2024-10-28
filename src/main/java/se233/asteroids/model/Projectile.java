package se233.asteroids.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.util.ImageUtil;
import se233.asteroids.util.SpriteUtil;

public abstract class Projectile {
    private static final Logger logger = LogManager.getLogger(Projectile.class);

    protected double x;
    protected double y;
    protected double speed;
    protected double maxSpeed;
    protected double acceleration;
    protected double friction;
    protected double velocityX;
    protected double velocityY;
    protected double gameWidth;
    protected double gameHeight;

    protected ImageView imageView;
    protected AnimatedSprite animatedSprite;

    public Projectile(String imagePath, int count, int columns, int rows, int frameWidth, int frameHeight, double width, double height, double x, double y, double angle, double initialSpeed, double maxSpeed, double acceleration, double friction, double gameWidth, double gameHeight) {
        this.x = x;
        this.y = y;
        this.speed = initialSpeed;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.friction = friction;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.velocityX = 0;
        this.velocityY = 0;

        Image image = ImageUtil.loadImage(imagePath);
        if (image != null) {
            this.animatedSprite = SpriteUtil.createAnimatedSprite(imagePath, count, columns, rows, frameWidth, frameHeight, width, height);
            if (this.animatedSprite != null) {
                this.animatedSprite.setRotate(angle);
                this.animatedSprite.setPreserveRatio(false);
            } else {
                logger.error("Failed to create animatedSprite for path: {}", imagePath);
            }

            try {
                this.imageView = new ImageView(image);
                this.imageView.setFitWidth(width);
                this.imageView.setFitHeight(height);
                this.imageView.setRotate(angle);
                this.imageView.setPreserveRatio(false);
                setX(x);
                setY(y);
            } catch (Exception e) {
                logger.error("Error while setting up ImageView: {}", e.getMessage());
            }
        } else {
            logger.error("Failed to load image: {}", imagePath);
        }
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void setAnimatedSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }

    public void updatePosition() {
        this.animatedSprite.setX(x - animatedSprite.getFitWidth() / 2.0);
        this.animatedSprite.setY(y - animatedSprite.getFitHeight() / 2.0);
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

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setRotate(double angle) {
        this.imageView.setRotate(angle);
    }

    public double getRotate() {
        return this.animatedSprite.getRotate();
    }

    public void move() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * acceleration;
        velocityY += Math.sin(angle) * acceleration;
        limitSpeed();
    }

    public void checkWallCollisions() {
        if (getX() < 0) setX(gameWidth);
        if (getX() > gameWidth) setX(0);
        if (getY() < 0) setY(gameHeight);
        if (getY() > gameHeight) setY(0);
    }

    private void limitSpeed() {
        this.setSpeed(Math.sqrt((velocityX * velocityX) + (velocityY * velocityY)));
        if (this.getSpeed() > maxSpeed) {
            velocityX = (velocityX / speed) * maxSpeed;
            velocityY = (velocityY / speed) * maxSpeed;
        }
    }

    public void applyFriction() {
        velocityX *= friction;
        velocityY *= friction;
    }

    public void update() {
        move();
        setX(this.getX() + velocityX);
        setY(this.getY() + velocityY);
        applyFriction();
        checkWallCollisions();
    }
}
