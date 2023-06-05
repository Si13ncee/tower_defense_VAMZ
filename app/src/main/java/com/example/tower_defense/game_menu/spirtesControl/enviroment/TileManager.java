package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_X;
import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_Y;

import android.graphics.Canvas;


import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;
import com.example.tower_defense.game_menu.spirtesControl.player.EPlayerControl;

import java.util.ArrayList;


public class TileManager implements IBitMapFunctions {


    private final Tile[][] tiles = new Tile[GameActivity.getMapSizeY()][GameActivity.getMapSizeX()];
    private int tileSelected = 0;
    private Tile selectedTile;
    private final EPlayerControl[] selectionAnimation = new EPlayerControl[3];
    private final Tile[][] path = new Tile[GameActivity.getMapSizeY()][GameActivity.getMapSizeX()];
    private ArrayList<Tile> lowestCostPath = new ArrayList<>();


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
            int id = 0;
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    this.createTile(ETileType.GRASS, posX, posY, id);
                    id++;
                }
            }
        }
    }

    public void createTile(ETileType type, int posX, int posY, int id) {
        synchronized (this.tiles) {
            this.tiles[posY][posX] = new Tile(SIZE_POLICKA_X * posX, SIZE_POLICKA_Y * posY, type);
        }
    }

    public void renderTiles(Canvas c) {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
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
        try {
            return this.tiles[posY][posX];
        } catch (ArrayIndexOutOfBoundsException exep) {
            return new Tile(posX * SIZE_POLICKA_X, posY * SIZE_POLICKA_Y, ETileType.GRASS);
        }

    }

    public void unselectTile() {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
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
                for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
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

    // A* algorithm
    public void calculatePath() {


        Tile startTile = this.findStartTile();
        Tile endTile = this.findEndTile();
        ArrayList<Tile> roads = new ArrayList<>();
        roads = this.findRoads();
        this.setHeuristicCosts(endTile, roads);

        ArrayList<Tile> open = new ArrayList<>();
        ArrayList<Tile> closed = new ArrayList<>();
        open.add(startTile);

        Tile currentTile = null;
        while (!open.isEmpty()) {


            for (Tile t : open) {
                if (currentTile == null) {
                    currentTile = t;
                } else if (t.getCostFromStart() + t.getHeuristicCost() < currentTile.getCostFromStart() + currentTile.getHeuristicCost()) {
                    currentTile = t;
                }
            }

            if (currentTile.equals(endTile)) {
                break;
            } else {

                int cost = currentTile.getCostFromStart() + 1;
                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).getTileType() == ETileType.ROAD) {


                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).getTileType())) {

                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).setParentNode(currentTile);
                    }

                    else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).getCostFromStart() + 1) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).setParentNode(currentTile);
                        }
                    }


                }


                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).getTileType() == ETileType.ROAD) {

                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)))) {
                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).setParentNode(currentTile);
                    } else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).getCostFromStart()) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).setParentNode(currentTile);
                        }
                    }
                }


                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).getTileType() == ETileType.ROAD) {

                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1))) {
                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).setParentNode(currentTile);
                    } else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).getCostFromStart()) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).setParentNode(currentTile);
                        }
                    }
                }

                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).getTileType() == ETileType.ROAD) {

                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1))) {
                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).setParentNode(currentTile);
                    } else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).getCostFromStart()) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).setParentNode(currentTile);
                        }
                    }
                }
            }
        }
    }

    private void setHeuristicCosts(Tile endTile, ArrayList<Tile> roads) {
        // Manhattanska vzdialenost
        for (Tile t : roads) {
            t.setHeuristicCost(Math.abs(t.getPosX() - endTile.getPosX()) + Math.abs(t.getPosY() - endTile.getPosY()));
        }
    }

    private ArrayList<Tile> findRoads() {
        ArrayList<Tile> road = new ArrayList<>();
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    if (this.tiles[posY][posX].getTileType() == ETileType.ROAD) {
                        road.add(this.tiles[posY][posX]);
                    }
                }
            }
        }
        return road;
    }

    private Tile findEndTile() {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    if (this.tiles[posY][posX].getTileType() == ETileType.ROAD && this.tiles[posY][posX].isEndingTile()) {
                        return this.tiles[posY][posX];
                    }
                }
            }
        }
        return null;
    }

    private Tile findStartTile() {
        synchronized (this.tiles) {
            for (int posY = 0; posY < GameActivity.getMapSizeY(); posY++) {
                for (int posX = 0; posX < GameActivity.getMapSizeX(); posX++) {
                    if (this.tiles[posY][posX].getTileType() == ETileType.ROAD && this.tiles[posY][posX].isStartingTile()) {
                        return this.tiles[posY][posX];
                    }
                }
            }
        }
        return null;
    }
}
