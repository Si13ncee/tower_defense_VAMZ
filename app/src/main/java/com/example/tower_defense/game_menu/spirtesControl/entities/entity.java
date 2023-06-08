package com.example.tower_defense.game_menu.spirtesControl.entities;


import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.spirtesControl.enviroment.Tile;

import java.util.ArrayList;
import java.util.Random;

public class entity {

    private int posX;
    private int posY;

    private enemiesList enemyType;
    private int lastDirection = Constants.Direction.RIGHT;
    private int speed;
    private int pathPos = 0;

    public entity(int posX, int posY, enemiesList type, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.enemyType = type;
        this.speed = speed;

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

    public void setPosX(double posX) {
        this.posX = (int) posX;
    }

    public void setPosY(double posY) {
        this.posY = (int) posY;
    }

    public void move(double delta, ArrayList<Tile> path) {


        System.out.println("posX: " + this.posX + " posY: " + this.posY);

        if (this.pathPos < path.size() - 1) {
            Tile currentTile = path.get(this.pathPos);
            if (!currentTile.isEndTile()) {
                Tile nextTile = path.get(this.pathPos + 1);
                this.lastDirection = this.findNextDirection(currentTile, nextTile);
            }



                switch (this.lastDirection) {
                    case 0:
                        this.posY -= this.speed * delta * 60;
                        if (this.enemyType.getSprite(0,0).getHeight() + this.posY <= currentTile.getPosY()) {
                           this.pathPos++;
                        }

                        break;
                    case 1:
                        this.posX += this.speed * delta * 60;;
                        if (this.posX >= currentTile.getPosX() + Constants.Dimensions.SIZE_POLICKA_X) {
                          this.pathPos++;
                        }

                        break;
                    case 2:
                        this.posY += this.speed * delta * 60;
                        if (this.posY >= currentTile.getPosY() + Constants.Dimensions.SIZE_POLICKA_Y) {
                            this.pathPos++;
                        }
                        break;
                    case 3:
                        this.posX -= (this.speed * delta * 60);
                        if (this.posX + this.enemyType.getSprite(0,0).getWidth() <= currentTile.getPosX()) {
                            this.pathPos++;
                        }
                }

        } else {
            return;
        }
    }

    private int findNextDirection(Tile currentTile, Tile nextTile) {


        int leftX = currentTile.getPosX();
        int topY = currentTile.getPosY();

        int nextLeftX = nextTile.getPosX();
        int nextTopY = nextTile.getPosY();

        if (topY > nextTopY && leftX == nextLeftX) {
            return Constants.Direction.UP;
        }
        if (topY == nextTopY && nextLeftX > leftX) {
            return Constants.Direction.RIGHT;
        }
        if (topY < nextTopY && leftX == nextLeftX) {
            return Constants.Direction.DOWN;
        }
        if (topY == nextTopY && nextLeftX < leftX) {
            return Constants.Direction.LEFT;
        }




        return -1;
    }

    public void changeDirection(int eDirection) {
        this.lastDirection = eDirection;
    }

    public int getDir() {
        return this.lastDirection;
    }

    public int getSpeed() {
        return this.speed;
    }
}
