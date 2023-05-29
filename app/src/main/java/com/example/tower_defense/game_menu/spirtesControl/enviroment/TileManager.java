package com.example.tower_defense.game_menu.spirtesControl.enviroment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;

import java.util.ArrayList;

public class TileManager implements IBitMapFunctions {
    private ArrayList<Tile> tiles = new ArrayList<>();

    public TileManager() {

    }

    public void createTile(ETileType type, int posX, int posY){
        synchronized (this.tiles) {
            this.tiles.add(new Tile(posX, posY, type));
        }
    }

    public void renderTiles(Canvas c) {
        synchronized (this.tiles) {
            for (Tile t : this.tiles) {
                c.drawBitmap(t.getTileType().getSpriteSheet(), t.getPosX(), t.getPosY(), null);
            }
        }
    }

}
