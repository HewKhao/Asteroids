package se233.asteroids.util;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.asteroids.Launcher;

import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
    private static final Logger logger = LogManager.getLogger(ImageUtil.class);

    public static Image loadImage(String path) {
        try (InputStream inputStream = Launcher.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                logger.error("Image resource not found: {}", path);
                return null;
            }
            return new Image(inputStream);
        } catch (IOException e) {
            logger.error("Error while loading image {}", e.getMessage());
            return null;
        }
    }
}
