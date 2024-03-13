package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
/**
 * Represents a block in the game world.
 */

public class Block extends GameObject {
    /** Size of the block. */
    public static final int SIZE = 30;

    /** Tag identifying the block object. */
    public static String BLOCK_TAG = "block";

    /**
     * Constructs a block object with specified parameters.
     *
     * @param topLeftCorner Initial position of the block's top-left corner.
     * @param renderable    Renderable component for the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        setTag(BLOCK_TAG);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
