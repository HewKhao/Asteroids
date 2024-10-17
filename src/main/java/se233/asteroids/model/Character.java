package se233.asteroids.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.Launcher;

import java.io.IOException;
import java.io.InputStream;

public abstract class Character {
    private static final Logger logger = LogManager.getLogger(Character.class);

    protected double x;
    protected double y;
    protected double speed;
    protected int health;
    protected ImageView imageView;

    public Character(String imagePath, int width, int height, double x, double y, double speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;

        try (InputStream inputStream = Launcher.class.getResourceAsStream(imagePath)) {
            if (inputStream == null) {
                logger.error("Image resource not found: {}", imagePath);
                throw new RuntimeException("Image resource not found");
            }
            Image image = new Image(inputStream);
            this.imageView = new ImageView(image);
            this.imageView.setFitWidth(width);
            this.imageView.setFitHeight(height);

            setX(x);
            setY(y);
            logger.info("Image loaded: Width = {}, Height = {}", image.getWidth(), image.getHeight());
        } catch (IOException e) {
            logger.error("Error loading image: {}", e.getMessage());
        }

    }

    public ImageView getImageView() {
        return imageView;
    }

//    public void setTranslateX(double x) {
//        this.x = x;
//        this.imageView.setX(x - imageView.getFitWidth() / 2.0);
//    }
//
//    public void setTranslateY(double y) {
//        this.y = y;
//        this.imageView.setY(y - imageView.getFitHeight() / 2.0);
//    }
//
    public void updatePosition() {
        this.imageView.setX(x - imageView.getFitWidth() / 2.0); // Center image horizontally
        this.imageView.setY(y - imageView.getFitHeight() / 2.0); // Center image vertically
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
