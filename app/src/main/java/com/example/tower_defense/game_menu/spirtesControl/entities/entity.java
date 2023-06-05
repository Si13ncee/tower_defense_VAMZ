package com.example.tower_defense.game_menu.spirtesControl.entities;


import com.example.tower_defense.game_menu.Constants;

import java.util.Random;

public class entity {

    private int posX;
    private int posY;
    private enemiesList enemyType;
    private int lastDirection = Constants.Direction.RIGHT;
    private int speed;

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

    public void move(double delta) {
        Random rand = new Random();
        if (rand.nextInt(100) == 0) {
            this.changeDirection((this.lastDirection + rand.nextInt(20)) % 4);
        }
        switch (this.lastDirection) {
            case 0:
                if (this.posY - this.speed > 0) {
                    this.posY -= this.speed * delta * 60;
                } else {
                    this.changeDirection((this.lastDirection + rand.nextInt(20)) % 4);
                }
                break;
            case 1:
                if (this.posX + this.speed + this.enemyType.getxSize() < Constants.MapDimension.SIZE_X) {
                    this.posX += this.speed * delta * 60;
                } else {
                    this.changeDirection((this.lastDirection + rand.nextInt(20)) % 4);
                }
                break;
            case 2:
                if (this.posY + this.speed + this.enemyType.getySize() < Constants.MapDimension.SIZE_Y) {
                    this.posY += this.speed * delta * 60;
                } else {
                    this.changeDirection((this.lastDirection + rand.nextInt(20)) % 4);
                }
                break;
            case 3:
                if (this.posX - this.speed > 0) {
                    this.posX -= (this.speed * delta * 60);
                } else {
                    this.changeDirection((this.lastDirection + rand.nextInt(20)) % 4);
                }
                break;
        }
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
