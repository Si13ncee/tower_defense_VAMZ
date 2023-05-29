package com.example.tower_defense.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.tower_defense.game_menu.game.Game;

public class GameActivity extends AppCompatActivity {

    private static Context context;
    private static int ScreenWidth;
    private static int ScreenHeight;


    public static Context getGameContext() {
        return context;
    }

    public static int getScreenWidth() {
        return ScreenWidth;
    }

    public static int getScreenHeight() {
        return ScreenHeight;
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

        setContentView(new Game(this));
    }

}