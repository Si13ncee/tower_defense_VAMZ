package com.example.tower_defense.game_menu.spirtesControl.entities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public enum enemiesList implements IBitMapFunctions {

    MAGMA_CRAB(R.drawable.magma_crab);
    private Bitmap spriteSheet;
    private BitmapFactory.Options options = new BitmapFactory.Options();
    private Bitmap[][] sprite = new Bitmap[9][8];

    enemiesList(int resID) {
        options.inScaled = false; // to get rid of image scaling
        spriteSheet = BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options);
        System.out.println("Width: " + spriteSheet.getWidth() + "  Height: " + spriteSheet.getHeight());

        for (int i = 0; i < sprite.length; i++) {
            for (int j = 0; j < sprite[i].length; j++)
                sprite[i][j] = getScaledBitmap(Bitmap.createBitmap(spriteSheet,64*j, 57*i, 64, 57));
        }
    }
    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int posY, int posX) {
        return sprite[posY][posX];
    }

    private Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int)Math.floor(bitmap.getWidth() * 1.5), (int)Math.floor(bitmap.getHeight() * 1.5), false);
    }
}
