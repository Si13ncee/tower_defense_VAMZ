package com.example.tower_defense.game_menu.spirtesControl.Towers;

import android.graphics.Canvas;

import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.GameActivity;


public class TowerManager {
    private tower[][] towers = new tower[GameActivity.getMapSizeY()][GameActivity.getMapSizeX()];
    public TowerManager() {

    }

    public boolean addTower(int posX, int posY, towerList sprite) {
        System.out.println(posX + " " + posY);
        synchronized (this.towers) {
            if (this.towers[posY][posX] == null) {
                this.towers[posY][posX] = new tower(posX * Constants.Dimensions.SIZE_POLICKA_X, posY * Constants.Dimensions.SIZE_POLICKA_Y, sprite.getSprite(), 5);
                return true;
            } else {
                return false;
            }
        }
    }

    public void render(Canvas c) {
    synchronized (this.towers) {
        for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
            for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {

                if (this.towers[posY][posX] != null) {
                    c.drawBitmap(this.towers[posY][posX].getTowerSprite(), this.towers[posY][posX].getPosX(), this.towers[posY][posX].getPosY(), null);
                }

            }
        }
    }
    }

}
