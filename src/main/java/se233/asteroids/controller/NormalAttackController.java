package se233.asteroids.controller;

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
        gameStageController.getGameStage().getChildren().add(attack.getAnimatedSprite());
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
        }
    }

    public void checkCollisions(List<? extends Character> characters) {
        Iterator<NormalAttack> attackIterator = normalAttackList.iterator();

        while (attackIterator.hasNext()) {
            NormalAttack attack = attackIterator.next();

            if (attack.checkCollision(characters)) {
                gameStageController.getExplosionController().addExplosion(attack.getX(), attack.getY());
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
