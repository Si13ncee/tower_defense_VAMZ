package com.example.tower_defense.game_menu;

public final class Constants {
    public static final class Direction {
        public static final int UP = 0;
        public static final int RIGHT = 1;
        public static final int DOWN = 2;
        public static final int LEFT = 3;

    }

    public static final class Dimensions {
        public static final int Ypolicka = (int) (32 * GameActivity.getScalingY());
        public static final int Xpolicka = (int) (32 * GameActivity.getScalingX());
    }
}
