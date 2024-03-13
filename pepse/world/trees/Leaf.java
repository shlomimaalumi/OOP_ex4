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
    private static final float ADDITIONAL_ANGLE_LEAF_JUMP = 90f;
    private static final float TIME_TO_TRANSITION_CYCLE = 3f;
    private static final Float INITIAL_TRANSITION_VAL = -12f;
    private static final Float FINAL_ROTATION_TRANSITION = 12f;
    private static final float TRANSITION_LEAF_TIME = 3f;
    private static final float LEAF_SCHEDULE = 3f;
    private static final Vector2 BASE_LEAF_SIZE = new Vector2(37, 37);
    private static final Vector2 BIGGEST_LEAF_SIZE = new Vector2(43, 43);
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
        float scheduleAt = RANDOM.nextFloat() * LEAF_SCHEDULE;

        new ScheduledTask(
                this, scheduleAt, true, onElapsed);
    }

    private void rotateLeaf() {
        if (timeToCircle) {
//            System.out.println("time to circle");
            return;
        }
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                INITIAL_TRANSITION_VAL,
                FINAL_ROTATION_TRANSITION,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                TRANSITION_LEAF_TIME,
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
                TIME_TO_TRANSITION_CYCLE,
                Transition.TransitionType.TRANSITION_ONCE,
                () -> timeToCircle = false
        );
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return false;
    }


    private void createSizeTransition() {
        new Transition<>(
                this,
                this::setDimensions,
                BASE_LEAF_SIZE,
                BIGGEST_LEAF_SIZE,
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                TRANSITION_LEAF_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
}