package se233.asteroids.controller;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.asteroids.model.Character;
import se233.asteroids.model.EliteAttack;
import se233.asteroids.model.EnemiesAttack;
import se233.asteroids.model.PlayerShip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EliteAttackController {
    private List<EliteAttack> eliteAttackList;
    private GameStageController gameStageController;

    public EliteAttackController(GameStageController gameStageController) {
        this.eliteAttackList = new ArrayList<>();
        this.gameStageController = gameStageController;
    }

    public List<EliteAttack> getEliteAttackList() {
        return eliteAttackList;
    }

    public void addEliteAttack(EliteAttack attack) {
        eliteAttackList.add(attack);

        Bounds bound = attack.getAnimatedSprite().getBoundsInParent();
        double centerX = attack.getX() - (bound.getWidth() / 2);
        double centerY = attack.getY() - (bound.getHeight() / 2);
        double width = bound.getWidth();
        double height = bound.getHeight();

        attack.outline = new Rectangle(centerX, centerY, width, height);
        attack.outline.setFill(Color.TRANSPARENT);
        attack.outline.setStroke(Color.RED);
        attack.outline.setStrokeWidth(2);
        attack.outline.setVisible(false);

        gameStageController.getGameStage().getChildren().add(attack.getAnimatedSprite());
        gameStageController.getGameStage().getChildren().add(attack.outline);

        // Uncomment code below to show hitbox
        if (gameStageController.isShowHitbox()) {
            attack.outline.setVisible(true);
        }
    }

    public void removeMarkedEnemiesAttack() {
        Iterator<EliteAttack> iterator = eliteAttackList.iterator();

        while (iterator.hasNext()) {
            EliteAttack attack = iterator.next();

            if (attack.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
                gameStageController.getGameStage().getChildren().remove(attack.outline);
            }
        }
    }

    public void removeEnemiesAttack(EliteAttack attack) {
        gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
        gameStageController.getGameStage().getChildren().remove(attack.outline);
    }

    public void updateAnimation() {
        for (EliteAttack attack : eliteAttackList) {
            attack.getAnimatedSprite().tick();
            attack.update();
        }
    }

    public void updateAnimationPosition() {
        for (EliteAttack attack : eliteAttackList) {
            double x = attack.getX();
            double y = attack.getY();
            double rotation = attack.getRotate();

            attack.getAnimatedSprite().setX(x);
            attack.getAnimatedSprite().setY(y);
            attack.getAnimatedSprite().setRotate(rotation);

            Bounds bound = attack.getAnimatedSprite().getBoundsInParent();
            double centerX = attack.getX() - (bound.getWidth() / 2);
            double centerY = attack.getY() - (bound.getHeight() / 2);
            double width = bound.getWidth();
            double height = bound.getHeight();

            attack.outline.setX(centerX);
            attack.outline.setY(centerY);
            attack.outline.setWidth(width);
            attack.outline.setHeight(height);
        }
    }

    public void checkCollisions(List<? extends Character> characters) {
        Iterator<EliteAttack> iterator = eliteAttackList.iterator();

        while (iterator.hasNext()) {
            EliteAttack attack = iterator.next();

            if (attack.checkCollision(characters)) {
                attack.setMaxSpeed(0);
                removeEnemiesAttack(attack);
                attack.markForRemove();
                attack.outline.setVisible(false);
            }
        }
    }

    public void checkPlayerShipCollision(PlayerShip playerShip) {
        Iterator<EliteAttack> iterator = eliteAttackList.iterator();

        while (iterator.hasNext()) {
            EliteAttack attack = iterator.next();

            attack.checkPlayerShipCollision(playerShip);
        }
    }

    public void update() {
        updateAnimation();
        updateAnimationPosition();
        removeMarkedEnemiesAttack();
    }
}
