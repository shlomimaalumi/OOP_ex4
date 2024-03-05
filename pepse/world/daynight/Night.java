package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import danogl.components.CoordinateSpace;


import java.awt.*;

public class Night {
    private static final String NIGHT_TAG = "night";
    private static final float INIT_OPAQUENESS = 0f;
    private static final float MIDNIGHT_OPACITY = 0.5f;
    private static final float HALF = 0.5f;


    private static GameObject NIGHT_INSTANCE = null;
    private static final Color BASE_NIGHE_COLOR = Color.BLACK;
    private static final RectangleRenderable rectangleRenderable =
            new RectangleRenderable(ColorSupplier.approximateColor(BASE_NIGHE_COLOR));

    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        if (NIGHT_INSTANCE == null) {
            GameObject night = new GameObject(Vector2.ZERO, windowDimensions, rectangleRenderable);
            night.setTag(NIGHT_TAG);
            night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            new Transition<Float>(
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
