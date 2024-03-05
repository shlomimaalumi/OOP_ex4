package pepse.world;

import danogl.*;
import danogl.gui.*;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;


public class Avatar extends GameObject {


    private static final String AVATER_TAG = "avater";

    public static final int IDLE_STATE = 0;
    public static final int JUMP_NO_MOVE_STATE = 1;
    public static final int RUN_STATE = 2;

    private static final String[] IDLE_PATHES = new String[]{"assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png"
    };

    private static final String[] JUMP_PATHES = new String[]{
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"
    };


    private static final String[] RUN_PATHES = new String[]{
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png"
    };

    private static final ImageRenderable[] IDLE_IMAGE_LIST = new ImageRenderable[IDLE_PATHES.length];
    private static final ImageRenderable[] JUMP_IMAGE_LIST = new ImageRenderable[JUMP_PATHES.length];
    private static final ImageRenderable[] RUN_IMAGE_LIST = new ImageRenderable[RUN_PATHES.length];


    private static final double TIME_BETWEEN_CLIPS = 0.25;

    private static AnimationRenderable IDLE_ANIMATION = null;
    private static AnimationRenderable RUN_ANIMATION = null;
    private static AnimationRenderable JUMP_ANIMATION = null;


    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final float INIT_ENERGY = 100f;
    private static final float RUN_ENERGY = 0.5f;
    private static final float JUMP_ENERGY = 10f;
    private static final float IDLE_ENERGY = 1f;
    private static final float RESTING_POSITION = 0f;
    private static final float DOUBLE_ACTIONS = 2f;


    private final UserInputListener inputListener;
    private float energy;
    private int state = IDLE_STATE;
    private int imageCounter = 0;
    private static final int RIGHT = 1;
    private static final int LEFT = -1;
    private static final int LOWER_BOUNDER = -1;
    private static final int INITIAL_COUNTER = 0;
    private int lastDirection = RIGHT;


    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(50).multY(2), imageReader.readImage(IDLE_PATHES[0],
                true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        energy = INIT_ENERGY;
        setTag(AVATER_TAG);
        initAnimationsLists(imageReader);
    }

    private void initAnimationsLists(ImageReader imageReader) {
        initializeAnimationList(IDLE_IMAGE_LIST, IDLE_PATHES, imageReader);
        initializeAnimationList(JUMP_IMAGE_LIST, JUMP_PATHES, imageReader);
        initializeAnimationList(RUN_IMAGE_LIST, RUN_PATHES, imageReader);
        setAnimationList();
    }

    private void initializeAnimationList(ImageRenderable[] animationList, String[] paths,
                                         ImageReader imageReader) {
        for (int i = 0; i < animationList.length; i++) {
            animationList[i] = imageReader.readImage(paths[i], true);
        }
    }

    private void setAnimationList() {
        IDLE_ANIMATION = new AnimationRenderable(IDLE_IMAGE_LIST, TIME_BETWEEN_CLIPS);
        JUMP_ANIMATION = new AnimationRenderable(JUMP_IMAGE_LIST, TIME_BETWEEN_CLIPS);
        RUN_ANIMATION = new AnimationRenderable(RUN_IMAGE_LIST, TIME_BETWEEN_CLIPS);
    }


    public void addEnergy(float energyToAdd) {
        energy = Math.min(energy + energyToAdd, INIT_ENERGY);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = runChecker();
        // TODO HANDLE RIGHT AND LEFT TOGETHER
        // TODO GRAPHIC ENERGY
        transform().setVelocityX(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && handleJump()) {
            transform().setVelocityY(VELOCITY_Y);
        }
        energyAndAnimationUpdate();
    }


    private void energyAndAnimationUpdate() {
        ImageRenderable[] listToRenere = null;
        if (getVelocity().equals(Vector2.ZERO)) {
            //we are not moving
            energy = Math.min(energy + IDLE_ENERGY, INIT_ENERGY);
            renderer().setRenderable(IDLE_ANIMATION);
        } else {
            if (getVelocity().x() != 0) {
                //we are moving on the X axis
                if (getVelocity().x() < 0 && lastDirection == RIGHT) {
                    renderer().setIsFlippedHorizontally(true);
                    lastDirection = LEFT;
                } else if (getVelocity().x() > 0 && lastDirection == LEFT) {
                    renderer().setIsFlippedHorizontally(false);
                    lastDirection =RIGHT;
                }
                renderer().setRenderable(RUN_ANIMATION);
            } else {
                // we are moving just on the Y axis
                renderer().setRenderable(JUMP_ANIMATION);
            }
        }
    }


    private float runChecker() {
        float xVel = RESTING_POSITION;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && handleRun()) {
            xVel -= VELOCITY_X;
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && handleRun()) {
            if (xVel == RESTING_POSITION) {
                xVel += VELOCITY_X;
            } else { //clicking right and left at the same time
                xVel = RESTING_POSITION;
                energy += DOUBLE_ACTIONS * RUN_ENERGY;
            }
        }
        return xVel;
    }

    private boolean handleRun() {
        if (energy < RUN_ENERGY) {
            return false;
        }
        energy -= RUN_ENERGY;
        return true;
    }

    private boolean handleJump() {
        if (energy < JUMP_ENERGY) {
            return false;
        }
        energy -= JUMP_ENERGY;
        return true;
    }

//    private void setAnimation(List<ImageRenderable> animationList){
//        this.renderer().setRenderable();
//        renderer().setIsFlippedHorizontally();
//    }


}

