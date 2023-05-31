package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import static com.example.tower_defense.game_menu.Constants.Dimensions.Xpolicka;
import static com.example.tower_defense.game_menu.Constants.Dimensions.Ypolicka;

import android.graphics.Canvas;


import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;
import com.example.tower_defense.game_menu.spirtesControl.player.EPlayerControl;


public class TileManager implements IBitMapFunctions {




    private final Tile[][] tiles = new Tile[GameActivity.getMapSizeY()][GameActivity.getMapSizeX()];
    private int tyleSelected = 0;
    private Tile selectedTyle;
    private final EPlayerControl[] selectionAnimation = new EPlayerControl[3];


    public TileManager() {
        System.out.println(Ypolicka);
        createMap();
        createAnimation();
    }

    private void createAnimation() {
        this.selectionAnimation[0] = EPlayerControl.SELECTED1;
        this.selectionAnimation[1] = EPlayerControl.SELECTED2;
        this.selectionAnimation[2] = EPlayerControl.SELECTED3;
    }

    private void createMap() {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    if (posX == 0 && posY == 0 || posX == GameActivity.getMapSizeX() - 1 && posY == GameActivity.getMapSizeY() - 1) {
                        this.createTile(ETileType.ROAD, posX,  posY);
                    } else {
                        this.createTile(ETileType.GRASS, posX,  posY);
                    }
                }
            }
        }
    }

    public void createTile(ETileType type, int posX, int posY){
        synchronized (this.tiles) {
            this.tiles[posY][posX] = new Tile(Xpolicka * posX, Ypolicka * posY, type);
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
        if (this.selectedTyle == selectedTyle && this.selectedTyle != null) {
            this.selectedTyle.setSelected(false);
            this.selectedTyle = null;
        } else {
            this.unselectTile();
            synchronized (this.tiles) {
                for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++){
                    for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                        if (this.tiles[posY][posX] == selectedTyle) {
                            this.tiles[posY][posX].setSelected(true);
                            this.selectedTyle = selectedTyle;

                        }
                    }
                }

            }
        }

    }
}
