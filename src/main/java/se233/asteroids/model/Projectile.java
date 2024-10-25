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

    public Projectile(String imagePath, double width, double height, double x, double y, double angle, double initialSpeed, double maxSpeed, double acceleration, double friction, double gameWidth, double gameHeight) {
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

        this.animatedSprite = SpriteUtil.createAnimatedSprite(imagePath, 5, 5, 1, 64, 32, width, height);
        this.animatedSprite.setRotate(angle);
        this.animatedSprite.setPreserveRatio(false);

//        Image image = ImageUtil.loadImage(imagePath);
//        if (image != null) {
//            this.imageView = new ImageView(image);
//            this.imageView.setFitWidth(width);
//            this.imageView.setFitHeight(height);
//            this.imageView.setRotate(angle);
//            setX(x);
//            setY(y);
//            logger.info("Image successfully loaded: {} (Width = {}, Height = {})", imagePath, width, height);
//        } else {
//            logger.error("Failed to load image: {}", imagePath);
//        }
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

//    public boolean checkWallCollisions() {
//        return getX() < 0 || getX() > gameWidth || getY() < 0 || getY() > gameHeight;
//    }

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
