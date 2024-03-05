package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.Color;

public class Sky {


    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    private static final String SKY_TAG = "sky";
    private static GameObject SKY_INSTANCE = null;

    private Sky() {
    }

    public static GameObject create(Vector2 windowDimensions) {
        if (SKY_INSTANCE == null) {
            GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                    new RectangleRenderable(BASIC_SKY_COLOR));
            sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            sky.setTag(SKY_TAG);
            SKY_INSTANCE = sky;
        }
        return SKY_INSTANCE;
    }
}
