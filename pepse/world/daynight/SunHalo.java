package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
    private static final String SUN_HALO_TAG = "sunHalo";
    private static GameObject SUN_HALO_INSTANCE = null;


    private static final float HALO_RADIUS = 100f;

    private static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    private static final OvalRenderable SUN_HALO_CIRCLE = new OvalRenderable(SUN_HALO_COLOR);


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
