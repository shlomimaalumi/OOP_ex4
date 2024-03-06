package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.JumpObserver;
import pepse.world.Avatar;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Fruit extends GameObject {
    private final GameObjectCollection gameObjects;
    private static final float FRUIT_RADIUS = 30;
    private static final Vector2 FRUIT_DIMENSIONS = new Vector2(FRUIT_RADIUS,FRUIT_RADIUS);
    private static final float ENERGY_TO_ADD = 100;
    private static final int FIRST_COLOR = 0;


//    private final static Color[] FRUITS_COLOR = new Color[]{Color.GREEN,Color.RED,Color.ORANGE,Color.YELLOW};
    private static final OvalRenderable[] COLORS_CIRCLE = {
            new OvalRenderable(Color.ORANGE),
            new OvalRenderable(Color.PINK),
            new OvalRenderable(Color.RED),
            new OvalRenderable(Color.YELLOW),
            new OvalRenderable(Color.BLUE),
            new OvalRenderable(Color.GREEN.brighter().brighter().brighter())
    };

    private OvalRenderable fruitOval;
    private final Random random;
    private int colorIndex = FIRST_COLOR;

    public Fruit(Vector2 topLeftCorner, GameObjectCollection gameObjects, Random random) {
        super(topLeftCorner, FRUIT_DIMENSIONS, COLORS_CIRCLE[FIRST_COLOR]);
        this.gameObjects=gameObjects;
        this.random = random;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (Objects.equals(other.getTag(), Avatar.AVATER_TAG)){
            Avatar avatar=(Avatar) other;
            avatar.addEnergy(ENERGY_TO_ADD);
            gameObjects.removeGameObject(this);
        }
    }

    public void onJump() {
        int index;
        do{
            index = random.nextInt(COLORS_CIRCLE.length);
        } while(index==colorIndex);
        this.renderer().setRenderable(COLORS_CIRCLE[index]);
        colorIndex=index;
    }
}
