package se233.asteroids.controller;

import se233.asteroids.model.NormalAttack;

import java.util.ArrayList;
import java.util.List;

public class NormalAttackController {
    private NormalAttack normalAttack;
    private List<NormalAttack> normalAttackList;

    public NormalAttackController(NormalAttack normalAttack) {
        this.normalAttack = normalAttack;
        this.normalAttackList = new ArrayList<NormalAttack>();
    }

    public List<NormalAttack> getNormalAttackList() {
        return this.normalAttackList;
    }

    public void update() {

    }
}
