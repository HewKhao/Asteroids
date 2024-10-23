package se233.asteroids.controller;

import se233.asteroids.model.NormalAttack;

import java.util.ArrayList;
import java.util.List;

public class NormalAttackController {
    private List<NormalAttack> normalAttackList;

    public NormalAttackController(double gameWidth, double gameHeight) {
        this.normalAttackList = new ArrayList<NormalAttack>();
    }

    public List<NormalAttack> getNormalAttackList() {
        return this.normalAttackList;
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

    public void update() {
        updateAnimaton();
        updateAnimationPositions();
    }
}
