package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.JumpObserver;
import pepse.world.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Tree extends GameObject implements JumpObserver {


    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    private static final RectangleRenderable trunkRectangleRenderable = new RectangleRenderable(TRUNK_COLOR);
    List<Leaf> leafs = new ArrayList<>();


    @Override
    public void onJump() {

    }



    public Tree(Vector2 position){
        super(position,null,null);

        Block trunkBlock = new Block(position, trunkRectangleRenderable);
        GameObject trunk = new GameObject();


    }

}
