package pepse.world;

import danogl.*;
import danogl.gui.*;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;
import pepse.JumpObserver;

import java.util.ArrayList;
import java.util.List;

import java.awt.event.KeyEvent;

/**
 * Represents the avatar character in the game world.
 */
public class Avatar extends GameObject {
    /** Tag identifying the avatar object. */
    public static final String AVATAR_TAG = "avatar";

    /** Array containing paths to idle animation images. */
    private static final String[] IDLE_PATHS = new String[]{
            "assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png"
    };

    /** Array containing paths to jump animation images. */
    private static final String[] JUMP_PATHS = new String[]{
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"
    };

    /** Array containing paths to run animation images. */
    private static final String[] RUN_PATHS = new String[]{
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png"
    };

    /** List of image renderables for idle animations. */
    private static final ImageRenderable[] IDLE_IMAGE_LIST = new ImageRenderable[IDLE_PATHS.length];

    /** List of image renderables for jump animations. */
    private static final ImageRenderable[] JUMP_IMAGE_LIST = new ImageRenderable[JUMP_PATHS.length];

    /** List of image renderables for run animations. */
    private static final ImageRenderable[] RUN_IMAGE_LIST = new ImageRenderable[RUN_PATHS.length];

    /** Time between animation clips. */
    private static final double TIME_BETWEEN_CLIPS = 0.25;

    /** Animation renderable for idle animations. */
    private static AnimationRenderable IDLE_ANIMATION = null;

    /** Animation renderable for run animations. */
    private static AnimationRenderable RUN_ANIMATION = null;

    /** Animation renderable for jump animations. */
    private static AnimationRenderable JUMP_ANIMATION = null;

    /** Velocity on the X axis. */
    private static final float VELOCITY_X = 400;

    /** Velocity on the Y axis. */
    private static final float VELOCITY_Y = -650;

    /** Gravity force. */
    private static final float GRAVITY = 600;

    /** Initial energy level. */
    private static final float INIT_ENERGY = 100f;

    /** Energy consumption rate while running. */
    private static final float RUN_ENERGY = 0.5f;

    /** Energy consumption rate while jumping. */
    private static final float JUMP_ENERGY = 10f;

    /** Energy consumption rate while idle. */
    private static final float IDLE_ENERGY = 1f;

    /** Resting position on the X axis. */
    private static final float RESTING_POSITION = 0f;

    /** Energy multiplier for performing double actions. */
    private static final float DOUBLE_ACTIONS = 2f;

    /** List of observers for avatar jumps. */
    private final List<JumpObserver> jumpObservers;

    /** Input listener for avatar controls. */
    private final UserInputListener inputListener;

    /** Energy level of the avatar. */
    private float energy;

    /** Direction constant: right. */
    private static final int RIGHT = 1;

    /** Direction constant: left. */
    private static final int LEFT = -1;

    /** Last movement direction. */
    private int lastDirection = RIGHT;

    /**
     * Constructs an avatar object with specified parameters.
     *
     * @param pos         Initial position of the avatar.
     * @param inputListener Listener for avatar controls.
     * @param imageReader  Reader for loading image resources.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(50).multY(2), imageReader.readImage(IDLE_PATHS[0], true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        energy = INIT_ENERGY;
        setTag(AVATAR_TAG);
        initAnimationsLists(imageReader);
        jumpObservers = new ArrayList<>();
    }

    /**
     * Initializes the lists of animation images.
     *
     * @param imageReader Reader for loading image resources.
     */
    private void initAnimationsLists(ImageReader imageReader) {
        initializeAnimationList(IDLE_IMAGE_LIST, IDLE_PATHS, imageReader);
        initializeAnimationList(JUMP_IMAGE_LIST, JUMP_PATHS, imageReader);
        initializeAnimationList(RUN_IMAGE_LIST, RUN_PATHS, imageReader);
        setAnimationList();
    }

