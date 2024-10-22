package se233.asteroids.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.util.ImageUtil;

public abstract class Character {
    private static final Logger logger = LogManager.getLogger(Character.class);

    protected double x;
    protected double y;
    protected double speed;
    protected double MAX_SPEED;
    protected double ACCELERATION;
    protected double ROTATION_SPEED;
    protected double FRICTION;
    protected int health;
    protected ImageView imageView;

    public Character(String imagePath, double width, double height, double x, double y, double MAX_SPEED, double ACCELERATION, double ROTATION_SPEED, double FRICTION, int health) {
        this.x = x;
        this.y = y;
        this.MAX_SPEED = MAX_SPEED;
        this.ACCELERATION = ACCELERATION;
        this.ROTATION_SPEED = ROTATION_SPEED;
        this.FRICTION = FRICTION;
        this.health = health;
        this.speed = 0;

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
}
