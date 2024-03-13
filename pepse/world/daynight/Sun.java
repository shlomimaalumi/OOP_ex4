package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents the sun object in the game world.
 */
public class Sun {
    /** Tag to identify the sun object. */
    private static final String SUN_TAG = "sun";

    /** Singleton instance of the sun object. */
    private static GameObject SUN_INSTANCE = null;

    /** Color of the sun. */
    private static final Color SUN_COLOR = Color.YELLOW;

    /** Radius of the sun. */
    private static final float SUN_RADIUS = 100;

    /** Renderable component representing the sun. */
    private static final OvalRenderable SUN_CIRCLE = new OvalRenderable(SUN_COLOR);

    /** Middle value used for positioning the sun. */
    private static final float MIDDLE = 0.5f;

    /** Initial value for the sun's angle. */
    private static final float INIT_VALUE = 0f;

    /** Final value for the sun's angle. */
    private static final float FINAL_VALUE = 360f;

    /**
     * Private constructor to prevent instantiation of Sun class.
     */
    private Sun() {
    }

    /**
     * Creates a game object representing the sun with specified parameters.
     *
     * @param cycleCenterPoint The center point of the day-night cycle.
     * @param cycleLength      The length of the day-night cycle.
     * @return The game object representing the sun.
     */
    public static GameObject create(Vector2 cycleCenterPoint, float cycleLength) {
        if (SUN_INSTANCE == null) {
            Vector2 sunLocation = cycleCenterPoint.multY(MIDDLE);
            GameObject sun = new GameObject(sunLocation, new Vector2(SUN_RADIUS, SUN_RADIUS), SUN_CIRCLE);
            sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            sun.setTag(SUN_TAG);
            new Transition<>(
                    sun,
                    (Float angle) -> sun.setCenter(sunLocation.subtract(cycleCenterPoint).rotated(angle).add(cycleCenterPoint)),
                    INIT_VALUE,
                    FINAL_VALUE,
                    Transition.LINEAR_INTERPOLATOR_FLOAT,
                    cycleLength,
                    Transition.TransitionType.TRANSITION_LOOP,
                    null);
            SUN_INSTANCE = sun;
        }
        return SUN_INSTANCE;
    }
}
