package se233.asteroids.controller;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import se233.asteroids.Launcher;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.PlayerShip;
import se233.asteroids.view.GameStage;

public class GameStageController {
    private PlayerShip playerShip;
    private PlayerShipController playerShipController;
    private AnimatedSprite idleSprite;
    private AnimatedSprite boostSprite;

    public GameStageController(GameStage gameStage) {
        double centerX = (double) gameStage.getWidthValue() / 2;
        double centerY = (double) gameStage.getHeightValue() / 2;

        playerShip = new PlayerShip(centerX, centerY, 1, 1, gameStage.getWidthValue(), gameStage.getHeightValue());
        playerShip.setRotate(-90);
        playerShipController = new PlayerShipController(playerShip);

        idleSprite = createAnimatedSprite("/se233/asteroids/assets/playerShip/Idle.png", 1, 1);
        boostSprite = createAnimatedSprite("/se233/asteroids/assets/playerShip/Boost.png", 5, 5);

        showAnimation("idle");
    }

    public void update() {
        playerShipController.update();
        updateSpritePositions();
        updateAnimationVisibility();
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

    private void updateAnimationVisibility() {
        String currentAnimation = playerShipController.getCurrentAnimation();
        showAnimation(currentAnimation);

        if (currentAnimation.equals("boost")) {
            boostSprite.tick();
        }
    }

    private void showAnimation(String animationType) {
        idleSprite.setVisible(animationType.equals("idle"));
        boostSprite.setVisible(animationType.equals("boost"));
    }

    public AnimatedSprite getIdleSprite() {
        return idleSprite;
    }

    public AnimatedSprite getBoostSprite() {
        return boostSprite;
    }

    public void handleKeyPressed(KeyEvent event) {
        playerShipController.handleKeyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        playerShipController.handleKeyReleased(event);
    }
}