package com.example.tower_defense.game_menu.spirtesControl.entities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public enum enemiesList implements IBitMapFunctions {

    MAGMA_CRAB(R.drawable.magma_crab);
    private final Bitmap spriteSheet;

    private final Bitmap[][] sprite = new Bitmap[9][8];

    enemiesList(int resID) {
        options.inScaled = false; // to get rid of image scaling
        spriteSheet = BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options);

        for (int i = 0; i < sprite.length; i++) {
            for (int j = 0; j < sprite[i].length; j++)
                sprite[i][j] = getScaledBitmap(Bitmap.createBitmap(spriteSheet,64*j, 57*i, 64, 57));
        }
    }
    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    @Override
    public Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int)Math.floor(bitmap.getWidth()), (int)Math.floor(bitmap.getHeight()), false);
    }
    public Bitmap getSprite(int posY, int posX) {
        return sprite[posY][posX];
    }


}
