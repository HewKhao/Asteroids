package se233.asteroids.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.NormalAttack;
import se233.asteroids.model.PlayerShip;
import se233.asteroids.view.GameStage;

import java.util.*;

public class GameStageController {
    private GameStage gameStage;
    private PlayerShip playerShip;
    private PlayerShipController playerShipController;
    private NormalAttack normalAttack;
    private NormalAttackController normalAttackController;
    private AsteroidController asteroidController;
    private ExplosionController explosionController;

    private boolean showHitbox = true;

    public NormalAttackController getNormalAttackController() {
        return normalAttackController;
    }

    public ExplosionController getExplosionController() {
        return explosionController;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public boolean isShowHitbox() {
        return showHitbox;
    }

    public GameStageController(GameStage gameStage) {
        this.gameStage = gameStage;
        double centerX = (double) gameStage.getWidthValue() / 2;
        double centerY = (double) gameStage.getHeightValue() / 2;

        this.playerShip = new PlayerShip(centerX, centerY, 1, 5,  0.2, 3.0, 0.99, 1, gameStage.getWidthValue(), gameStage.getHeightValue());
        this.playerShip.setRotate(-90);
        this.playerShipController = new PlayerShipController(playerShip, this);

        this.normalAttackController = new NormalAttackController(this);

        this.asteroidController = new AsteroidController(this);

        this.explosionController = new ExplosionController(this);

        Map<String, AnimatedSprite> playerShipAnimations = playerShip.getAnimations();
        gameStage.getChildren().addAll(playerShipAnimations.values());
    }

//    public void removeOutOfBoundsNormalAttack() {
//        Iterator<NormalAttack> iterator = normalAttackController.getNormalAttackList().iterator();
//
//        while (iterator.hasNext()) {
//            NormalAttack attack = iterator.next();
//            if (attack.checkWallCollisions()) {
//                iterator.remove();
//                gameStage.getChildren().remove(attack.getImageView());
//            }
//        }
//    }

    public void removeMarkedNormalAttack() {
        Iterator<NormalAttack> iterator = normalAttackController.getNormalAttackList().iterator();

        while (iterator.hasNext()) {
            NormalAttack attack = iterator.next();
            attack.update();

            if (attack.isMarkForRemove()) {
                iterator.remove();
                gameStage.getChildren().remove(attack.getAnimatedSprite());
            }
        }
    }

    public void handleKeyPressed(KeyEvent event) {
        playerShipController.handleKeyPressed(event);
    }

    public void handleKeyReleased(KeyEvent event) {
        playerShipController.handleKeyReleased(event);
    }

    public void updateCollision() {
        normalAttackController.checkCollisions(asteroidController.getAsteroidList());
    }

    public void update() {
        playerShipController.update();
        normalAttackController.update();
        asteroidController.update();
        explosionController.update();

        updateCollision();

//        removeOutOfBoundsNormalAttack();
        removeMarkedNormalAttack();
    }

    public void startGameLoop() {
        final double frameRate = 60.0;
        final double interval = 1000 / frameRate;

        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(interval), event -> {
            update();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
}