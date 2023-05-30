package com.example.tower_defense.game_menu.spirtesControl.player;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public enum EPlayerControl implements IBitMapFunctions {
    SELECTED1(R.drawable.select1),
    SELECTED2(R.drawable.select2),
    SELECTED3(R.drawable.select3);

    private final Bitmap spriteSheet;

    EPlayerControl(int resID) {
        this.options.inScaled = false;
        this.spriteSheet = this.getScaledBitmap(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options));
    }

    public Bitmap getSpriteSheet() {
        return this.spriteSheet;
    }
}
