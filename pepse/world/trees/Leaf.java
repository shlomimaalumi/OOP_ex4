package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
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
    private static final float ADDITIONAL_ANGLE_LEAF_JUMP = 90f;
    private static final float LEAF_TRANSITION_TIME = 2f;
    private static final Float INITIAL_ROTATION_TRANSITION = -10f;
    private static final Float FINAL_ROTATION_TRANSITION = 10f;
    private static final float TRANSITION_TIME_LEAF = 3f;
    private static final float LEAF_SCHEDULE_AT_MULTIPLIER = 3f;
    private static final Vector2 INITIAL_LEAF_SIZE = new Vector2(30, 30);
    private static final Vector2 FINAL_LEAF_SIZE = new Vector2(45, 45);


    private int angle = RANDOM.nextInt(-ROTATE_ANGLE, ROTATE_ANGLE);
    private int partOfCircle = NO_MORE_CIRCLES;
    private boolean timeToCircle;


    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, LEAF_CIRCLE);
        timeToCircle = false;
        setTag(LEAF_TAG);
        moveLeaf();

    }


    private void moveLeaf() {
        Runnable onElapsed = () -> {
            rotateLeaf();
            createSizeTransition();
        };
        float scheduleAt = RANDOM.nextFloat() * LEAF_SCHEDULE_AT_MULTIPLIER;

        new ScheduledTask(
                this, scheduleAt, true, onElapsed);
    }

    private void rotateLeaf() {
        if (timeToCircle) {
//            System.out.println("time to circle");
            return;
        }
        new Transition<Float>(
                this,
                this.renderer()::setRenderableAngle,
                INITIAL_ROTATION_TRANSITION,
                FINAL_ROTATION_TRANSITION,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                TRANSITION_TIME_LEAF,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    public void onJump() {
        timeToCircle = true;
        float startAngle = this.renderer().getRenderableAngle();
        float endAngle = startAngle + ADDITIONAL_ANGLE_LEAF_JUMP;
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                startAngle,
                endAngle,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                LEAF_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                () -> timeToCircle = false
        );
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return false;
    }


    private void createSizeTransition() {
        new Transition<Vector2>(
                this,
                this::setDimensions,
                INITIAL_LEAF_SIZE,
                FINAL_LEAF_SIZE,
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                TRANSITION_TIME_LEAF,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
}
