package pepse.world;

import danogl.*;
import danogl.gui.*;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;



public class Avatar extends GameObject {


    private static final String AVATER_TAG = "avater";
    private static final String AVATER_PATH = "assets/idle_0.png";

    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final float INIT_ENERGY = 100f;
    private static final float RUN_ENERGY = 0.5f;
    private static final float JUMP_ENERGY = 10f;
    private static final float IDLE_ENERGY = 1f;
    private static final float RESTING_POSITION = 0f;
    private static final float DOUBLE_ACTIONS = 2f;



    private UserInputListener inputListener;
    private float energy;


    public Avatar(Vector2 pos, UserInputListener inputListener,ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(50).multY(2), imageReader.readImage(AVATER_PATH,
                true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        energy = INIT_ENERGY;
        setTag(AVATER_TAG);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = runChecker();
        //TODO HANDLE RIGHT AND LEFT TOGETHER
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && handleJump()){
            transform().setVelocityY(VELOCITY_Y);
        }
        if (getVelocity().equals(Vector2.ZERO)){
            energy = Math.min(energy+IDLE_ENERGY,INIT_ENERGY);
        }
    }

    private float runChecker(){
        float xVel = RESTING_POSITION;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && handleRun()){
            xVel -= VELOCITY_X;
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && handleRun()){
            if (xVel ==RESTING_POSITION){
                xVel += VELOCITY_X;
            }else { //clicking right and left at the same time
                xVel = RESTING_POSITION;
                energy += DOUBLE_ACTIONS * RUN_ENERGY;
            }
        }
        return xVel;
    }

    private boolean handleRun() {
        if (energy < RUN_ENERGY){
            return false;
        }
        energy -= RUN_ENERGY;
        return true;
    }

    private boolean handleJump() {
        if (energy < JUMP_ENERGY){
            return false;
        }
        energy -= JUMP_ENERGY;
        return true;
    }
}