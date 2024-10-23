package se233.asteroids.util;

import javafx.scene.image.Image;
import se233.asteroids.Launcher;
import se233.asteroids.model.AnimatedSprite;

public class SpriteUtil {
    public static AnimatedSprite createAnimatedSprite(String imagePath, int count, int columns, int rows, int frameWidth, int frameHeight, double imageWidth, double imageHeight) {
        Image spriteSheet = new Image(Launcher.class.getResourceAsStream(imagePath));
        AnimatedSprite sprite = new AnimatedSprite(spriteSheet, count, columns, rows, 0, 0, frameWidth, frameHeight);
        sprite.setFitWidth(imageWidth);
        sprite.setFitHeight(imageHeight);
        return sprite;
    }
}
