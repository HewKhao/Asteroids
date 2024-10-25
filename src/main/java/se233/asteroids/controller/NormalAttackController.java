package se233.asteroids.controller;

import javafx.scene.Node;
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

            attack.checkCollision(characters);
        }
    }

    public void update() {
        updateAnimaton();
        updateAnimationPositions();
    }
}