    /**
     * Initializes a list of animation images from file paths.
     *
     * @param animationList List to store the animation images.
     * @param paths         Array of file paths to the animation images.
     * @param imageReader   Reader for loading image resources.
     */
    private void initializeAnimationList(ImageRenderable[] animationList, String[] paths, ImageReader imageReader) {
        for (int i = 0; i < animationList.length; i++) {
            animationList[i] = imageReader.readImage(paths[i], true);
        }
    }

    /**
     * Sets the animation lists for different actions.
     */
    private void setAnimationList() {
        IDLE_ANIMATION = new AnimationRenderable(IDLE_IMAGE_LIST, TIME_BETWEEN_CLIPS);
        JUMP_ANIMATION = new AnimationRenderable(JUMP_IMAGE_LIST, TIME_BETWEEN_CLIPS);
        RUN_ANIMATION = new AnimationRenderable(RUN_IMAGE_LIST, TIME_BETWEEN_CLIPS);
    }

    /**
     * Adds energy to the avatar.
     *
     * @param energyToAdd Energy to add.
     */
    public void addEnergy(float energyToAdd) {
        energy = Math.min(energy + energyToAdd, INIT_ENERGY);
    }

    /**
     * Updates the avatar's state based on user input and energy consumption.
     *
     * @param deltaTime Time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = runChecker();
        transform().setVelocityX(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && handleJump()) {
            notifyJumpObservers();
            transform().setVelocityY(VELOCITY_Y);
        }
        energyAndAnimationUpdate();
    }

    /**
     * Updates the avatar's energy level and animation based on its state.
     */
    private void energyAndAnimationUpdate() {
        if (getVelocity().equals(Vector2.ZERO)) {
            energy = Math.min(energy + IDLE_ENERGY, INIT_ENERGY);
            renderer().setRenderable(IDLE_ANIMATION);
        } else {
            if (getVelocity().x() != 0) {
                if (getVelocity().x() < 0 && lastDirection == RIGHT) {
                    renderer().setIsFlippedHorizontally(true);
                    lastDirection = LEFT;
                } else if (getVelocity().x() > 0 && lastDirection == LEFT) {
                    renderer().setIsFlippedHorizontally(false);
                    lastDirection = RIGHT;
                }
                renderer().setRenderable(RUN_ANIMATION);
            } else {
                renderer().setRenderable(JUMP_ANIMATION);
            }
        }
    }

    /**
     * Checks for user input related to horizontal movement.
     *
     * @return Horizontal velocity based on user input.
     */
    private float runChecker() {
        float xVel = RESTING_POSITION;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && handleRun()) {
            xVel -= VELOCITY_X;
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && handleRun()) {
            if (xVel == RESTING_POSITION) {
                xVel += VELOCITY_X;
            } else {
                xVel = RESTING_POSITION;
                energy += DOUBLE_ACTIONS * RUN_ENERGY;
            }
        }
        return xVel;
    }

    /**
     * Handles the avatar's running action.
     *
     * @return True if the avatar can run, false otherwise.
     */
    private boolean handleRun() {
        if (energy < RUN_ENERGY) {
            return false;
        }
        energy -= RUN_ENERGY;
        return true;
    }

    /**
     * Handles the avatar's jumping action.
     *
     * @return True if the avatar can jump, false otherwise.
     */
    private boolean handleJump() {
        if (energy < JUMP_ENERGY) {
            return false;
        }
        energy -= JUMP_ENERGY;
        return true;
    }

    /**
     * Adds a jump observer to the avatar.
     *
     * @param observer Observer to add.
     */
    public void addJumpObserver(JumpObserver observer) {
        jumpObservers.add(observer);
    }

    /**
     * Notifies all jump observers when the avatar jumps.
     */
    private void notifyJumpObservers() {
        for (JumpObserver observer : jumpObservers) {
            observer.onJump();
        }
    }

    /**
     * Gets the current energy level of the avatar.
     *
     * @return Current energy level.
     */
    public Float getEnergy() {
        return energy;
    }
}
