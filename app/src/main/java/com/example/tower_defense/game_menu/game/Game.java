package com.example.tower_defense.game_menu.game;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;


import com.example.tower_defense.game_menu.GameActivity;

import com.example.tower_defense.game_menu.spirtesControl.entities.enemiesList;
import com.example.tower_defense.game_menu.spirtesControl.entities.entity;

import com.example.tower_defense.game_menu.spirtesControl.enviroment.TileManager;

import java.util.ArrayList;
import java.util.Random;



public class Game extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Random rand;

    private SurfaceHolder holder;
    private ArrayList<entity> enemies = new ArrayList<>();
    private Thread gameLoopThread;
    private TileManager tm = new TileManager();


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


        synchronized(this.enemies) {
            for (entity e: enemies) {
               canvas.drawBitmap(e.getEnemyType().getSprite(0,0), e.getPosX(), e.getPosY(), null);
            }
        }



        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoopThread.start();

        for (int i = 0; i < 10; i++) {
            synchronized (this.enemies) {
                this.enemies.add(new entity(rand.nextInt(GameActivity.getScreenWidth()), rand.nextInt(GameActivity.getScreenHeight()), enemiesList.MAGMA_CRAB, 3));
            }
        }
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

                this.tm.setSelectedTyle(this.tm.getTile((int) (event.getY() / (32 * GameActivity.getScalingY())), (int) (event.getX() / (32 * GameActivity.getScalingX()))));

            //this.tm.createTile(ETileType.GRASS, (int)event.getX(), (int)event.getY());
        }
        return true;
    }

    @Override
    public void run() {

        long lastFPScheck = System.currentTimeMillis();
        int fps = 0;

        long lastDelta = System.nanoTime();
        long nanoSec = 1_000_000_000;

        while (true) {

            long nowDelta = System.nanoTime();
            double timeSinceLastDelta = nowDelta - lastDelta;
            double delta = timeSinceLastDelta / nanoSec;

            try {
                this.update(delta);
                this.render();
            } catch (NullPointerException npt) {
                System.out.println("npt");
                break;
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
            for (entity e: this.enemies) {
                e.move(delta);
            }


            /*int direction = 0; //0 = y--, 1 = x++, 2 = y++, 3 = x--
            for (entity e: this.enemies) {
                direction = this.rand.nextInt(4);
                switch (direction) {
                    case 0:
                        e.setPosY((e.getPosY() - 4) * delta);
                        break;
                    case 1:
                        e.setPosX((e.getPosX() + 4) * delta);
                        break;
                    case 2:
                        e.setPosY((e.getPosY() + 4)* delta);
                        break;
                    case 3:
                        e.setPosX((e.getPosX() - 4) * delta);
                        break;
                }
            }*/
        }
    }
}
