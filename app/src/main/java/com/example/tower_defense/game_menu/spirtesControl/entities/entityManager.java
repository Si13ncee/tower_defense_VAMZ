package com.example.tower_defense.game_menu.spirtesControl.entities;

import android.graphics.Canvas;

import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.spirtesControl.enviroment.Tile;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class entityManager {
    private ArrayList<entity> enemies = new ArrayList<>();
    private Tile startingTile;
    private boolean canSpawn = false;
    public entityManager () {
    }
    public void update(double delta, ArrayList<Tile> path) {
        synchronized (this.enemies) {
            for (entity e : this.enemies) {
                e.move(delta, path);
            }
        }
    }

    public void Spawning() {
        Timer timer = new Timer();
        int delay = 0;
        int timeInterval = 20_000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (canSpawn) {
                    createEnemy(startingTile);
                }
            }
        }, delay, timeInterval);
    }

    public void setStartingTile(Tile startingTile) {
        this.startingTile = startingTile;
    }

    public void createEnemy(Tile startingTile) {
        this.enemies.add(new entity(startingTile.getPosX(), startingTile.getPosY(), enemiesList.MAGMA_CRAB, 5));
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

    public void setCanSpawn(boolean b) {
        this.canSpawn = b;
    }
}
