package se233.asteroids.controller;

import javafx.animation.Animation;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.Asteroid;
import se233.asteroids.model.NormalAttack;
import se233.asteroids.view.GameStage;

import java.util.*;

public class AsteroidController {
    private final List<Asteroid> asteroidList;
    private final GameStageController gameStageController;

    private Random random = new Random();

    public AsteroidController(GameStageController gameStageController) {
        asteroidList = new ArrayList<>();
        this.gameStageController = gameStageController;
    }

    public void spawnAsteroids() {
        if (asteroidList.size() < 15) {
            double maxSpeed = 0.1 + (random.nextDouble() * 1.9);
            Asteroid asteroid = new Asteroid(38, 38, 1, maxSpeed, 0.05, 2, 0.98, 100, gameStageController.getGameStage().getWidth(), gameStageController.getGameStage().getHeight());

            asteroid.randomSpawn();
            asteroid.initializeRandomDirection();

            asteroidList.add(asteroid);
//            gameStageController.getGameStage().getChildren().add(asteroid.getImageView());
            gameStageController.getGameStage().getChildren().addAll(asteroid.getAnimations().values());

            double animatedSpriteWidth = asteroid.getAnimations().get("idle").getFitWidth();
            double animatedSpriteHeight = asteroid.getAnimations().get("idle").getFitHeight();
            double imageViewWidth = asteroid.getImageView().getFitWidth();
            double imageViewHeight = asteroid.getImageView().getFitHeight();

            double centerX = asteroid.getX() + (animatedSpriteWidth / 2) - (imageViewWidth / 2);
            double centerY = asteroid.getY() + (animatedSpriteHeight / 2) - (imageViewHeight / 2);

            asteroid.getImageView().setX(centerX);
            asteroid.getImageView().setY(centerY);

            Bounds bound = asteroid.getImageView().getBoundsInLocal();
            double x = bound.getMinX();
            double y = bound.getMinY();
            double width = bound.getWidth();
            double height = bound.getHeight();

            asteroid.outline = new Rectangle(x, y, width, height);
            asteroid.outline.setFill(Color.TRANSPARENT);
            asteroid.outline.setStroke(Color.RED);
            asteroid.outline.setStrokeWidth(2);
            asteroid.outline.setVisible(false);

            gameStageController.getGameStage().getChildren().add(asteroid.outline);

            // Uncomment code below to show hitbox
            if (gameStageController.isShowHitbox()) {
                asteroid.outline.setVisible(true);
            }
        }
    }

    public void removeMarkedAsteroids() {
        Iterator<Asteroid> iterator = asteroidList.iterator();

        while (iterator.hasNext()) {
            Asteroid asteroid = iterator.next();

            if (asteroid.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().removeAll(asteroid.getAnimations().values());
                gameStageController.getGameStage().getChildren().remove(asteroid.outline);
            }
        }
    }

    public void updateAnimationVisibility() {
        for (Asteroid asteroid : asteroidList) {
            Map<String, AnimatedSprite> animations = asteroid.getAnimations();
            List<String> currentAnimations = asteroid.getCurrentAnimations();

            for (Map.Entry<String, AnimatedSprite> entry : animations.entrySet()) {
                String key = entry.getKey();
                AnimatedSprite animation = entry.getValue();
                animation.setVisible(currentAnimations.contains(key));

                if (animation.isVisible()) {
                    if (animation.isPlayOnce()) {
                        if (animation.getPlayFrameCount() < animation.getTotalFrames()) {
                            animation.tick();
                        } else {
                            asteroid.markForRemove();
                        }
                    }
                }
            }
        }
    }

    public void updateAnimationPositions() {
        for (Asteroid asteroid : asteroidList) {
            double animatedSpriteWidth = asteroid.getAnimations().get("idle").getFitWidth();
            double animatedSpriteHeight = asteroid.getAnimations().get("idle").getFitHeight();
            double imageViewWidth = asteroid.getImageView().getFitWidth();
            double imageViewHeight = asteroid.getImageView().getFitHeight();

            double centerX = asteroid.getX() + (animatedSpriteWidth / 2) - (imageViewWidth / 2);
            double centerY = asteroid.getY() + (animatedSpriteHeight / 2) - (imageViewHeight / 2);

            asteroid.getImageView().setX(centerX);
            asteroid.getImageView().setY(centerY);

            for (Map.Entry<String, AnimatedSprite> entry : asteroid.getAnimations().entrySet()) {
                String key = entry.getKey();
                AnimatedSprite sprite = entry.getValue();

                sprite.setX(asteroid.getX());
                sprite.setY(asteroid.getY());
            }

            Bounds bound = asteroid.getImageView().getBoundsInParent();
            double Bx = bound.getMinX();
            double By = bound.getMinY();
            double width = bound.getWidth();
            double height = bound.getHeight();

            asteroid.outline.setX(Bx);
            asteroid.outline.setY(By);
            asteroid.outline.setWidth(width);
            asteroid.outline.setHeight(height);
        }
    }

    public List<Asteroid> getAsteroidList() {
        return asteroidList;
    }

    public void update() {
        if (Math.random() < 0.01) {
            spawnAsteroids();
        }
        for (Asteroid asteroid : asteroidList) {
            asteroid.update();
        }
        updateAnimationVisibility();
        updateAnimationPositions();
        removeMarkedAsteroids();
    }

}
