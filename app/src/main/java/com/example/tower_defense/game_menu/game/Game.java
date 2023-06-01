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

import com.example.tower_defense.game_menu.spirtesControl.entities.enemiesList;
import com.example.tower_defense.game_menu.spirtesControl.entities.entity;

import com.example.tower_defense.game_menu.spirtesControl.enviroment.TileManager;
import com.example.tower_defense.game_menu.spirtesControl.ui.button.ButtonImages;
import com.example.tower_defense.game_menu.spirtesControl.ui.button.CustomButton;
import com.example.tower_defense.game_menu.spirtesControl.ui.sideBar.SideBar;

import java.util.ArrayList;
import java.util.Random;



public class Game extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private final Random rand;

    private final SurfaceHolder holder;
    private ArrayList<entity> enemies = new ArrayList<>();
    private final Thread gameLoopThread;
    private final TileManager tm = new TileManager();
    private final SideBar sb = new SideBar();
    private boolean gameStart = false;
    private CustomButton buttonArcherTower;



    public Game(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        this.gameLoopThread = new Thread(this);
        this.rand = new Random();

        this.buttonArcherTower = new CustomButton(Constants.MapDimension.SIZE_X + 5, 1 , 80, 110);
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        this.tm.renderTiles(canvas);
        this.sb.render(canvas);

        canvas.drawBitmap(
                ButtonImages.ARCHER_TOWER_BUTTON_UNPRESSED.getButtonImage(
                        this.buttonArcherTower.isPushed()),
                        this.buttonArcherTower.getHitbox().left,
                        this.buttonArcherTower.getHitbox().top,
                        null);

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
                this.enemies.add(new entity(1, rand.nextInt(1), enemiesList.MAGMA_CRAB, rand.nextInt(5) + 4));
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
            if (event.getX() < GameActivity.getMapSizeX() * SIZE_POLICKA_X && event.getY() < GameActivity.getMapSizeY() * SIZE_POLICKA_Y) {
                this.tm.setSelectedTyle(this.tm.getTile((int) (event.getY() / (32 * GameActivity.getScalingY())), (int) (event.getX() / (32 * GameActivity.getScalingX()))));
            }

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
        if (this.gameStart) {
            synchronized (this.enemies) {
                for (entity e: this.enemies) {
                    e.move(delta);
                }
            }
        }

    }
}
