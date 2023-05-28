package com.example.tower_defense.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.tower_defense.game_menu.game.Game;

public class GameActivity extends AppCompatActivity {

    private static Context context;


    public static Context getGameContext() {
        return context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Game(this));
        context = this;

    }

}