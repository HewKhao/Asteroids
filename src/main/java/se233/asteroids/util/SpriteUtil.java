package se233.asteroids.util;

import javafx.scene.image.Image;
import se233.asteroids.Launcher;
import se233.asteroids.model.AnimatedSprite;

public class SpriteUtil {
    public static AnimatedSprite createAnimatedSprite(String imagePath, int count, int columns, int rows, int width, int height) {
        Image spriteSheet = new Image(Launcher.class.getResourceAsStream(imagePath));
        AnimatedSprite sprite = new AnimatedSprite(spriteSheet, count, columns, rows, 0, 0, width, height);
        sprite.setFitWidth(100);
        sprite.setFitHeight(100);
        return sprite;
    }
}
