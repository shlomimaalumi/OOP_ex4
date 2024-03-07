package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;


public class Terrain {
    private static final String TERRAIN_TAG = "terrain";


    private static Terrain TERRAIN_INSTANCE = null;

    private static final float INIT_GROUND_HEIGHT_RATIO = 2 / 3f;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final RectangleRenderable rectangleRenderable =
            new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
    private final float groundHeightAtX0;
    private final Vector2 windowDimensions;


    private Terrain(Vector2 windowDimensions, int seed) {
        this.windowDimensions = windowDimensions;
        groundHeightAtX0 = windowDimensions.y() * INIT_GROUND_HEIGHT_RATIO;
    }

    public static Terrain create(Vector2 windowDimensions, int seed){
        if (TERRAIN_INSTANCE == null){
            TERRAIN_INSTANCE = new Terrain(windowDimensions,seed);
        }
        return TERRAIN_INSTANCE;
    }

    public float groundHeightAt(float x) {
        //TODO make this more interesting
        return (float) (Math.sin(x / 100) * 100 + groundHeightAtX0 + Math.cos(x / 33) * 33) - x%10 * 3;
    }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<Block>();
        minX = findClosestDivisibleLower(minX, Block.SIZE);
        maxX = findClosestDivisibleHigher(maxX, Block.SIZE);
        for (int x = minX; x < maxX; x += Block.SIZE) {
            blocks.addAll(createGroundBlocksAt(x));
        }
        return blocks;
    }

    private static int findClosestDivisibleLower(int x, int size) {
        return x - (x % size);
    }

    private static int findClosestDivisibleHigher(int x, int size) {
        return (x % size == 0) ? x : ((x / size) + 1) * size;
    }

    private Block createGroundBlocksAtX(int x, int y) {
        Block block = new Block(new Vector2(x, y), rectangleRenderable);
        block.setTag(TERRAIN_TAG);
        return block;
    }

    private List<Block> createGroundBlocksAt(int x) {
        List<Block> blocks = new ArrayList<>();
        int minY = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);

        for (int y = minY; y < windowDimensions.y(); y += Block.SIZE) {
            blocks.add(createGroundBlocksAtX(x, y));
        }
        return blocks;
    }
}
