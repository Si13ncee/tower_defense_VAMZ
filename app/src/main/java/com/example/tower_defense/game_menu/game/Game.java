package com.example.tower_defense.game_menu.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.tower_defense.game_menu.Towers.towerList;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint = new Paint();
    private SurfaceHolder holder;

    public Game(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        this.paint.setColor(Color.BLUE);
    }

    public void render() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(towerList.ARCHER.getSpriteSheet(), 0, 500, null);

        canvas.drawBitmap(towerList.ARCHER.getSprite(0),500, 500, null);
        canvas.drawBitmap(towerList.ARCHER.getSprite(1),500, 700, null);
        canvas.drawBitmap(towerList.ARCHER.getSprite(2),500, 900, null);

        canvas.drawRect(50,50,100,100,this.paint);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawRect(50,50,100,100,this.paint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.render();
        }
        return true;
    }

}
