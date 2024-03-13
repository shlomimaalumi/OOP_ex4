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

/**
 * Represents a tree object in the game world.
 */
public class Tree extends GameObject implements JumpObserver {

    /**
     * Maximum distance from the tree for leaves and fruits to be positioned.
     */
    private static final float MAX_RIGHT_DIS_FROM_TREE = 100;
    /**
     * Maximum distance from the tree for leaves and fruits to be positioned.
     */
    private static final float MAX_LEFT_DIS_FROM_TREE = 100;
    /**
     * Maximum distance from the tree for leaves and fruits to be positioned.
     */
    private static final float MAX_UP_DIS_FROM_TREE = 100;
    /**
     * Maximum distance from the tree for leaves and fruits to be positioned.
     */
    private static final float MAX_DOWN_DIS_FROM_TREE = 100;

    /**
     * Initial color of the tree trunk.
     */
    private static final Color INITIAL_TRUNK_COLOR = new Color(100, 50, 20);
    /**
     * Random generator for changing the trunk color.
     */
    private static final Random RANDOM = new Random();
    /**
     * Difference in color components for changing the trunk color.
     */
    private static final int DIFF = 20;

    /**
     * Color of the tree trunk.
     */
    private Color trunkColor = INITIAL_TRUNK_COLOR;
    /**
     * List of leaves in the tree.
     */
    private final List<Leaf> leaves = new ArrayList<>();
    /**
     * List of fruits in the tree.
     */
    private final List<Fruit> fruits = new ArrayList<>();
    /**
     * The trunk of the tree.
     */
    private GameObject trunk;

    /**
     * Constructs a Tree object with specified parameters.
     *
     * @param topLeftCorner Top-left corner position of the tree.
     * @param trunkWidth    Width of the tree trunk.
     * @param trunkHeight   Height of the tree trunk.
     * @param addObject     Interface to add objects to the game world.
     * @param removeObject  Interface to remove objects from the game world.
     * @param leavesAmount  Number of leaves to add to the tree.
     * @param fruitsAmount  Number of fruits to add to the tree.
     * @param random        Random generator for positioning leaves and fruits.
     */
    public Tree(Vector2 topLeftCorner, float trunkWidth, float trunkHeight, AddObjectInterface addObject,
                RemoveObjectInterface removeObject, int leavesAmount, int fruitsAmount, Random random) {
        super(topLeftCorner, Vector2.ZERO, null);
        trunkColor = INITIAL_TRUNK_COLOR;
        addTrunk(topLeftCorner, trunkWidth, trunkHeight, addObject);
        addLeaves(topLeftCorner, addObject, leavesAmount, random);
        addFruits(topLeftCorner, addObject, removeObject, fruitsAmount, random);
    }

    /**
     * Private method to add fruits to the tree.
     *
     * @param topLeftCorner Top-left corner position of the tree.
     * @param addObject     Interface to add objects to the game world.
     * @param removeObject  Interface to remove objects from the game world.
     * @param fruitsAmount  Number of fruits to add to the tree.
     * @param random        Random generator for positioning fruits.
     */
    private void addFruits(Vector2 topLeftCorner, AddObjectInterface addObject,
                           RemoveObjectInterface removeObject, int fruitsAmount, Random random) {
        // Add specified number of fruits to the tree
        for (int i = 0; i < fruitsAmount; i++) {
            float x = random.nextFloat(-MAX_LEFT_DIS_FROM_TREE, MAX_RIGHT_DIS_FROM_TREE);
            float y = random.nextFloat(-MAX_UP_DIS_FROM_TREE, MAX_DOWN_DIS_FROM_TREE);
            Vector2 fruitPosition = new Vector2(topLeftCorner).add(new Vector2(x, y));
            addFruit(fruitPosition, addObject, removeObject, random);
        }
    }

    /**
     * Private method to add a fruit to the tree.
     *
     * @param fruitPosition Position of the fruit.
     * @param addObject     Interface to add objects to the game world.
     * @param removeObject  Interface to remove objects from the game world.
     * @param random        Random generator for positioning fruits.
     */
    private void addFruit(Vector2 fruitPosition, AddObjectInterface addObject,
                          RemoveObjectInterface removeObject, Random random) {
        Fruit fruit = new Fruit(fruitPosition, addObject, removeObject, random);
        fruits.add(fruit);
    }

    /**
     * Private method to add leaves to the tree.
     *
     * @param topLeftCorner Top-left corner position of the tree.
     * @param addObject     Interface to add objects to the game world.
     * @param leavesAmount  Number of leaves to add to the tree.
     * @param random        Random generator for positioning leaves.
     */
    private void addLeaves(Vector2 topLeftCorner, AddObjectInterface addObject, int leavesAmount,
                           Random random) {
        // Add specified number of leaves to the tree
        for (int i = 0; i < leavesAmount; i++) {
            float x = random.nextFloat(-MAX_LEFT_DIS_FROM_TREE, MAX_RIGHT_DIS_FROM_TREE);
            float y = random.nextFloat(-MAX_UP_DIS_FROM_TREE, MAX_DOWN_DIS_FROM_TREE);
            Vector2 leafVector = new Vector2(topLeftCorner).add(new Vector2(x, y));
            addLeaf(leafVector, addObject);
        }
    }

    /**
     * Private method to add a leaf to the tree.
     *
     * @param topLeftCorner Top-left corner position of the leaf.
     * @param addObject     Interface to add objects to the game world.
     */
    private void addLeaf(Vector2 topLeftCorner, AddObjectInterface addObject) {
        Leaf leaf = new Leaf(topLeftCorner);
        leaves.add(leaf);
        addObject.addObject(leaf, Layer.STATIC_OBJECTS);
    }

    /**
     * Private method to add the trunk to the tree.
     *
     * @param topLeftCorner Top-left corner position of the trunk.
     * @param trunkWidth    Width of the trunk.
     * @param trunkHeight   Height of the trunk.
     * @param addObject     Interface to add objects to the game world.
     */
    private void addTrunk(Vector2 topLeftCorner, float trunkWidth, float trunkHeight,
                          AddObjectInterface addObject) {
        RectangleRenderable trunkRenderable = new RectangleRenderable(trunkColor);
        this.trunk = new GameObject(topLeftCorner, new Vector2(trunkWidth, trunkHeight), trunkRenderable);
        trunk.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS - 1);
        trunk.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        addObject.addObject(trunk, Layer.STATIC_OBJECTS);
    }

    /**
     * Changes the color of the tree trunk.
     */
    private void chageTrunkColor() {
        int r = INITIAL_TRUNK_COLOR.getRed() + RANDOM.nextInt(-DIFF, DIFF);
        int g = INITIAL_TRUNK_COLOR.getGreen() + RANDOM.nextInt(-DIFF, DIFF);
        int b = INITIAL_TRUNK_COLOR.getBlue() + RANDOM.nextInt(-DIFF, DIFF);
        trunk.renderer().setRenderable(new RectangleRenderable(new Color(r, g, b)));
    }

    /**
     * Handles the behavior of the tree when a jump event occurs.
     */
    @Override
    public void onJump() {
        chageTrunkColor();
        // Notify leaves and fruits of the jump event
        for (Leaf leaf : leaves) {
            leaf.onJump();
        }
        for (Fruit fruit : fruits) {
            fruit.onJump();
        }
    }

    /**
     * Constructs a Tree object with the specified position.
     *
     * @param position Position of the tree.
     */
    public Tree(Vector2 position) {
        super(position, null, null);
    }
}