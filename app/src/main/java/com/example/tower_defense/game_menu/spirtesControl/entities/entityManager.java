package com.example.tower_defense.game_menu.spirtesControl.entities;

import com.example.tower_defense.game_menu.Constants;

import java.util.ArrayList;

public class entityManager {
    private ArrayList<entity> enemies = new ArrayList<>();
    private boolean gameState = false;

    public entityManager (boolean gameState) {
        this.gameState = gameState;
    }

    public void update() {
        synchronized (this.enemies) {
            for (entity e : this.enemies) {
                if (this.nextRoadTile(e)) {

                }
            }
        }
    }

    private boolean nextRoadTile(entity e) {
        int newX = e.getPosX() + getMoveSpeedX(e, e.getDir());
        int newY =  e.getPosY() + getMoveSPeedY(e, e.getDir());

        return false;
    }

    private int getMoveSPeedY(entity e, int dir) {
        if (dir == Constants.Direction.UP){
            return -e.getSpeed();
        } else if (dir == Constants.Direction.DOWN) {
            return e.getSpeed();
        }
        return 0;
    }

    private int getMoveSpeedX(entity e, int dir) {
        if (dir == Constants.Direction.RIGHT) {
            return e.getSpeed();
        } else if(dir == Constants.Direction.LEFT) {
            return -e.getSpeed();
        }
        return 0;
    }

    public void render() {
        synchronized (this.enemies) {

        }
    }
}
