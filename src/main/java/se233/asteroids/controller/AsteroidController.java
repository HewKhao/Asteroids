package se233.asteroids.controller;

import se233.asteroids.model.Asteroid;
import se233.asteroids.view.GameStage;

import java.util.ArrayList;
import java.util.List;

public class AsteroidController {
    private final List<Asteroid> asteroidList;
    private final GameStage gameStage;

    public List<Asteroid> getAsteroidList() {
        return asteroidList;
    }

    public AsteroidController(GameStage gameStage) {
        asteroidList = new ArrayList<>();
        this.gameStage = gameStage;
    }

    public void spawnAsteroids() {
        if (asteroidList.size() < 15) {
            Asteroid asteroid = new Asteroid(96, 96, 1, 1.5, 0.05, 2, 0.98, 100, gameStage.getWidth(), gameStage.getHeight());

            asteroid.randomSpawn();
            asteroid.initializeRandomDirection();

            asteroidList.add(asteroid);
            gameStage.getChildren().add(asteroid.getImageView());
        }
    }

    public void update() {
        if (Math.random() < 0.01) {
            spawnAsteroids();
        }
        for (Asteroid asteroid : asteroidList) {
            asteroid.update();
        }
    }

}
