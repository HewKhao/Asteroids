package se233.asteroids.controller;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.Asteroid;
import se233.asteroids.model.NormalEnemies;
import se233.asteroids.model.PlayerShip;

import java.util.*;

public class NormalEnemiesController {
    private final List<NormalEnemies> enemiesList;
    private final GameStageController gameStageController;

    private Random random = new Random();

    public NormalEnemiesController(GameStageController gameStageController) {
        enemiesList = new ArrayList<>();
        this.gameStageController = gameStageController;
    }

    public int getDeath() {
        return  NormalEnemies.getAmountDeath();
    }

    public void setDeath(int death) {
        NormalEnemies.setAmountDeath(death);
    }

    public void spawnEnemies() {
        if (enemiesList.size() < 7) {
            double maxSpeed = 0.1 + (random.nextDouble() * 2.9);
            NormalEnemies normalEnemies = new NormalEnemies(30, 30, 1, maxSpeed, 0.07, 3, 0.98, 1, gameStageController.getGameStage().getWidth(), gameStageController.getGameStage().getHeight());

            normalEnemies.randomSpawn();
            normalEnemies.initializeRandomDirection();

            enemiesList.add(normalEnemies);
            gameStageController.getGameStage().getChildren().addAll(normalEnemies.getAnimations().values());

            double animatedSpriteWidth = normalEnemies.getAnimations().get("idle").getFitWidth();
            double animatedSpriteHeight = normalEnemies.getAnimations().get("idle").getFitHeight();
            double imageViewWidth = normalEnemies.getImageView().getFitWidth();
            double imageViewHeight = normalEnemies.getImageView().getFitHeight();

            double centerX = normalEnemies.getX() + (animatedSpriteWidth / 2) - (imageViewWidth / 2);
            double centerY = normalEnemies.getY() + (animatedSpriteHeight / 2) - (imageViewHeight / 2);

            normalEnemies.getImageView().setX(centerX);
            normalEnemies.getImageView().setY(centerY);

            Bounds bound = normalEnemies.getImageView().getBoundsInLocal();
            double x = bound.getMinX();
            double y = bound.getMinY();
            double width = bound.getWidth();
            double height = bound.getHeight();

            normalEnemies.outline = new Rectangle(x, y, width, height);
            normalEnemies.outline.setFill(Color.TRANSPARENT);
            normalEnemies.outline.setStroke(Color.RED);
            normalEnemies.outline.setStrokeWidth(2);
            normalEnemies.outline.setVisible(false);

            gameStageController.getGameStage().getChildren().add(normalEnemies.outline);

            // Uncomment code below to show hitbox
            if (gameStageController.isShowHitbox()) {
                normalEnemies.outline.setVisible(true);
            }
        }
    }

    public void removeMarkedEnemies() {
        Iterator<NormalEnemies> iterator = enemiesList.iterator();

        while (iterator.hasNext()) {
            NormalEnemies normalEnemies = iterator.next();

            if (normalEnemies.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().removeAll(normalEnemies.getAnimations().values());
                gameStageController.getGameStage().getChildren().remove(normalEnemies.outline);
            }
        }
    }

    public void updateAnimationVisibility() {
        for (NormalEnemies enemies : enemiesList) {
            Map<String, AnimatedSprite> animations = enemies.getAnimations();
            List<String> currentAnimations = enemies.getCurrentAnimations();

            for (Map.Entry<String, AnimatedSprite> entry : animations.entrySet()) {
                String key = entry.getKey();
                AnimatedSprite animation = entry.getValue();
                animation.setVisible(currentAnimations.contains(key));

                if (animation.isVisible()) {
                    if (animation.isPlayOnce()) {
                        if (animation.getPlayFrameCount() < animation.getTotalFrames()) {
                            animation.tick();
                        } else {
                            enemies.markForRemove();
                        }
                    } else {
                        animation.tick();
                    }
                }
            }
        }
    }

    public void updateAnimationPositions() {
        for (NormalEnemies enemies : enemiesList) {
            double animatedSpriteWidth = enemies.getAnimations().get("idle").getFitWidth();
            double animatedSpriteHeight = enemies.getAnimations().get("idle").getFitHeight();
            double imageViewWidth = enemies.getImageView().getFitWidth();
            double imageViewHeight = enemies.getImageView().getFitHeight();

            double centerX = enemies.getX() + (animatedSpriteWidth / 2) - (imageViewWidth / 2);
            double centerY = enemies.getY() + (animatedSpriteHeight / 2) - (imageViewHeight / 2);

            enemies.getImageView().setX(centerX);
            enemies.getImageView().setY(centerY);

            for (Map.Entry<String, AnimatedSprite> entry : enemies.getAnimations().entrySet()) {
                String key = entry.getKey();
                AnimatedSprite sprite = entry.getValue();

                sprite.setX(enemies.getX());
                sprite.setY(enemies.getY());
            }

            Bounds bound = enemies.getImageView().getBoundsInParent();
            double Bx = bound.getMinX();
            double By = bound.getMinY();
            double width = bound.getWidth();
            double height = bound.getHeight();

            enemies.outline.setX(Bx);
            enemies.outline.setY(By);
            enemies.outline.setWidth(width);
            enemies.outline.setHeight(height);
        }
    }

    public List<NormalEnemies> getEnemiesList() {
        return enemiesList;
    }

    public void checkCollisions(PlayerShip playerShip) {
        Iterator<NormalEnemies> iterator = enemiesList.iterator();

        while (iterator.hasNext()) {
            NormalEnemies enemies = iterator.next();

            enemies.checkPlayerShipCollision(playerShip);
        }
    }

    public void update() {
        if (Math.random() < 0.003) {
            spawnEnemies();
        }
        for (NormalEnemies enemies : enemiesList) {
            enemies.update();
            if (Math.random() < 0.01) {
                enemies.shoot(gameStageController);
            }
        }
        updateAnimationVisibility();
        updateAnimationPositions();
        removeMarkedEnemies();
    }
}
