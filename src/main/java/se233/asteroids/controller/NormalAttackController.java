package se233.asteroids.controller;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.asteroids.model.Character;
import se233.asteroids.model.NormalAttack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NormalAttackController {
    private List<NormalAttack> normalAttackList;
    private GameStageController gameStageController;

    public NormalAttackController(GameStageController gameStageController) {
        this.normalAttackList = new ArrayList<NormalAttack>();
        this.gameStageController = gameStageController;
    }

    public List<NormalAttack> getNormalAttackList() {
        return this.normalAttackList;
    }

    public void addNormalAttack(NormalAttack attack) {
        normalAttackList.add(attack);

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

    public void removeMarkedNormalAttack() {
        Iterator<NormalAttack> iterator = normalAttackList.iterator();

        while (iterator.hasNext()) {
            NormalAttack attack = iterator.next();

            if (attack.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
                gameStageController.getGameStage().getChildren().remove(attack.outline);
            }
        }
    }

    public void removeNormalAttack(NormalAttack attack) {
        gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
        gameStageController.getGameStage().getChildren().remove(attack.outline);
    }

    public void updateAnimaton() {
        for (NormalAttack attack : normalAttackList) {
            attack.getAnimatedSprite().tick();
            attack.update();
        }
    }

    public void updateAnimationPositions() {
        for (NormalAttack attack : normalAttackList) {
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
        Iterator<NormalAttack> attackIterator = normalAttackList.iterator();

        while (attackIterator.hasNext()) {
            NormalAttack attack = attackIterator.next();

            if (attack.checkCollision(characters,gameStageController)) {
                attack.setMaxSpeed(0);
                removeNormalAttack(attack);
                attack.markForRemoval();

                double projX = attack.getX();
                double projY = attack.getY();
                double rotation = attack.getRotate();

                double offsetX = 30;
                double offsetY = 0;

                double radians = Math.toRadians(rotation);
                double spawnX = projX + (offsetX * Math.cos(radians)) - (offsetY * Math.sin(radians));
                double spawnY = projY + (offsetX * Math.sin(radians)) + (offsetY * Math.cos(radians));

                gameStageController.getExplosionController().addExplosion(spawnX, spawnY);
            }
        }
    }

    public void update() {
        updateAnimaton();
        updateAnimationPositions();
        removeMarkedNormalAttack();
    }
}
