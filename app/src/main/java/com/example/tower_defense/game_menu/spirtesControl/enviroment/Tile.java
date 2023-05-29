package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import com.example.tower_defense.game_menu.spirtesControl.entities.enemiesList;

public class Tile {

    private int posX;
    private int posY;
    private ETileType tileType;

    public Tile(int posX, int posY, ETileType type) {
        this.posX = posX;
        this.posY = posY;
        this.tileType = type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public ETileType getTileType() {
        return this.tileType;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}