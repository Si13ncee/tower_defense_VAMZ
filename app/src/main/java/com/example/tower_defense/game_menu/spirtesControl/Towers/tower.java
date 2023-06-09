package com.example.tower_defense.game_menu.spirtesControl.Towers;

public class tower {
    private int posX;
    private int posY;
    private towerList towerType;
    private int damage;
    private float rangeRadius;
    private int level = 1;


    public tower(int posX, int posY, towerList towerType, int rangeRadius) {

        this.posX = posX;
        this.posY = posY;
        this.towerType = towerType;
        this.rangeRadius = rangeRadius;

    }

    public towerList getTowerType() {
        return this.towerType;
    }

    public float getPosX() {
        return this.posX;
    }

    public float getPosY() {
        return this.posY;
    }

    public boolean upgradeTower() {
        this.level++;
        return true;
    }
}
