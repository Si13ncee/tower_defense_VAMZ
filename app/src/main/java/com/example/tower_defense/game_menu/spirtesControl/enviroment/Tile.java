package com.example.tower_defense.game_menu.spirtesControl.enviroment;

public class Tile {

    private int posX;
    private int posY;
    private ETileType tileType;
    private boolean selected = false;
    private boolean startingTile = false;
    private boolean endingTile = false;

    private int costFromStart = 0;
    private double heuristicCost = 0;
    private Tile parentNode = null;




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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (selected == true && this.getTileType() == ETileType.ROAD) {
            System.out.println("Cost from start: "  + this.getCostFromStart() + "Heuristic cost: " + this.getHeuristicCost());
        }
        this.selected = selected;
    }

    public void changeTile(ETileType type) {
        this.tileType = type;
    }

    public boolean isEndingTile() {
        return endingTile;
    }

    public void setEndingTile(boolean endingTile) {
        this.endingTile = endingTile;
    }

    public boolean isStartingTile() {
        return startingTile;
    }

    public void setStartingTile(boolean startingTile) {
        this.startingTile = startingTile;
    }

    public int getCostFromStart() {
        return costFromStart;
    }

    public void setCostFromStart(int costFromStart) {
        this.costFromStart = costFromStart;
    }


    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public Tile getParentNode() {
        return parentNode;
    }

    public void setParentNode(Tile parentNode) {
        this.parentNode = parentNode;
    }
}
