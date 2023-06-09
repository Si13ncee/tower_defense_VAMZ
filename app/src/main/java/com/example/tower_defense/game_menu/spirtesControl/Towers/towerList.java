package com.example.tower_defense.game_menu.spirtesControl.Towers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

public enum towerList implements IBitMapFunctions {

    ARCHER(R.drawable.archer_tower), //64x42
    ARCHER_TEST(R.drawable.archer_tower_test), // 96x64
    ARCHER_LEVEL_0(R.drawable.archer_tower_level_1); //64x128

    private BitmapFactory.Options options = new BitmapFactory.Options();
    private Bitmap sprite;
    //private Bitmap[] sprite = new Bitmap[3];
    towerList(int resID) {
        options.inScaled = false; // to get rid of image scaling
        this.sprite = this.getScaledBitmap(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options));
        //for (int i = 0; i < sprite.length; i++) {
        //    sprite[i] = Bitmap.createBitmap(spriteSheet,32*i, 0, 32, 64);
        //}

    }

    @Override
    public Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int)Math.floor((bitmap.getWidth() / 2) * GameActivity.getScalingX()), (int)Math.floor((bitmap.getHeight() / 2) * GameActivity.getScalingY()), false);
    }

    public Bitmap getSprite() {
        return sprite;
    }

    //public Bitmap getSprite(int pos) {
        //return sprite[pos];
    //}

}
