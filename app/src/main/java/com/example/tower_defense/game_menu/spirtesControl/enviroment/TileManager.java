package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import android.graphics.Canvas;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;
import com.example.tower_defense.game_menu.spirtesControl.player.EPlayerControl;

import java.util.Random;

public class TileManager implements IBitMapFunctions {


    private static final int Ypolicka = (int) (32 * GameActivity.getScalingY());
    private static final int Xpolicka = (int) (32 * GameActivity.getScalingX());

    private final Tile[][] tiles = new Tile[GameActivity.getMapSizeY()][GameActivity.getMapSizeX()];
    private int tyleSelected = 0;
    private Tile selectedTyle;
    private final EPlayerControl[] selectionAnimation = new EPlayerControl[3];


    public TileManager() {
        System.out.println(this.Ypolicka);
        createMap();
        createAnimation();
    }

    private void createAnimation() {
        this.selectionAnimation[0] = EPlayerControl.SELECTED1;
        this.selectionAnimation[1] = EPlayerControl.SELECTED2;
        this.selectionAnimation[2] = EPlayerControl.SELECTED3;
    }

    private void createMap() {
        //40x25 rozmery
        Random rand = new Random();
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    if (rand.nextInt(2) == 0) {
                        this.createTile(ETileType.GRASS, posX,  posY);
                    } else {
                        this.createTile(ETileType.ROAD, posX, posY);
                    }

                }
            }
        }
    }

    public void createTile(ETileType type, int posX, int posY){
        synchronized (this.tiles) {
            this.tiles[posY][posX] = new Tile(this.Xpolicka * posX, this.Ypolicka * posY, type);
        }
    }

    public void renderTiles(Canvas c) {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++){
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    c.drawBitmap(this.tiles[posY][posX].getTileType().getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                    if (this.tiles[posY][posX].isSelected()) {
                        this.tyleSelected++;
                        if (0 <= this.tyleSelected && !(this.tyleSelected > 15)) {
                            c.drawBitmap(this.selectionAnimation[0].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                        } else if (15 <= this.tyleSelected && !(this.tyleSelected > 30)) {
                            c.drawBitmap(this.selectionAnimation[1].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                        } else if (30 <= this.tyleSelected && !(this.tyleSelected > 45)) {
                            c.drawBitmap(this.selectionAnimation[2].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                        } else if (45 <= this.tyleSelected && !(this.tyleSelected > 60)) {
                            c.drawBitmap(this.selectionAnimation[1].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                        } else {
                            c.drawBitmap(this.selectionAnimation[0].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                            this.tyleSelected = 0;
                        }
                    }
                }

            }
        }
    }

    public Tile getTile(int posY, int posX) {
        return this.tiles[posY][posX];
    }

    public void unselectTile() {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++){
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    if (this.tiles[posY][posX].isSelected()) {
                        this.tiles[posY][posX].setSelected(false);
                    }
                }
            }
        }
    }

    public Tile getSelectedTyle() {
        return selectedTyle;
    }

    public void setSelectedTyle(Tile selectedTyle) {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++){
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    if (this.tiles[posY][posX] == selectedTyle) {
                        this.tiles[posY][posX].setSelected(true);
                    }
                }
            }
        }
        this.selectedTyle = selectedTyle;
    }
}
