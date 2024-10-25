package se233.asteroids.util;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.model.AnimatedSprite;

public class SpriteUtil {
    private static final Logger logger = LogManager.getLogger(SpriteUtil.class);

    public static AnimatedSprite createAnimatedSprite(String imagePath, int count, int columns, int rows, int frameWidth, int frameHeight, double imageWidth, double imageHeight) {
        Image spriteSheet = ImageUtil.loadImage(imagePath);
        if (spriteSheet != null) {
            AnimatedSprite sprite = new AnimatedSprite(spriteSheet, count, columns, rows, 0, 0, frameWidth, frameHeight);
            sprite.setFitWidth(imageWidth);
            sprite.setFitHeight(imageHeight);
            logger.info("Image successfully loaded: {}", imagePath);
            return sprite;
        } else {
            logger.error("Image could not be loaded: {}", imagePath);
        }
        return null;
    }

    public static AnimatedSprite createAnimatedSpriteWithOffset(String imagePath, int count, int columns, int rows, int offsetX, int offsetY, int frameWidth, int frameHeight, double imageWidth, double imageHeight) {
        Image spriteSheet = ImageUtil.loadImage(imagePath);
        if (spriteSheet != null) {
            AnimatedSprite sprite = new AnimatedSprite(spriteSheet, count, columns, rows, offsetX, offsetY, frameWidth, frameHeight);
            sprite.setFitWidth(imageWidth);
            sprite.setFitHeight(imageHeight);
            sprite.setPreserveRatio(false);
            logger.info("Image successfully loaded: {}", imagePath);
            return sprite;
        } else {
            logger.error("Image could not be loaded: {}", imagePath);
        }
        return null;
    }
}
