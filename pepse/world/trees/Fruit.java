package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.Layer;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.AddObjectInterface;
import pepse.world.Avatar;
import pepse.world.RemoveObjectInterface;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a fruit object in the game world.
 */
public class Fruit extends GameObject {
    /** Radius of the fruit. */
    private static final float FRUIT_RADIUS = 30;

    /** Dimensions of the fruit. */
    private static final Vector2 FRUIT_DIMENSIONS = new Vector2(FRUIT_RADIUS, FRUIT_RADIUS);

    /** Amount of energy to add when collected. */
    private static final float ENERGY_TO_ADD = 10;

    /** Index of the first color in the colors array. */
    private static final int FIRST_COLOR = 0;

    /** Time for the fruit to respawn after being collected. */
    private static final float CYCLE_TIME = 30f;

    /** Array of renderables representing different colors for the fruit. */
    private static final OvalRenderable[] COLORS_CIRCLE = {
            new OvalRenderable(Color.ORANGE),
            new OvalRenderable(Color.PINK),
            new OvalRenderable(Color.RED),
            new OvalRenderable(Color.YELLOW),
            new OvalRenderable(Color.BLUE),
            new OvalRenderable(Color.GREEN.brighter().brighter().brighter())
    };

    /** Callback for adding objects to the world. */
    private final AddObjectInterface addObjectCallBack;

    /** Callback for removing objects from the world. */
    private final RemoveObjectInterface removeObjectCallBack;

    /** Random object for generating random values. */
    private final Random random;

    /** Index of the current color of the fruit. */
    private int colorIndex = FIRST_COLOR;

    /**
     * Constructs a fruit object with given parameters.
     *
     * @param topLeftCorner      The top left corner position of the fruit.
     * @param addObject          Interface for adding objects to the world.
     * @param removeObject       Interface for removing objects from the world.
     * @param random             Random object for generating random values.
     */
    public Fruit(Vector2 topLeftCorner, AddObjectInterface addObject, RemoveObjectInterface removeObject,
                 Random random) {
        super(topLeftCorner, FRUIT_DIMENSIONS, COLORS_CIRCLE[FIRST_COLOR]);
        this.addObjectCallBack = addObject;
        this.removeObjectCallBack = removeObject;
        this.random = random;

        addObject.addObject(this, Layer.DEFAULT);
    }

    /**
     * Handles the behavior of the fruit when a collision occurs.
     *
     * @param other     The game object involved in the collision.
     * @param collision Details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        // Check if the colliding object is the avatar
        if (Objects.equals(other.getTag(), Avatar.AVATAR_TAG)) {
            // Cast the colliding object to Avatar
            Avatar avatar = (Avatar) other;
            // Add energy to the avatar
            avatar.addEnergy(ENERGY_TO_ADD);
            // Remove the fruit object from the world
            removeObjectCallBack.removeObject(this);
            // Schedule the fruit to respawn after a certain time
            new ScheduledTask(
                    other, CYCLE_TIME, false, () -> addObjectCallBack.addObject(this, Layer.DEFAULT));
        }
    }


    /**
     * Method called when the avatar jumps, changes the color of the fruit.
     */
    public void onJump() {
        int index;
        do {
            index = random.nextInt(COLORS_CIRCLE.length);
        } while (index == colorIndex);
        this.renderer().setRenderable(COLORS_CIRCLE[index]);
        colorIndex = index;
    }
}
