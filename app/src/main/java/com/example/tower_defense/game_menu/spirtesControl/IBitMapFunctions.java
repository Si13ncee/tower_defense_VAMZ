package com.example.tower_defense.game_menu.spirtesControl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.game_menu.GameActivity;

public interface IBitMapFunctions {


    BitmapFactory.Options options = new BitmapFactory.Options();

    default Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int)Math.floor(bitmap.getWidth() * GameActivity.getScalingX()), (int)Math.floor(bitmap.getHeight() * GameActivity.getScalingY()), false);
    }

}
