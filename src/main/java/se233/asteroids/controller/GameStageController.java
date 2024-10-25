package se233.asteroids.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.Asteroid;
import se233.asteroids.model.NormalAttack;
import se233.asteroids.model.PlayerShip;
import se233.asteroids.view.GameStage;

import java.util.*;

public class GameStageController {
    private GameStage gameStage;
    private PlayerShip playerShip;
    private Asteroid asteroid;
    private PlayerShipController playerShipController;
    private NormalAttack normalAttack;
    private NormalAttackController normalAttackController;
    private List<Asteroid> asteroidList;
    private Random random = new Random();

    public GameStageController(GameStage gameStage) {
        this.gameStage = gameStage;
        double centerX = (double) gameStage.getWidthValue() / 2;
        double centerY = (double) gameStage.getHeightValue() / 2;

        this.playerShip = new PlayerShip(centerX, centerY, 1, 5,  0.2, 3.0, 0.99, 1, gameStage.getWidthValue(), gameStage.getHeightValue());
        this.playerShip.setRotate(-90);
        this.playerShipController = new PlayerShipController(playerShip, this);

        this.normalAttackController = new NormalAttackController(gameStage.getWidth(), gameStage.getHeight());

        this.asteroidList = new ArrayList<>();

        Map<String, AnimatedSprite> playerShipAnimations = playerShip.getAnimations();
        gameStage.getChildren().addAll(playerShipAnimations.values());

//        gameStage.getChildren().addAll(
//                playerShip.getAnimations().get("idle"),
//                playerShip.getAnimations().get("boost"),
//                playerShip.getAnimations().get("shoot"),
//                playerShip.getAnimations().get("fire")
//        );

    }

    public void addAsteroid(Asteroid asteroid) {
        asteroidList.add(asteroid);
        gameStage.getChildren().add(asteroid.getImageView());
    }

    public void addNormalAttack(NormalAttack attack) {
        normalAttackController.getNormalAttackList().add(attack);
//        gameStage.getChildren().add(attack.getImageView());
        gameStage.getChildren().add(attack.getAnimatedSprite());
    }

    public void updateAsteroids() {
        Iterator<Asteroid> iterator = asteroidList.iterator();
        while (iterator.hasNext()) {
            Asteroid asteroid = iterator.next();
            asteroid.update();
        }
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


    public void update() {
        playerShipController.update();
        normalAttackController.update();
        updateAsteroids();
//        removeOutOfBoundsNormalAttack();
        removeMarkedNormalAttack();
    }

    public void startGameLoop() {
        final double frameRate = 60.0;
        final double interval = 1000 / frameRate;
        final double buffer = 50;

        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(interval), event -> {
            update();

            //random spawn asteroid
            if (Math.random() < 0.01 && asteroidList.size() < 20){ // limit spawn
                this.asteroid = new Asteroid(96,96,1,1.5,0.05,2,0.98,100,gameStage.getWidth(),gameStage.getHeight());
                asteroid.randomSpawn(50);
                asteroid.initializeRandomDirection();
                addAsteroid(asteroid);
            }
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }
}