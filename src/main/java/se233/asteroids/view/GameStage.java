package se233.asteroids.view;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import se233.asteroids.Launcher;
import se233.asteroids.controller.PlayerShipController;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.PlayerShip;

public class GameStage extends Pane {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private PlayerShip playerShip;
    private PlayerShipController controller;

    private AnimatedSprite idleSprite;
    private AnimatedSprite boostSprite;

    public GameStage() {
        setPrefSize(WIDTH, HEIGHT);

        Image backgroundImage = new Image(Launcher.class.getResourceAsStream("/se233/asteroids/assets/background/background.png"));

        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        setBackground(new Background(bgImage));

        double centerX = (double) WIDTH / 2;
        double centerY = (double) HEIGHT / 2;

        playerShip = new PlayerShip(centerX, centerY, 1, 1, WIDTH, HEIGHT);
        playerShip.setRotate(-90);
        controller = new PlayerShipController(playerShip);

        idleSprite = createAnimatedSprite("/se233/asteroids/assets/playerShip/Idle.png", 1, 1);
        boostSprite = createAnimatedSprite("/se233/asteroids/assets/playerShip/Boost.png", 5, 5);

        getChildren().addAll(idleSprite, boostSprite);

        showAnimation("idle");

//        getChildren().add(playerShip.getImageView());

        setOnKeyPressed(event -> {
            controller.handleKeyPressed(event);
        });

        setOnKeyReleased(event -> {
            controller.handleKeyReleased(event);
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.update();
                updateSpritePositions();
                updateVisibleAnimation();
            }
        }.start();
    }

    private AnimatedSprite createAnimatedSprite(String imagePath, int columns, int rows) {
        Image spriteSheet = new Image(Launcher.class.getResourceAsStream(imagePath));
        AnimatedSprite sprite = new AnimatedSprite(spriteSheet, columns, rows, 1, 0, 0, 192, 192);
        sprite.setFitWidth(100);
        sprite.setFitHeight(100);
        return sprite;
    }

    private void updateSpritePositions() {
        double x = playerShip.getX() - 50;
        double y = playerShip.getY() - 50;
        double rotation = playerShip.getRotate();

        for (AnimatedSprite sprite : new AnimatedSprite[]{idleSprite, boostSprite}) {
            sprite.setX(x);
            sprite.setY(y);
            sprite.setRotate(rotation);
        }
    }

    private void updateVisibleAnimation() {
        String currentAnimation = controller.getCurrentAnimation();
        showAnimation(currentAnimation);

        if (currentAnimation.equals("boost")) {
            boostSprite.tick();
        }
    }

    private void showAnimation(String animationType) {
        idleSprite.setVisible(animationType.equals("idle"));
        boostSprite.setVisible(animationType.equals("boost"));
    }
}
