package com.example.tower_defense.game_menu.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;

import com.example.tower_defense.game_menu.spirtesControl.Towers.towerList;
import com.example.tower_defense.game_menu.spirtesControl.entities.enemiesList;
import com.example.tower_defense.game_menu.spirtesControl.entities.entity;

import java.util.ArrayList;
import java.util.Random;

import kotlin.jvm.Synchronized;

public class Game extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Random rand;
    private Paint paint = new Paint();
    private SurfaceHolder holder;
    private ArrayList<entity> enemies = new ArrayList<>();
    private Thread gameLoopThread;


    public Game(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        this.paint.setColor(Color.BLUE);
        this.gameLoopThread = new Thread(this);
        this.rand = new Random();


    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(towerList.ARCHER.getSpriteSheet(), 0, 500, null);

        canvas.drawBitmap(towerList.ARCHER.getSprite(0),500, 500, null);
        canvas.drawBitmap(towerList.ARCHER.getSprite(1),500, 700, null);
        canvas.drawBitmap(towerList.ARCHER.getSprite(2),500, 900, null);

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
            synchronized (this.enemies) {
                this.enemies.add(new entity((int) event.getX(), (int) event.getY(), enemiesList.MAGMA_CRAB));
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.update();
                this.render();
            } catch (NullPointerException npt) {
                System.out.println("npt");
                break;
            }

        }
    }

    private void update() {
        synchronized (this.enemies) {
            int direction = 0; //0 = y--, 1 = x++, 2 = y++, 3 = x--
            for (entity e: this.enemies) {
                direction = this.rand.nextInt(4);
                switch (direction) {
                    case 0:
                        e.setPosY(e.getPosY() - 4);
                        break;
                    case 1:
                        e.setPosX(e.getPosX() + 4);
                        break;
                    case 2:
                        e.setPosY(e.getPosY() + 4);
                        break;
                    case 3:
                        e.setPosX(e.getPosX() - 4);
                        break;
                }
            }
        }
    }
}
