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
    private static final int DELTA_COLOR = 15;
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
        List<Block> blocks = new ArrayList<>();
        minX = findClosestDivisibleLower(minX);
        maxX = findClosestDivisibleHigher(maxX);
        for (int x = minX; x < maxX; x += Block.SIZE) {
            blocks.addAll(createGroundBlocksAt(x));
        }
        return blocks;
    }

    private static int findClosestDivisibleLower(int x) {
        return x - (x % Block.SIZE);
    }

    private static int findClosestDivisibleHigher(int x) {
        return (x % Block.SIZE == 0) ? x : ((x / Block.SIZE) + 1) * Block.SIZE;
    }

    private Block createGroundBlocksAtX(int x, int y) {
        RectangleRenderable rectangleRenderable =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR, DELTA_COLOR));
        Block block = new Block(new Vector2(x, y), rectangleRenderable);
        block.setTag(TERRAIN_TAG);
        return block;
    }

    private List<Block> createGroundBlocksAt(int x) {
        List<Block> blocks = new ArrayList<>();
        int minY = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);

        for (int y = minY; y < windowDimensions.y()+300; y += Block.SIZE) {
            blocks.add(createGroundBlocksAtX(x, y));
        }
        return blocks;
    }
}
