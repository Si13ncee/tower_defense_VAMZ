package com.example.tower_defense.main_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.R;

public class MainMenu extends AppCompatActivity {

    private Button play;
    private Button highScore;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES; //výrez pre kameru
        }

        this.setUpButtons();
    }



    private void setUpButtons() {
        this.play = (Button) findViewById(R.id.playButton);
        this.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameActivity = new Intent(MainMenu.this, GameActivity.class);
                startActivity(gameActivity);
            }
        });

        this.highScore = (Button) findViewById(R.id.highScoreButton);
        this.highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        this.exit = (Button) findViewById(R.id.exitButton);
        this.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0); //close app
            }
        });
    }
}