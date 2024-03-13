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

public class Fruit extends GameObject {
    private static final float FRUIT_RADIUS = 30;
    private static final Vector2 FRUIT_DIMENSIONS = new Vector2(FRUIT_RADIUS, FRUIT_RADIUS);
    private static final float ENERGY_TO_ADD = 10;
    private static final int FIRST_COLOR = 0;
    private static final float CYCLE_TIME = 30f;


    private static final OvalRenderable[] COLORS_CIRCLE = {
            new OvalRenderable(Color.ORANGE),
            new OvalRenderable(Color.PINK),
            new OvalRenderable(Color.RED),
            new OvalRenderable(Color.YELLOW),
            new OvalRenderable(Color.BLUE),
            new OvalRenderable(Color.GREEN.brighter().brighter().brighter())
    };
    private final AddObjectInterface addObjectCallBack;
    private final RemoveObjectInterface removeObjectCallBack;

    private final Random random;
    private int colorIndex = FIRST_COLOR;

    public Fruit(Vector2 topLeftCorner, AddObjectInterface addObject, RemoveObjectInterface removeObject,
                 Random random) {
        super(topLeftCorner, FRUIT_DIMENSIONS, COLORS_CIRCLE[FIRST_COLOR]);
        this.addObjectCallBack = addObject;
        this.removeObjectCallBack = removeObject;
        this.random = random;

        addObject.addObject(this, Layer.DEFAULT);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (Objects.equals(other.getTag(), Avatar.AVATER_TAG)) {
            Avatar avatar = (Avatar) other;
            avatar.addEnergy(ENERGY_TO_ADD);
            removeObjectCallBack.removeObject(this);
            new ScheduledTask(
                    other, CYCLE_TIME, false, () -> addObjectCallBack.addObject(this, Layer.DEFAULT));
        }
    }


    public void onJump() {
        int index;
        do {
            index = random.nextInt(COLORS_CIRCLE.length);
        } while (index == colorIndex);
        this.renderer().setRenderable(COLORS_CIRCLE[index]);
        colorIndex = index;
    }
}
