package com.example.tower_defense.game_menu.spirtesControl.sideBar;

import static com.example.tower_defense.game_menu.Constants.Dimensions.Xpolicka;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public class SideBar implements IBitMapFunctions {
    private static final int statBarSizeX = 3 * Xpolicka;

    private final Bitmap spriteSheet;
    private final int posX;
    private final int posY;

    public SideBar() {
        this.posX = Xpolicka * GameActivity.getMapSizeX();
        this.posY = 0;

        this.options.inScaled = false;
        this.spriteSheet = this.getScaledBitmap(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), R.drawable.sidebar, options));

    }

    @Override
    public Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, statBarSizeX, GameActivity.getScreenHeight(), false);
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(this.spriteSheet, this.posX, this.posY, null);
    }
}
