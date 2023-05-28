package com.example.tower_defense.game_menu.spirtesControl.entities;

import android.graphics.Bitmap;

public class entity {

    private int posX;
    private int posY;
    private enemiesList enemyType;

    public entity(int posX, int posY, enemiesList type) {
        this.posX = posX;
        this.posY = posY;
        this.enemyType = type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public enemiesList getEnemyType () {
        return this.enemyType;
    }
}
