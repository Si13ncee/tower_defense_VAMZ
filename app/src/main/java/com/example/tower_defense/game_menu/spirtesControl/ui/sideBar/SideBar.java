package com.example.tower_defense.game_menu.spirtesControl.ui.sideBar;

import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_X;

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

    public SideBar() {
        this.options.inScaled = false;
        this.spriteSheet = this.getScaledBitmap(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), R.drawable.sidebar, options));
        this.grassBtn = new CustomButton(Constants.MapDimension.SIZE_X + 10, 200, 40, 40);

    }


    @Override
    public Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, statBarSizeX, GameActivity.getScreenHeight(), false);
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(this.spriteSheet, this.posX, this.posY, null);
        if (this.grassBtn.isPushed()) {
            canvas.drawBitmap(
                    ButtonImages.GRASS_BUTTON_PRESSED.getButtonImage(
                        this.grassBtn.isPushed()),
                        this.grassBtn.getHitbox().left,
                        this.grassBtn.getHitbox().top,
                        null
                        );
        } else {
            canvas.drawBitmap(
                    ButtonImages.GRASS_BUTTON_UNPRESSED.getButtonImage(
                            this.grassBtn.isPushed()),
                    this.grassBtn.getHitbox().left,
                    this.grassBtn.getHitbox().top,
                    null
            );
        }
    }

    public CustomButton getGrassBtn() {
        return grassBtn;
    }
}
