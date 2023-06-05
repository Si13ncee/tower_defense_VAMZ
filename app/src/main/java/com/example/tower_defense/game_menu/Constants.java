package com.example.tower_defense.game_menu;

import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_X;
import static com.example.tower_defense.game_menu.Constants.Dimensions.SIZE_POLICKA_Y;

public final class Constants {
    public static final class Direction {
        public static final int UP = 0;
        public static final int RIGHT = 1;
        public static final int DOWN = 2;
        public static final int LEFT = 3;

    }

    public static final class Dimensions {
        public static final int SIZE_POLICKA_Y = (int) (32 * GameActivity.getScalingY());
        public static final int SIZE_POLICKA_X = (int) (32 * GameActivity.getScalingX());
    }

    public static final class MapDimension {
        public static final int SIZE_X = (int) (SIZE_POLICKA_X * GameActivity.getMapSizeX());
        public static final int SIZE_Y = (int) (SIZE_POLICKA_Y * GameActivity.getMapSizeY());
    }

    public static final class FieldConstants {
        public static final int RoadAmount = 15;
    }
}
