package pepse.world.trees;

import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.awt.Color;

public class Leaf extends Block{
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final RectangleRenderable LEAF_CIRCLE = new RectangleRenderable(LEAF_COLOR);
    private static final String LEAF_TAG = "leaf";

    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner,LEAF_CIRCLE);
        setTag(LEAF_TAG);

    }



}
