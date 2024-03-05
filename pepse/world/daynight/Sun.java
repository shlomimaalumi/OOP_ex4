package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    private static final String SUN_TAG = "sun";
    private static final Color SUN_COLOR = Color.YELLOW;
    private static final float SUN_RADIUS = 30;
    private static final OvalRenderable SUN_CIRCLE = new OvalRenderable(SUN_COLOR);
    private static final float MIDDLE = 0.5f;
    private static GameObject SUN_INSTANCE = null;


    public Sun(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
    }

    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        if (SUN_INSTANCE == null) {
            Vector2 SUN_LOCATION =
                    windowDimensions.mult(MIDDLE).subtract(new Vector2(SUN_RADIUS, SUN_RADIUS).mult(MIDDLE));
            GameObject sun = new GameObject(SUN_LOCATION,new Vector2(SUN_RADIUS, SUN_RADIUS), SUN_CIRCLE);
            sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            sun.setTag(SUN_TAG);
            SUN_INSTANCE=sun;
//            new Transition<Float>(
//                    sun,
//                    sun.renderer()::setOpaqueness,
//                    INIT_OPAQUENESS,
//                    MIDNIGHT_OPACITY,
//                    Transition.CUBIC_INTERPOLATOR_FLOAT,
//                    cycleLength * HALF,
//                    Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
//                    null);
//            NIGHT_INSTANCE = night;
        }
        return SUN_INSTANCE;
    }
}
