package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents the halo around the sun in the game world.
 */
public class SunHalo {
    /** Tag to identify the sun halo object. */
    private static final String SUN_HALO_TAG = "sunHalo";

    /** Singleton instance of the sun halo object. */
    private static GameObject SUN_HALO_INSTANCE = null;

    /** Radius of the halo around the sun. */
    private static final float HALO_RADIUS = 100f;

    /** Color of the sun halo. */
    private static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);

    /** Renderable component representing the sun halo. */
    private static final OvalRenderable SUN_HALO_CIRCLE = new OvalRenderable(SUN_HALO_COLOR);

    /**
     * Private constructor to prevent instantiation of SunHalo class.
     */
    private SunHalo() {
    }

    /**
     * Creates a game object representing the halo around the sun.
     *
     * @param sun The sun object around which the halo is created.
     * @return The game object representing the halo around the sun.
     */
    public static GameObject create(GameObject sun) {
        if (SUN_HALO_INSTANCE == null) {
            GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),
                    sun.getDimensions().add(new Vector2(HALO_RADIUS, HALO_RADIUS)), SUN_HALO_CIRCLE);
            sunHalo.setTag(SUN_HALO_TAG);
            sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));
            SUN_HALO_INSTANCE = sunHalo;
        }
        return SUN_HALO_INSTANCE;
    }
}
