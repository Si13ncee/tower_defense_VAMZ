package com.example.tower_defense.game_menu.spirtesControl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public interface IBitMapFunctions {

    BitmapFactory.Options options = new BitmapFactory.Options();

    default Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int)Math.floor(bitmap.getWidth() * 1.5), (int)Math.floor(bitmap.getHeight() * 1.5), false);
    }
}
