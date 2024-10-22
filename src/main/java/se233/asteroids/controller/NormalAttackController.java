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

    public void update() {
        for (NormalAttack attack : normalAttackList) {
            attack.update();
        }
    }
}
