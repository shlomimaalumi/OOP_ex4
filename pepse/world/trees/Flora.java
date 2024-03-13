package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the flora in the game world, including trees.
 */
public class Flora {
    /** Minimum height of the tree trunk. */
    private static final int MIN_TRUNK_HEIGHT = 200;

    /** Maximum height of the tree trunk. */
    private static final int MAX_TRUNK_HEIGHT = 300;

    /** Width of the tree trunk. */
    private final static int TRUNK_WIDTH = 30;

    /** Minimum amount of leaves on the tree. */
    private static final int MIN_LEAVES_AMOUNT = 20;

    /** Maximum amount of leaves on the tree. */
    private static final int MAX_LEAVES_AMOUNT = 30;

    /** Minimum amount of fruit on the tree. */
    private static final int MIN_FRUIT_AMOUNT = 2;

    /** Maximum amount of fruit on the tree. */
    private static final int MAX_FRUIT_AMOUNT = 6;

    /** Ratio determining the space between tree trunks. */
    private static final int SPACE_BETWEEN_TRUNKS_RATIO = 3;

    /** Value indicating tree creation. */
    private static final int CREATE_TREE = 0;

    /** Chance for tree creation. */
    private static final int CHANCE_FOR_CREATE_TREE = 10;

    /** Interface for adding objects to the world. */
    private final AddObjectInterface addObject;

    /** Interface for removing objects from the world. */
    private final RemoveObjectInterface removeObject;

    /** Terrain object representing the ground. */
    private final Terrain terrain;

    /** Random object for generating random values. */
    private final Random random;

    /**
     * Constructs Flora object with given interfaces and terrain.
     *
     * @param addObject    Interface for adding objects to the world.
     * @param removeObject Interface for removing objects from the world.
     * @param terrain      Terrain object representing the ground.
     */
    public Flora(AddObjectInterface addObject, RemoveObjectInterface removeObject, Terrain terrain) {
        this.addObject = addObject;
        this.removeObject = removeObject;
        this.terrain = terrain;
        this.random = new Random();
    }

    /**
     * Creates trees within the specified range.
     *
     * @param minX Minimum x-coordinate for tree creation.
     * @param maxX Maximum x-coordinate for tree creation.
     * @return List of trees created within the range.
     */
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        for (int i = minX; i <= maxX; i += Block.SIZE) {
            if (random.nextInt(CHANCE_FOR_CREATE_TREE) == CREATE_TREE) {
                float groundHeight = terrain.groundHeightAt((float) i);
                int trunkHeight = random.nextInt(MIN_TRUNK_HEIGHT, MAX_TRUNK_HEIGHT);
                int trunkWidth = TRUNK_WIDTH;
                int leavesAmount = random.nextInt(MIN_LEAVES_AMOUNT, MAX_LEAVES_AMOUNT);
                int fruitAmount = random.nextInt(MIN_FRUIT_AMOUNT, MAX_FRUIT_AMOUNT);
                Vector2 topLeftCorner = new Vector2(i, groundHeight - trunkHeight);
                Tree tree = new Tree(topLeftCorner, trunkWidth, (float) trunkHeight, addObject, removeObject,
                        leavesAmount, fruitAmount, random);
                i += SPACE_BETWEEN_TRUNKS_RATIO * trunkWidth;
                trees.add(tree);
            }
        }
        return trees;
    }
}
