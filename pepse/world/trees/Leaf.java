package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.Color;
import java.util.Random;

/**
 * The Leaf class represents a leaf object in the game world.
 */
public class Leaf extends Block {

    /**
     * The color of the leaf.
     */
    private static final Color LEAF_COLOR = new Color(50, 200, 30);

    /**
     * The renderable representing the leaf.
     */
    private static final RectangleRenderable LEAF_CIRCLE = new RectangleRenderable(LEAF_COLOR);

    /**
     * The tag identifying the leaf object.
     */
    private static final String LEAF_TAG = "leaf";

    /**
     * A random number generator used for scheduling leaf movements.
     */
    private static final Random RANDOM = new Random();

    /**
     * The additional angle for leaf jump animation.
     */
    private static final float ADDITIONAL_ANGLE_LEAF_JUMP = 90f;

    /**
     * The time taken to complete the transition cycle.
     */
    private static final float TIME_TO_TRANSITION_CYCLE = 3f;

    /**
     * The initial value for leaf transition.
     */
    private static final Float INITIAL_TRANSITION_VAL = -12f;

    /**
     * The final rotation value for leaf transition.
     */
    private static final Float FINAL_ROTATION_TRANSITION = 12f;

    /**
     * The time taken for leaf transition.
     */
    private static final float TRANSITION_LEAF_TIME = 3f;

    /**
     * The schedule time for leaf animation.
     */
    private static final float LEAF_SCHEDULE = 3f;

    /**
     * The base size of the leaf.
     */
    private static final Vector2 BASE_LEAF_SIZE = new Vector2(37, 37);

    /**
     * The largest size of the leaf.
     */
    private static final Vector2 BIGGEST_LEAF_SIZE = new Vector2(43, 43);

    /**
     * Flag indicating if it's time for leaf circle animation.
     */
    private boolean timeToCircle;

    /**
     * Constructs a Leaf object with the specified position.
     *
     * @param topLeftCorner The top-left corner position of the leaf.
     */
    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, LEAF_CIRCLE);
        timeToCircle = false;
        setTag(LEAF_TAG);
        moveLeaf();
    }

    /**
     * Moves the leaf by scheduling leaf animations.
     */
    private void moveLeaf() {
        Runnable onElapsed = () -> {
            rotateLeaf();
            createSizeTransition();
        };
        float scheduleAt = RANDOM.nextFloat() * LEAF_SCHEDULE;

        new ScheduledTask(
                this, scheduleAt, true, onElapsed);
    }

    /**
     * Rotates the leaf with transition animation.
     */
    private void rotateLeaf() {
        if (timeToCircle) {
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

    /**
     * Triggers the leaf jump animation.
     */
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

    /**
     * Determines whether the leaf should collide with another game object.
     *
     * @param other The other game object.
     * @return Always returns false, indicating no collision.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return false;
    }

    /**
     * Creates a size transition for the leaf.
     */
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
