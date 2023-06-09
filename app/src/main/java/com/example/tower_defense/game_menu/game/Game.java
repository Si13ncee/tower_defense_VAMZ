package com.example.tower_defense.game_menu.game;

import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_X;
import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_Y;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;


import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.Towers.TowerManager;
import com.example.tower_defense.game_menu.spirtesControl.entities.entity;

import com.example.tower_defense.game_menu.spirtesControl.entities.entityManager;
import com.example.tower_defense.game_menu.spirtesControl.enviroment.ETileType;
import com.example.tower_defense.game_menu.spirtesControl.enviroment.TileManager;
import com.example.tower_defense.game_menu.spirtesControl.ui.button.CustomButton;
import com.example.tower_defense.game_menu.spirtesControl.ui.sideBar.SideBar;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;


public class Game extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final Random rand;

    private final SurfaceHolder holder;
    private ArrayList<entity> enemies = new ArrayList<>();
    private final Thread gameLoopThread;
    private final TileManager tm = new TileManager();
    private final SideBar sb = new SideBar();
    private final TowerManager towerManager = new TowerManager();
    private boolean gameStart = false;
    private int placedRoads = 0;
    private float firstWave;
    private entityManager em = new entityManager();
    private Timer timer = new Timer();




    public Game(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        this.gameLoopThread = new Thread(this);
        this.rand = new Random();
    }

    public void render() {

        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        this.tm.renderTiles(canvas);
        this.sb.render(canvas, this.gameStart);
        if (gameStart) {
            this.em.render(canvas);
        }
        this.sb.unpushAll();

        holder.unlockCanvasAndPost(canvas);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            this.gameLoopThread.interrupt();
        }
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() < GameActivity.getMapSizeX() * SIZE_POLICKA_X && event.getY() < GameActivity.getMapSizeY() * SIZE_POLICKA_Y) {
                this.tm.setSelectedTile(this.tm.getTile((int) (event.getY() / (32 * GameActivity.getScalingY())), (int) (event.getX() / (32 * GameActivity.getScalingX()))));
            } else {
                if (this.tm.getSelectedTile() != null) {
                    int x = this.tm.getSelectedTile().getPosX() / SIZE_POLICKA_X;
                    int y = this.tm.getSelectedTile().getPosY() / SIZE_POLICKA_Y;


                    if (this.sb.checkIfBtnIsPressed(event, this.tm.getSelectedTile(), this.gameStart) == 1 && (
                            this.tm.getTile(y+1, x).getTileType() == ETileType.ROAD ||
                                    this.tm.getTile(y, x+1).getTileType() == ETileType.ROAD ||
                                    this.tm.getTile(y-1, x).getTileType() == ETileType.ROAD ||
                                    this.tm.getTile(y, x-1).getTileType() == ETileType.ROAD ||
                                    this.placedRoads == 0)
                    ) {
                        this.tm.getSelectedTile().changeTile(ETileType.ROAD);

                        this.placedRoads++;
                        if (this.placedRoads == Constants.FieldConstants.RoadAmount) {
                            this.tm.getSelectedTile().setEndingTile(true);
                        }
                        if (this.placedRoads == 1) {
                            this.tm.getSelectedTile().setStartingTile(true);
                        }
                        System.out.println("placedRoads: " + this.placedRoads);
                    } else if (this.sb.checkIfBtnIsPressed(event, this.tm.getSelectedTile(), this.gameStart) == -1) {
                        this.tm.getSelectedTile().changeTile(ETileType.GRASS);
                        this.placedRoads--;
                    }
                }

        }
        }
        return true;
    }

    @Override
    public void run() {

        long lastFPScheck = System.currentTimeMillis();
        int fps = 0;

        long lastDelta = System.nanoTime();
        long nanoSec = 1_000_000_000;

        this.firstWave = System.nanoTime();

        while (true) {

            long nowDelta = System.nanoTime();
            double timeSinceLastDelta = nowDelta - lastDelta;
            double delta = timeSinceLastDelta / nanoSec;

            try {
                if (this.placedRoads == Constants.FieldConstants.RoadAmount  && this.gameStart == false) {
                    this.tm.calculatePath();
                    this.gameStart = true;
                    this.em.setStartingTile(this.tm.getStartTile());
                    this.em.setCanSpawn(true);
                    this.em.Spawning();
                }
                this.update(delta);
                this.render();
            } catch (NullPointerException npt) {
                System.out.println("Chyba npt");
            }

            fps++;
            lastDelta = nowDelta;
            if (System.currentTimeMillis() - lastFPScheck >= 1000) {
                System.out.println("FPS: " + fps);
                fps = 0;
                lastFPScheck += 1000;
            }
        }
    }

    private void update(double delta) {
        synchronized (this.enemies) {
            if (this.gameStart) {
                this.em.update(delta, this.tm.getFinalPath());
            }
        }
    }

    private boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(),e.getY());
    }

}
