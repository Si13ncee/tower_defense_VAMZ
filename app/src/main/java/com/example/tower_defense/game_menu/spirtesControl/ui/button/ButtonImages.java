package com.example.tower_defense.game_menu.spirtesControl.ui.button;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public enum ButtonImages implements IBitMapFunctions {

    ARCHER_TOWER_BUTTON_UNPRESSED(R.drawable.archer_tower_button_unpressed, 80, 110),
    ARCHER_TOWER_BUTTON_PRESSED(R.drawable.archer_tower_button_pressed, 80, 110),

    GRASS_BUTTON_PRESSED(R.drawable.grass_pressed, 80, 80),
    GRASS_BUTTON_UNPRESSED(R.drawable.grass_unpressed, 80, 80),
    ROAD_BUTTON_PRESSED(R.drawable.road_pressed, 80, 80),
    ROAD_BUTTON_UNPRESSED(R.drawable.road_unpressed, 80, 80);


    private int width, height;
    private Bitmap normal;
    ButtonImages(int resID, int sizePolickaX, int sizePolickaY) {
        this.width = sizePolickaX;
        this.height = sizePolickaY;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options);
        normal = Bitmap.createBitmap(buttonAtlas, 0, 0, width, height);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Bitmap getButtonImage(boolean isPushed) {
        return isPushed ? normal : normal;
    }
}
