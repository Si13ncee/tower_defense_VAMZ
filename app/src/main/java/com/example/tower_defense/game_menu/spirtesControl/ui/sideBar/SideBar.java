package com.example.tower_defense.game_menu.spirtesControl.ui.sideBar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;
import com.example.tower_defense.game_menu.spirtesControl.enviroment.ETileType;
import com.example.tower_defense.game_menu.spirtesControl.enviroment.Tile;
import com.example.tower_defense.game_menu.spirtesControl.ui.button.ButtonImages;
import com.example.tower_defense.game_menu.spirtesControl.ui.button.CustomButton;

import java.util.ArrayList;

public class SideBar implements IBitMapFunctions {
    private static final int statBarSizeX = GameActivity.getScreenWidth() - Constants.MapDimension.SIZE_X;

    private final Bitmap spriteSheet;
    private final int posX = Constants.MapDimension.SIZE_X;
    private final int posY = 0;
    private ArrayList<CustomButton> btns = new ArrayList<>();
    private CustomButton grassBtn;
    private CustomButton roadBtn;
    private CustomButton buttonArcherTower;
    private int anim = 0;


    public SideBar() {
        this.options.inScaled = false;
        this.spriteSheet = this.getScaledBitmap(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), R.drawable.sidebar, options));
        this.btns.add(this.grassBtn = new CustomButton(Constants.MapDimension.SIZE_X + 10, Constants.MapDimension.SIZE_Y - 90, 80, 80));
        this.btns.add(this.roadBtn = new CustomButton(Constants.MapDimension.SIZE_X + 100, Constants.MapDimension.SIZE_Y - 90, 80, 80));
        this.btns.add(this.buttonArcherTower = new CustomButton(Constants.MapDimension.SIZE_X + 10, 10 , 80, 110));
    }


    @Override
    public Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, statBarSizeX, GameActivity.getScreenHeight(), false);
    }

    public void render(Canvas canvas, boolean gameStarted) {
        canvas.drawBitmap(this.spriteSheet, this.posX, this.posY, null);
        if (gameStarted) {
            this.renderButton(this.buttonArcherTower, this.buttonArcherTower.isPushed(), ButtonImages.ARCHER_TOWER_BUTTON_PRESSED, ButtonImages.ARCHER_TOWER_BUTTON_UNPRESSED, canvas);
        } else {
            this.renderButton(this.grassBtn, this.grassBtn.isPushed(), ButtonImages.GRASS_BUTTON_PRESSED, ButtonImages.GRASS_BUTTON_UNPRESSED, canvas);
            this.renderButton(this.roadBtn, this.roadBtn.isPushed(), ButtonImages.ROAD_BUTTON_PRESSED, ButtonImages.ROAD_BUTTON_UNPRESSED, canvas);

        }

    }

    public CustomButton getGrassBtn() {
        return grassBtn;
    }

    private void renderButton(CustomButton btn, boolean isPushed, ButtonImages pressed, ButtonImages unpressed, Canvas canvas) {
        if (isPushed) {
            canvas.drawBitmap(
                    pressed.getButtonImage(
                            btn.isPushed()),
                            btn.getHitbox().left,
                            btn.getHitbox().top,
                            null
                );
        } else {
            canvas.drawBitmap(
                    unpressed.getButtonImage(
                            btn.isPushed()),
                            btn.getHitbox().left,
                            btn.getHitbox().top,
                        null
            );
        }
    }

    private boolean isIn(MotionEvent e, CustomButton b) {
        return b.getHitbox().contains(e.getX(),e.getY());
    }

    public int checkIfBtnIsPressed(MotionEvent event, Tile selectedTile, boolean gameStarted) {
        int returnedValue = 0;
        synchronized (this.btns) {
            for (CustomButton cb : this.btns) {
                if (isIn(event, cb) && selectedTile != null && event.getAction() == MotionEvent.ACTION_DOWN) {
                    cb.setPushed(true);

                    if (!gameStarted) {
                        if (cb == this.grassBtn) {
                            if (selectedTile.getTileType() == ETileType.ROAD) {
                                returnedValue = -1;
                            } else {
                                returnedValue = 0;
                            }
                        }
                        if (cb == this.roadBtn) {
                            if (selectedTile.getTileType() == ETileType.GRASS) {
                                returnedValue = 1;
                            } else {
                                returnedValue = 0;
                            }

                        }
                    }

                } else {
                    cb.setPushed(false);
                }

            }
            return returnedValue;
        }

    }

    public void unpushAll() {
        anim++;
        if (anim >= 20) {
            synchronized (this.btns) {
                for (CustomButton cb : this.btns) {
                    cb.setPushed(false);
                }
            }
            anim = 0;
        }

    }
}
