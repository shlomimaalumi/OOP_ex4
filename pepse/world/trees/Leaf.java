package pepse.world.trees;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.Color;
import java.util.Random;

public class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final RectangleRenderable LEAF_CIRCLE = new RectangleRenderable(LEAF_COLOR);
    private static final String LEAF_TAG = "leaf";
    private static final Random RANDOM = new Random();
    private static final int ROTATE_ANGLE = 15;
    private static final float MIN_DELAY = 0.1F;
    private static final float MAX_DELAY = 0.5F;
    private static final int FULL_CIRCLE = 90;
    private static final int NO_MORE_CIRCLES = 0;
    private static final int MOTION = 1;


    private int angle =  RANDOM.nextInt(-ROTATE_ANGLE, ROTATE_ANGLE);
    private int partOfCircle = NO_MORE_CIRCLES;


    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, LEAF_CIRCLE);
        setTag(LEAF_TAG);
        makeLeafMove();

    }

    private void makeLeafMove() {
        Runnable onElapsed = () -> {
            rotateLeaf();
            setDimensions(getDimensions());
        };

        new ScheduledTask(
                this, RANDOM.nextFloat(MIN_DELAY, MAX_DELAY), true, onElapsed);

    }

    private void rotateLeaf() {
        if (timeToCircle()) {
            angle = angle + ROTATE_ANGLE;
            partOfCircle -= MOTION;
        } else {
            angle = RANDOM.nextInt(angle - ROTATE_ANGLE, angle + ROTATE_ANGLE);
        }
        renderer().setRenderableAngle(angle);
    }

    private boolean timeToCircle() {
        return partOfCircle != NO_MORE_CIRCLES;
    }

    public void onJump() {
        partOfCircle = FULL_CIRCLE / ROTATE_ANGLE;
    }
}
