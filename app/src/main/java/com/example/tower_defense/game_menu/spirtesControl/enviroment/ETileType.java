package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public enum ETileType implements IBitMapFunctions {
    GRASS(R.drawable.grass),
    ROAD(R.drawable.road);

    private final Bitmap spriteSheet;
    ETileType(int resID) {
        this.options.inScaled = false;
        this.spriteSheet = this.getScaledBitmap(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options));
    }

    public Bitmap getSpriteSheet() {
        return this.spriteSheet;
    }


}
