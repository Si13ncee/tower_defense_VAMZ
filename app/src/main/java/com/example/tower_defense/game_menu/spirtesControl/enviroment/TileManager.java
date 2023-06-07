package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_X;
import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_Y;

import android.graphics.Canvas;


import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;
import com.example.tower_defense.game_menu.spirtesControl.player.EPlayerControl;

import java.util.ArrayList;
import java.util.Collections;


public class TileManager implements IBitMapFunctions {


    private final Tile[][] tiles = new Tile[GameActivity.getMapSizeY()][GameActivity.getMapSizeX()];
    private int tileSelected = 0;
    private Tile selectedTile;
    private final EPlayerControl[] selectionAnimation = new EPlayerControl[3];
    private ArrayList<Tile> finalPath = new ArrayList<>();

    private Tile startTile;
    private Tile endTile;


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


        startTile = this.findStartTile();
        endTile = this.findEndTile();
        ArrayList<Tile> roads = new ArrayList<>();
        roads = this.findRoads();
        this.setHeuristicCosts(endTile, roads);

        ArrayList<Tile> open = new ArrayList<>();
        ArrayList<Tile> closed = new ArrayList<>();
        open.add(startTile);

        startTile.setCostFromStart(0);

        while (!open.isEmpty()) {
            Tile currentTile = null;

            for (Tile t : open) {
                if (currentTile == null) {
                    currentTile = t;
                } else if (t.getCostFromStart() + t.getHeuristicCost() < currentTile.getCostFromStart() + currentTile.getHeuristicCost()) {
                    currentTile = t;
                }
            }
            int cost = currentTile.getCostFromStart() + 1;

            if (currentTile.equals(endTile)) {
                endTile.setCostFromStart(cost - 1);
                break;
            } else {


                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).getTileType() == ETileType.ROAD) {


                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).getTileType()) &&
                    !closed.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X))) {

                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).setParentNode(currentTile);
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).setCostFromStart(cost);
                    } else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).getCostFromStart() + 1) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) - 1 ,currentTile.getPosX() / SIZE_POLICKA_X).setParentNode(currentTile);
                        }
                    }


                }


                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).getTileType() == ETileType.ROAD) {

                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X))) &&
                    !closed.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)))) {
                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).setParentNode(currentTile);
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).setCostFromStart(cost);
                    } else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).getCostFromStart()) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y) + 1, (currentTile.getPosX() / SIZE_POLICKA_X)).setParentNode(currentTile);
                        }
                    }
                }


                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).getTileType() == ETileType.ROAD) {

                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1)) &&
                    !closed.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1))) {
                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).setParentNode(currentTile);
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).setCostFromStart(cost);
                    } else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).getCostFromStart()) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) + 1).setParentNode(currentTile);
                        }
                    }
                }

                if (this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).getTileType() == ETileType.ROAD) {

                    if (!open.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1)) &&
                    !closed.contains(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1))) {
                        open.add(this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1));
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).setParentNode(currentTile);
                        this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).setCostFromStart(cost);
                    } else {
                        if (cost < this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).getCostFromStart()) {
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).setCostFromStart(cost);
                            this.getTile((currentTile.getPosY() / SIZE_POLICKA_Y), (currentTile.getPosX() / SIZE_POLICKA_X) - 1).setParentNode(currentTile);
                        }
                    }
                }

                closed.add(currentTile);
                open.remove(currentTile);
            }
        }

        Tile currentTile = endTile;
        this.finalPath.add(currentTile);
        while (!currentTile.equals(startTile)) {
            currentTile = currentTile.getParentNode();
            this.finalPath.add(currentTile);
        }
        Collections.reverse(this.finalPath);
    }

    private void setHeuristicCosts(Tile endTile, ArrayList<Tile> roads) {
        // Manhattanska vzdialenost
        for (Tile t : roads) {
            t.setHeuristicCost(Math.abs((t.getPosX() - endTile.getPosX()) / SIZE_POLICKA_X) + (Math.abs(t.getPosY() - endTile.getPosY()))/ SIZE_POLICKA_Y);
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

    public Tile getStartTile() {
        return this.startTile;
    }
}
