package com.example.tower_defense.game_menu.spirtesControl.ui.button;

import android.graphics.RectF;

public class CustomButton {

    private final RectF hitbox;
    private boolean pushed;
    public CustomButton(float x, float y, float width, float heigth) {
        this.hitbox = new RectF(x, y, x + width, y + heigth);
    }

    public RectF getHitbox() {
        return this.hitbox;
    }

    public boolean isPushed() {
        return this.pushed;
    }

    public void setPushed(boolean pushed) {
        this.pushed = pushed;
    }
}
