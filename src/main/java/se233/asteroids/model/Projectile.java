package se233.asteroids.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.util.ImageUtil;

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

    public Projectile(String imagePath, double width, double height, double x, double y, double angle, double speed, double maxSpeed, double acceleration, double friction, double gameWidth, double gameHeight) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.friction = friction;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.velocityX = 0;
        this.velocityY = 0;

        Image image = ImageUtil.loadImage(imagePath);
        if (image != null) {
            this.imageView = new ImageView(image);
            this.imageView.setFitWidth(width);
            this.imageView.setFitHeight(height);
            this.imageView.setRotate(angle);
            setX(x);
            setY(y);
            logger.info("Image successfully loaded: {} (Width = {}, Height = {})", imagePath, width, height);
        } else {
            logger.error("Failed to load image: {}", imagePath);
        }
    }

    public ImageView getImageView() {
        return this.imageView;
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

    public void setRotate(double angle) {
        this.imageView.setRotate(angle);
    }

    public double getRotate() {
        return imageView.getRotate();
    }

    public void move() {
        double angle = Math.toRadians(this.getRotate());
        velocityX += Math.cos(angle) * acceleration;
        velocityY += Math.sin(angle) * acceleration;
        limitSpeed();
    }

    public boolean checkWallCollisions() {
        return getX() < 0 || getX() > gameWidth || getY() < 0 || getY() > gameHeight;
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

    public void update() {
        move();
        setX(this.getX() + velocityX);
        setY(this.getY() + velocityY);
        applyFriction();
        checkWallCollisions();
    }
}
