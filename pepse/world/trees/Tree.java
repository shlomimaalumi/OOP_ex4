package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.JumpObserver;
import pepse.world.AddObjectInterface;
import pepse.world.RemoveObjectInterface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree extends GameObject implements JumpObserver {

    private static final float MAX_RIGHT_DIS_FROM_TREE = 100;
    private static final float MAX_LEFT_DIS_FROM_TREE = 100;
    private static final float MAX_UP_DIS_FROM_TREE = 100;
    private static final float MAX_DOWN_DIS_FROM_TREE = 100;
    private static final Color INITIAL_TRUNK_COLOR = new Color(100, 50, 20);
    private static final Random RANDOM = new Random();
    private static final int DIFF = 20;


    private Color trunkColor = INITIAL_TRUNK_COLOR;
    List<Leaf> leaves = new ArrayList<>();
    List<Fruit> fruits = new ArrayList<>();
    private GameObject trunk;




    public Tree(Vector2 topLeftCorner, float trunkWidth, float trunkHeight, AddObjectInterface addObject,
                RemoveObjectInterface removeObject,
                int leavesAmount, int fruitsAmount, Random random) {
        super(topLeftCorner, Vector2.ZERO, null);
        trunkColor = INITIAL_TRUNK_COLOR;
        addTrunk(topLeftCorner, trunkWidth, trunkHeight, addObject);
        addLeaves(topLeftCorner, addObject, leavesAmount, random);
        addFruits(topLeftCorner, addObject, removeObject, fruitsAmount, random);
    }

    private void addFruits(Vector2 topLeftCorner, AddObjectInterface addObject,
                           RemoveObjectInterface removeObject,
                           int fruitsAmount,
                           Random random) {
        for (int i = 0; i < fruitsAmount; i++) {
            float x = random.nextFloat(-MAX_LEFT_DIS_FROM_TREE, MAX_RIGHT_DIS_FROM_TREE);
            float y = random.nextFloat(-MAX_UP_DIS_FROM_TREE, MAX_DOWN_DIS_FROM_TREE);
            Vector2 fruitPosition = new Vector2(topLeftCorner).add(new Vector2(x, y));
            addFruit(fruitPosition, addObject, removeObject, random);
        }
    }

    private void addFruit(Vector2 fruitPosition, AddObjectInterface addObject,
                          RemoveObjectInterface removeObject,
                          Random random) {
        Fruit fruit = new Fruit(fruitPosition, addObject, removeObject, random);
        fruits.add(fruit);
    }

    private void addLeaves(Vector2 topLeftCorner, AddObjectInterface addObject, int leavesAmount,
                           Random random) {
        for (int i = 0; i < leavesAmount; i++) {
            float x = random.nextFloat(-MAX_LEFT_DIS_FROM_TREE, MAX_RIGHT_DIS_FROM_TREE);
            float y = random.nextFloat(-MAX_UP_DIS_FROM_TREE, MAX_DOWN_DIS_FROM_TREE);
            Vector2 leafVector = new Vector2(topLeftCorner).add(new Vector2(x, y));
            addLeaf(leafVector, addObject);
        }
    }

    private void addLeaf(Vector2 topLeftCorner, AddObjectInterface addObject) {
        Leaf leaf = new Leaf(topLeftCorner);
        leaves.add(leaf);
        addObject.addObject(leaf, Layer.STATIC_OBJECTS);
    }

    private void addTrunk(Vector2 topLeftCorner, float trunkWidth, float trunkHeight,
                          AddObjectInterface addObject) {
        RectangleRenderable trunkRenderable = new RectangleRenderable(trunkColor);
        this.trunk = new GameObject(topLeftCorner, new Vector2(trunkWidth, trunkHeight), trunkRenderable);
        trunk.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS - 1);
        trunk.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        addObject.addObject(trunk, Layer.STATIC_OBJECTS);
        }

    private void chageTrunkColor() {
        int r = INITIAL_TRUNK_COLOR.getRed() + RANDOM.nextInt(-DIFF, DIFF);
        int g = INITIAL_TRUNK_COLOR.getGreen() + RANDOM.nextInt(-DIFF, DIFF);
        int b = INITIAL_TRUNK_COLOR.getBlue() + RANDOM.nextInt(-DIFF, DIFF);
        trunk.renderer().setRenderable(new RectangleRenderable(new Color(r, g, b)));
    }

    @Override
    public void onJump() {
        chageTrunkColor();
        for (Leaf leaf : leaves) {
            leaf.onJump();
        }
        for (Fruit fruit : fruits) {
            fruit.onJump();
        }

    }


    public Tree(Vector2 position) {
        super(position, null, null);
    }

}
