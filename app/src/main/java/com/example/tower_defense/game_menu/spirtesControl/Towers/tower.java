package com.example.tower_defense.game_menu.spirtesControl.Towers;

import android.graphics.Bitmap;

import com.example.tower_defense.game_menu.Constants;

public class tower {
    private int posX;
    private int posY;
    private Bitmap towerSprite;
    private int damage;
    private float rangeRadius;
    private int level = 1;


    public tower(int posX, int posY, Bitmap towerType, int rangeRadius) {

        this.towerSprite = towerType;
        this.rangeRadius = rangeRadius;
        this.posX = posX;
        this.posY = posY - towerType.getHeight() + Constants.Dimensions.SIZE_POLICKA_X;

    }

    public Bitmap getTowerSprite() {
        return this.towerSprite;
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
