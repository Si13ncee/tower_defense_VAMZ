package com.example.tower_defense.game_menu.spirtesControl.ui.sideBar;

import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_X;
import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_Y;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.tower_defense.R;
import com.example.tower_defense.game_menu.Constants;
import com.example.tower_defense.game_menu.GameActivity;
import com.example.tower_defense.game_menu.spirtesControl.IBitMapFunctions;
import com.example.tower_defense.game_menu.spirtesControl.ui.button.ButtonImages;
import com.example.tower_defense.game_menu.spirtesControl.ui.button.CustomButton;

public class SideBar implements IBitMapFunctions {
    private static final int statBarSizeX = GameActivity.getScreenWidth() - Constants.MapDimension.SIZE_X;

    private final Bitmap spriteSheet;
    private final int posX = Constants.MapDimension.SIZE_X;
    private final int posY = 0;
    private CustomButton grassBtn;
    private CustomButton roadBtn;

    public SideBar() {
        this.options.inScaled = false;
        this.spriteSheet = this.getScaledBitmap(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), R.drawable.sidebar, options));
        this.grassBtn = new CustomButton(Constants.MapDimension.SIZE_X + 10, 200, 40, 40);
        this.roadBtn = new CustomButton(Constants.MapDimension.SIZE_X + 50, 200, 40, 40);
    }


    @Override
    public Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, statBarSizeX, GameActivity.getScreenHeight(), false);
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(this.spriteSheet, this.posX, this.posY, null);
        this.renderButton(this.grassBtn, this.grassBtn.isPushed(), ButtonImages.GRASS_BUTTON_PRESSED, ButtonImages.GRASS_BUTTON_UNPRESSED, canvas);
        this.renderButton(this.roadBtn, this.roadBtn.isPushed(), ButtonImages.ROAD_BUTTON_PRESSED, ButtonImages.ROAD_BUTTON_UNPRESSED, canvas);
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
}
