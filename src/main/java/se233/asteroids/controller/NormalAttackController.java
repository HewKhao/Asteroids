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

    Rectangle outline;

    public NormalAttackController(GameStageController gameStageController) {
        this.normalAttackList = new ArrayList<NormalAttack>();
        this.gameStageController = gameStageController;
    }

    public List<NormalAttack> getNormalAttackList() {
        return this.normalAttackList;
    }

    public void addNormalAttack(NormalAttack attack) {
        normalAttackList.add(attack);
        gameStageController.getGameStage().getChildren().add(attack.getAnimatedSprite());

        Bounds bound = attack.getAnimatedSprite().getBoundsInParent();
        double x = bound.getMinX();
        double y = bound.getMinY();
        double width = bound.getWidth();
        double height = bound.getHeight();

        outline = new Rectangle(x, y, width, height);
        outline.setFill(Color.TRANSPARENT); // no fill inside
        outline.setStroke(Color.RED); // outline color
        outline.setStrokeWidth(2);

        // Uncomment code below to show hitbox
//        gameStageController.getGameStage().getChildren().add(outline);
    }

    public void removeMarkedNormalAttack() {
        Iterator<NormalAttack> iterator = normalAttackList.iterator();

        while (iterator.hasNext()) {
            NormalAttack attack = iterator.next();

            if (attack.isMarkForRemove()) {
                iterator.remove();
                gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
            }
        }
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
            double Bx = bound.getMinX();
            double By = bound.getMinY();
            double width = bound.getWidth();
            double height = bound.getHeight();

            outline.setX(Bx);
            outline.setY(By);
            outline.setWidth(width);
            outline.setHeight(height);
        }
    }

    public void checkCollisions(List<? extends Character> characters) {
        Iterator<NormalAttack> attackIterator = normalAttackList.iterator();

        while (attackIterator.hasNext()) {
            NormalAttack attack = attackIterator.next();

            if (attack.checkCollision(characters)) {
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


//    public void checkCollisions(List<? extends Character> characters) {
//        for (NormalAttack attack : normalAttackList) {
//
//            if (attack.checkCollision(characters)) {
//                normalAttackList.remove(attack);
//                gameStageController.getGameStage().getChildren().remove(attack.getAnimatedSprite());
//                attack.explode();
//                gameStageController.getGameStage().getChildren().add(attack.getAnimatedSprite());
////                attack.markForRemoval();
//            }
//        }
//    }

    public void update() {
        updateAnimaton();
        updateAnimationPositions();
        removeMarkedNormalAttack();
    }
}
