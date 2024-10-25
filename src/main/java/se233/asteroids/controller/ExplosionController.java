package se233.asteroids.controller;

import se233.asteroids.model.AnimatedSprite;
import se233.asteroids.model.Explosion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExplosionController {
    private final List<Explosion> explosionList;
    private GameStageController gameStageController;

    public ExplosionController(GameStageController gameStageController) {
        this.explosionList = new ArrayList<>();
        this.gameStageController = gameStageController;
    }

    public void addExplosion(double x, double y) {
        Explosion explosion = new Explosion(x, y);
        explosionList.add(explosion);
        gameStageController.getGameStage().getChildren().add(explosion.getAnimatedSprite());
    }

    public void removeMarkedExplosion() {
        Iterator<Explosion> iterator = explosionList.iterator();

        while (iterator.hasNext()) {
            Explosion explosion= iterator.next();

            if (explosion.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().remove(explosion.getAnimatedSprite());
            }
        }
    }

    public void updateAnimation() {
        for (Explosion explosion : explosionList) {
            AnimatedSprite animation = explosion.getAnimatedSprite();

            if (animation.getPlayFrameCount() < animation.getTotalFrames()) {
                animation.tick();
            } else {
                explosion.markForRemoval();
            }
        }
    }

    public void update() {
        updateAnimation();
        removeMarkedExplosion();
    }
}
