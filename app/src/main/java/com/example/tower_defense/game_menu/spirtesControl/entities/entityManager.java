package com.example.tower_defense.game_menu.spirtesControl.entities;

import android.graphics.Canvas;

import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.spirtesControl.enviroment.Tile;

import java.util.ArrayList;

public class entityManager {
    private ArrayList<entity> enemies = new ArrayList<>();

    public entityManager () {

    }

    public void update(double delta) {
        synchronized (this.enemies) {
            for (entity e : this.enemies) {
                e.move(delta);
            }
        }
    }

    public void createEnemy(Tile startingTile) {
        this.enemies.add(new entity(startingTile.getPosX(), startingTile.getPosY(), enemiesList.MAGMA_CRAB, 5));
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

    public void render(Canvas c) {
        synchronized (this.enemies) {
            for (entity e: enemies) {
                c.drawBitmap(e.getEnemyType().getSprite(0,0), e.getPosX(), e.getPosY(), null);
            }
        }
    }
}
