package se233.asteroids.controller;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.EliteEnemies;
import se233.asteroids.model.PlayerShip;

import java.util.*;

public class EliteEnemiesController {
    private final List<EliteEnemies> enemiesList;
    private final GameStageController gameStageController;

    private Random random = new Random();

    public EliteEnemiesController(GameStageController gameStageController) {
        enemiesList = new ArrayList<>();
        this.gameStageController = gameStageController;
    }

    public void spawnEnemies() {
        if (enemiesList.size() < 1) {
            EliteEnemies eliteEnemies = new EliteEnemies(50, 50, 1, 1, 0.07, 3, 0.98 , 3, gameStageController.getGameStage().getWidth(), gameStageController.getGameStage().getHeight());

            eliteEnemies.spawn();
            eliteEnemies.initializeRandomDirection();

            enemiesList.add(eliteEnemies);
            gameStageController.getGameStage().getChildren().addAll(eliteEnemies.getAnimations().values());

            double animatedSpriteWidth = eliteEnemies.getAnimations().get("flying").getFitWidth();
            double animatedSpriteHeight = eliteEnemies.getAnimations().get("flying").getFitHeight();
            double imageViewWidth = eliteEnemies.getImageView().getFitWidth();
            double imageViewHeight = eliteEnemies.getImageView().getFitHeight();

            double centerX = eliteEnemies.getX() + (animatedSpriteWidth / 2) - (imageViewWidth / 2);
            double centerY = eliteEnemies.getY() + (animatedSpriteHeight / 2) - (imageViewHeight / 2);

            eliteEnemies.getImageView().setX(centerX);
            eliteEnemies.getImageView().setY(centerY);

            Bounds bound = eliteEnemies.getImageView().getBoundsInLocal();
            double x = bound.getMinX();
            double y = bound.getMinY();
            double width = bound.getWidth();
            double height = bound.getHeight();

            eliteEnemies.outline = new Rectangle(x, y, width, height);
            eliteEnemies.outline.setFill(Color.TRANSPARENT);
            eliteEnemies.outline.setStroke(Color.RED);
            eliteEnemies.outline.setStrokeWidth(2);
            eliteEnemies.outline.setVisible(false);

            gameStageController.getGameStage().getChildren().add(eliteEnemies.outline);

            // Uncomment code below to show hitbox
            if (gameStageController.isShowHitbox()) {
                eliteEnemies.outline.setVisible(true);
            }
        }
    }

    public void removeMarkedEnemies() {
        Iterator<EliteEnemies> iterator = enemiesList.iterator();

        while (iterator.hasNext()) {
            EliteEnemies eliteEnemies = iterator.next();

            if (eliteEnemies.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().removeAll(eliteEnemies.getAnimations().values());
                gameStageController.getGameStage().getChildren().remove(eliteEnemies.outline);
            }
        }
    }

    public void updateAnimationVisibility() {
        for (EliteEnemies enemies : enemiesList) {
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
                            if (enemies.isDead()) {
                                enemies.markForRemove();
                                gameStageController.incrementScore(5);
                            }
                            currentAnimations.remove(key);
                            animation.reset();
                        }
                    } else {
                        animation.tick();
                    }
                }
            }
        }
    }

    public void updateAnimationPositions() {
        for (EliteEnemies enemies : enemiesList) {
            double animatedSpriteWidth = enemies.getAnimations().get("flying").getFitWidth();
            double animatedSpriteHeight = enemies.getAnimations().get("flying").getFitHeight();
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

    public List<EliteEnemies> getEnemiesList() {
        return enemiesList;
    }

    public void checkCollisions(PlayerShip playerShip) {
        Iterator<EliteEnemies> iterator = enemiesList.iterator();

        while (iterator.hasNext()) {
            EliteEnemies enemies = iterator.next();

            enemies.checkPlayerShipCollision(playerShip);
        }
    }

    public void update() {
        if (gameStageController.getNormalEnemiesController().getDeath() >= gameStageController.getEliteRequirement()) {
            spawnEnemies();
            gameStageController.getNormalEnemiesController().setDeath(0);
        }
        for (EliteEnemies enemies : enemiesList) {
            enemies.update();
            if (Math.random() < 0.005) {
                enemies.initializeRandomDirection();
            }
        }
        updateAnimationVisibility();
        updateAnimationPositions();
        removeMarkedEnemies();
    }
}
