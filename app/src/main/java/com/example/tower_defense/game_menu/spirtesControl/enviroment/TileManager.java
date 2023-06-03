package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_X;
import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_Y;

import android.graphics.Canvas;


import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;
import com.example.tower_defense.game_menu.spirtesControl.player.EPlayerControl;


public class TileManager implements IBitMapFunctions {




    private final Tile[][] tiles = new Tile[GameActivity.getMapSizeY()][GameActivity.getMapSizeX()];
    private int tileSelected = 0;
    private Tile selectedTile;
    private final EPlayerControl[] selectionAnimation = new EPlayerControl[3];


    public TileManager() {
        System.out.println(SIZE_POLICKA_Y);
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
            this.tiles[posY][posX] = new Tile(SIZE_POLICKA_X * posX, SIZE_POLICKA_Y * posY, type);
        }
    }

    public void renderTiles(Canvas c) {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++){
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    c.drawBitmap(this.tiles[posY][posX].getTileType().getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                    if (this.tiles[posY][posX].isSelected()) {
                        this.tileSelected++;
                        if (0 <= this.tileSelected && !(this.tileSelected > 15)) {
                            c.drawBitmap(this.selectionAnimation[0].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);

                        } else if (15 <= this.tileSelected && !(this.tileSelected > 30)) {
                            c.drawBitmap(this.selectionAnimation[1].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);

                        } else if (30 <= this.tileSelected && !(this.tileSelected > 45)) {
                            c.drawBitmap(this.selectionAnimation[2].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);

                        } else if (45 <= this.tileSelected && !(this.tileSelected > 60)) {
                            c.drawBitmap(this.selectionAnimation[1].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);

                        } else {
                            c.drawBitmap(this.selectionAnimation[0].getSpriteSheet(), this.tiles[posY][posX].getPosX(), this.tiles[posY][posX].getPosY(), null);
                            this.tileSelected = 0;
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

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTyle) {
        if (this.selectedTile == selectedTyle && this.selectedTile != null) {
            this.selectedTile.setSelected(false);
            this.selectedTile = null;
        } else {
            this.unselectTile();
            synchronized (this.tiles) {
                for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++){
                    for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                        if (this.tiles[posY][posX] == selectedTyle) {
                            this.tiles[posY][posX].setSelected(true);
                            this.selectedTile = selectedTyle;

                        }
                    }
                }

            }
        }

    }
}
