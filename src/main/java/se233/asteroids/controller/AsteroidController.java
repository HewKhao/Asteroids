package se233.asteroids.controller;

import javafx.animation.Animation;
import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.Asteroid;
import se233.asteroids.view.GameStage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AsteroidController {
    private final List<Asteroid> asteroidList;
    private final GameStage gameStage;

    public AsteroidController(GameStage gameStage) {
        asteroidList = new ArrayList<>();
        this.gameStage = gameStage;
    }

    public void spawnAsteroids() {
        if (asteroidList.size() < 15) {
            Asteroid asteroid = new Asteroid(38, 38, 1, 1.5, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());

            asteroid.randomSpawn();
            asteroid.initializeRandomDirection();

            asteroidList.add(asteroid);
//            gameStage.getChildren().add(asteroid.getImageView());
            gameStage.getChildren().add(asteroid.getAnimations().get("Idle"));
        }
    }

    public void updateAnimationPositions() {
        for (Asteroid asteroid : asteroidList) {
            for (Map.Entry<String, AnimatedSprite> entry : asteroid.getAnimations().entrySet()) {
                String key = entry.getKey();
                AnimatedSprite sprite = entry.getValue();

                sprite.setX(asteroid.getX());
                sprite.setY(asteroid.getY());
            }
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
        updateAnimationPositions();
    }

}
