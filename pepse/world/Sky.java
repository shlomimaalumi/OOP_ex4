package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.Color;

/**
 * Represents the sky in the game world.
 */
public class Sky {
    /** Tag identifying the sky object. */
    private static final String SKY_TAG = "sky";

    /** Singleton instance of the sky object. */
    private static GameObject SKY_INSTANCE = null;

    /** Basic color of the sky. */
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /** Private constructor to prevent instantiation. */
    private Sky() {
    }

    /**
     * Creates and returns the sky object.
     *
     * @param windowDimensions Dimensions of the game window.
     * @return The sky object.
     */
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
