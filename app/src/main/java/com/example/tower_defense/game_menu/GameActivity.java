package com.example.tower_defense.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.example.tower_defense.game_menu.game.Game;

public class GameActivity extends AppCompatActivity {

    private static Context context;
    private static int ScreenWidth;
    private static int ScreenHeight;
    private static double scalingY;
    private static double scalingX;
    private static int mapSizeY = 10;
    private static int mapSizeX = 20;



    public static Context getGameContext() {
        return context;
    }

    public static int getScreenWidth() {
        return ScreenWidth;
    }

    public static int getScreenHeight() {
        return ScreenHeight;
    }

    public static double getScalingY() {
        return scalingY;
    }

    public static int getMapSizeX() {
        return mapSizeX;
    }

    public static int getMapSizeY() {
        return mapSizeY;
    }

    public static double getScalingX() {
        return scalingX;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        System.out.println("W: " + dm.widthPixels + " H: " + dm.heightPixels);

        ScreenWidth = dm.widthPixels;
        ScreenHeight = dm.heightPixels;

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES; //výrez pre kameru
        }

        scalingX = ((double) ScreenWidth / (mapSizeX) )/ 32;
        scalingY = ((double) ScreenHeight / mapSizeY) / 32;
        System.out.println("Scaling: " + scalingY);
        setContentView(new Game(this));
    }

}