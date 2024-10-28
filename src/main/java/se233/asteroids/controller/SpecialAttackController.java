package se233.asteroids.controller;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.asteroids.model.Character;
import se233.asteroids.model.NormalAttack;
import se233.asteroids.model.SpecialAttack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpecialAttackController {
    private List<SpecialAttack> specialAttacksList;
    private GameStageController gameStageController;

    public SpecialAttackController(GameStageController gameStageController) {
        this.specialAttacksList = new ArrayList<>();
        this.gameStageController = gameStageController;
    }

    public List<SpecialAttack> getSpecialAttacksList() {
        return this.specialAttacksList;
    }

    public void addSpecialAttack(SpecialAttack attack) {
        specialAttacksList.add(attack);

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

        if (gameStageController.isShowHitbox()) {
            attack.outline.setVisible(true);
        }
    }

    public void removeMarkedSpecialAttack() {
        Iterator<SpecialAttack> iterator = specialAttacksList.iterator();

        while (iterator.hasNext()) {
            SpecialAttack attack = iterator.next();

            if (attack.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
                gameStageController.getGameStage().getChildren().remove(attack.outline);
            }
        }
    }

    public void removeSpecialAttack(SpecialAttack attack) {
        gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
        gameStageController.getGameStage().getChildren().remove(attack.outline);
    }

    public void updateAnimation() {
        for (SpecialAttack attack : specialAttacksList) {
            attack.getAnimatedSprite().tick();
            attack.update();
        }
    }

    public void updateAnimationPositions() {
        for (SpecialAttack attack : specialAttacksList) {
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
        Iterator<SpecialAttack> attackIterator = specialAttacksList.iterator();

        while (attackIterator.hasNext()) {
            SpecialAttack attack = attackIterator.next();

            attack.checkCollision(characters, gameStageController);
        }
    }

    public void update() {
        updateAnimation();
        updateAnimationPositions();
        removeMarkedSpecialAttack();
    }
}
