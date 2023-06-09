package com.example.tower_defense.game_menu.spirtesControl.Towers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public enum towerList implements IBitMapFunctions {

    ARCHER(R.drawable.archer_tower); //64x42

    private BitmapFactory.Options options = new BitmapFactory.Options();
    private Bitmap spriteSheet;
    private Bitmap[] sprite = new Bitmap[3];
    towerList(int resID) {
        options.inScaled = false; // to get rid of image scaling
        spriteSheet = BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options);
        System.out.println("Width: " + spriteSheet.getWidth() + "  Height: " + spriteSheet.getHeight());

        for (int i = 0; i < sprite.length; i++) {
            sprite[i] = Bitmap.createBitmap(spriteSheet,64*i, 0, 64, 128);
        }
    }


    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int pos) {
        return sprite[pos];
    }


    public float getPosY() {
        return
    }
}
