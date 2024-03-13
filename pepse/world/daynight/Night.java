package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

/**
 * Represents the night time in the game world.
 */
public class Night {
    /** Tag to identify the night object. */
    private static final String NIGHT_TAG = "night";

    /** Singleton instance of the night object. */
    private static GameObject NIGHT_INSTANCE = null;

    /** Initial opaqueness value for the night. */
    private static final float INIT_OPAQUENESS = 0f;

    /** Opacity value for midnight. */
    private static final float MIDNIGHT_OPACITY = 0.5f;

    /** Middle value used for calculations. */
    private static final float HALF = 0.5f;

    /** Base color for the night. */
    private static final Color BASE_NIGHT_COLOR = Color.BLACK;

    /** Renderable component representing the night. */
    private static final RectangleRenderable rectangleRenderable =
            new RectangleRenderable(ColorSupplier.approximateColor(BASE_NIGHT_COLOR));

    /**
     * Private constructor to prevent instantiation of Night class.
     */
    private Night() {
    }

    /**
     * Creates a game object representing the night time with specified dimensions and cycle length.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the day-night cycle.
     * @return The game object representing the night time.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        if (NIGHT_INSTANCE == null) {
            GameObject night = new GameObject(Vector2.ZERO, windowDimensions, rectangleRenderable);
            night.setTag(NIGHT_TAG);
            night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            new Transition<>(
                    night,
                    night.renderer()::setOpaqueness,
                    INIT_OPAQUENESS,
                    MIDNIGHT_OPACITY,
                    Transition.CUBIC_INTERPOLATOR_FLOAT,
                    cycleLength * HALF,
                    Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                    null);
            NIGHT_INSTANCE = night;
        }
        return NIGHT_INSTANCE;
    }
}
