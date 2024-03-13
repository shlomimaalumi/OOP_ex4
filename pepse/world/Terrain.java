package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**
 * Represents the terrain in the game world.
 */
public class Terrain {
    /**
     * The tag used to identify terrain objects.
     */
    private static final String TERRAIN_TAG = "terrain";

    /**
     * The singleton instance of the terrain.
     */
    private static Terrain TERRAIN_INSTANCE = null;

    /**
     * The initial ratio of ground height relative to the window dimensions.
     */
    private static final float INIT_GROUND_HEIGHT_RATIO = 2 / 3f;

    /**
     * The base color of the ground.
     */
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);

    /**
     * The delta color used for creating variations in ground color.
     */
    private static final int DELTA_COLOR = 15;

    /**
     * The height of the ground at x-coordinate 0.
     */
    private final float groundHeightAtX0;

    /**
     * The dimensions of the game window.
     */
    private final Vector2 windowDimensions;

    /**
     * Private constructor for the Terrain class.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed used for randomization.
     */
    private Terrain(Vector2 windowDimensions, int seed) {
        this.windowDimensions = windowDimensions;
        groundHeightAtX0 = windowDimensions.y() * INIT_GROUND_HEIGHT_RATIO;
    }

    /**
     * Creates a new instance of the Terrain class.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed used for randomization.
     * @return The instance of the Terrain class.
     */
    public static Terrain create(Vector2 windowDimensions, int seed) {
        if (TERRAIN_INSTANCE == null) {
            TERRAIN_INSTANCE = new Terrain(windowDimensions, seed);
        }
        return TERRAIN_INSTANCE;
    }

    /**
     * Calculates the height of the ground at a given x-coordinate.
     *
     * @param x The x-coordinate.
     * @return The height of the ground at the specified x-coordinate.
     */
    public float groundHeightAt(float x) {
        //TODO make this more interesting
        return (float) (Math.sin(x / 100) * 100 + groundHeightAtX0 + Math.cos(x / 33) * 33) - x % 10 * 3;
    }

    /**
     * Creates ground blocks within a specified range of x-coordinates.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A list of ground blocks within the specified range.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        minX = findClosestDivisibleLower(minX);
        maxX = findClosestDivisibleHigher(maxX);
        for (int x = minX; x < maxX; x += Block.SIZE) {
            blocks.addAll(createGroundBlocksAt(x));
        }
        return blocks;
    }

    /**
     * Finds the closest lower divisible value of Block.SIZE to the given x-coordinate.
     *
     * @param x The x-coordinate.
     * @return The closest lower divisible value.
     */
    private static int findClosestDivisibleLower(int x) {
        return x - (x % Block.SIZE);
    }

    /**
     * Finds the closest higher divisible value of Block.SIZE to the given x-coordinate.
     *
     * @param x The x-coordinate.
     * @return The closest higher divisible value.
     */
    private static int findClosestDivisibleHigher(int x) {
        return (x % Block.SIZE == 0) ? x : ((x / Block.SIZE) + 1) * Block.SIZE;
    }

    /**
     * Creates a ground block at the specified x and y coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The created ground block.
     */
    private Block createGroundBlocksAtX(int x, int y) {
        RectangleRenderable rectangleRenderable =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR, DELTA_COLOR));
        Block block = new Block(new Vector2(x, y), rectangleRenderable);
        block.setTag(TERRAIN_TAG);
        return block;
    }

    /**
     * Creates ground blocks at the specified x-coordinate.
     *
     * @param x The x-coordinate.
     * @return A list of ground blocks at the specified x-coordinate.
     */
    private List<Block> createGroundBlocksAt(int x) {
        List<Block> blocks = new ArrayList<>();
        int minY = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);

        for (int y = minY; y < windowDimensions.y() + 300; y += Block.SIZE) {
            blocks.add(createGroundBlocksAtX(x, y));
        }
        return blocks;
    }
}
