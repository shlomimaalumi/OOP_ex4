package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;


public class Sun {
    private static final String SUN_TAG = "sun";
    private static GameObject SUN_INSTANCE = null;


    private static final Color SUN_COLOR = Color.YELLOW;
    private static final float SUN_RADIUS = 30;
    private static final OvalRenderable SUN_CIRCLE = new OvalRenderable(SUN_COLOR);
    private static final float MIDDLE = 0.5f, HALF = 0.5f;
    private static final float HORIZONAL = 0f;
    private static final float CYCLE_RADIUS = 100;
    private static final float INIT_VALUE = 0f;
    private static final float FINAL_VALUE = 360f;


    public Sun(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
    }

    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        if (SUN_INSTANCE == null) {
            Vector2 SUN_LOCATION =
                    windowDimensions.mult(MIDDLE).subtract(new Vector2(SUN_RADIUS, SUN_RADIUS).mult(MIDDLE));
            GameObject sun = new GameObject(SUN_LOCATION,new Vector2(SUN_RADIUS, SUN_RADIUS), SUN_CIRCLE);
            sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            sun.setTag(SUN_TAG);
            //TODO check the exact location of the sun and the the axis
            Vector2 cycleCenter = new Vector2(windowDimensions.mult(MIDDLE));
            Vector2 initialSunCenter = new Vector2(SUN_LOCATION).subtract(new Vector2(CYCLE_RADIUS,
                    HORIZONAL));
            new Transition<Float>(
                    sun, (Float angle) -> sun.setCenter(initialSunCenter.subtract(cycleCenter).
                            rotated(angle).add(cycleCenter)),
                    INIT_VALUE, FINAL_VALUE, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                    Transition.TransitionType.TRANSITION_LOOP, null);
            SUN_INSTANCE=sun;
        }
        return SUN_INSTANCE;
    }
}
