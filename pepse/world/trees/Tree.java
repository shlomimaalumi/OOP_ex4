package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.JumpObserver;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree extends GameObject implements JumpObserver {


    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
//    private static final RectangleRenderable trunkRectangleRenderable = new RectangleRenderable(TRUNK_COLOR);
    private final static float  TRUNK_WIDTH=10;
    private static final RectangleRenderable TRUNK_RENDERABLE = new RectangleRenderable(TRUNK_COLOR);
    private static final float MAX_RIGHT_DIS_FROM_TREE = 100;
    private static final float MAX_LEFT_DIS_FROM_TREE = 100;
    private static final float MAX_UP_DIS_FROM_TREE = 100;
    private static final float MAX_DOWN_DIS_FROM_TREE = 100;
    private static final Color INITIAL_TRUNK_COLOR = new Color(100, 50, 20);
    private static final Random RANDOM = new Random();
    private static final int DIFF = 20;


    private Color trunkColor = INITIAL_TRUNK_COLOR;
//    private RectangleRenderable trunkRenderable;
    List<Leaf> leaves = new ArrayList<>();
    List<Fruit> fruits = new ArrayList<>();


    public Tree(Vector2 topLeftCorner,float trunkWidth ,float trunkHeight, GameObjectCollection gameObjects,
                int leavesAmount,int fruitsAmount,Random random) {
        super(topLeftCorner,Vector2.ZERO,null);
        addTrunk(topLeftCorner,trunkWidth,trunkHeight,gameObjects);
        addLeaves(topLeftCorner,gameObjects,leavesAmount,random);
        addFruits(topLeftCorner,gameObjects,fruitsAmount,random);
    }

    private void addFruits(Vector2 topLeftCorner, GameObjectCollection gameObjects, int fruitsAmount,
                           Random random) {
        for (int i = 0; i < fruitsAmount; i++) {
            float x = random.nextFloat(-MAX_LEFT_DIS_FROM_TREE, MAX_RIGHT_DIS_FROM_TREE);
            float y = random.nextFloat(-MAX_UP_DIS_FROM_TREE, MAX_DOWN_DIS_FROM_TREE);
            Vector2 fruitPosition = new Vector2(topLeftCorner).add(new Vector2(x, y));
            addFruit(fruitPosition, gameObjects, random);
        }
    }

    private void addFruit(Vector2 fruitPosition, GameObjectCollection gameObjects, Random random) {
        Fruit fruit = new Fruit(fruitPosition, gameObjects, random);
        fruits.add(fruit);
        gameObjects.addGameObject(fruit);
    }

    private void addLeaves(Vector2 topLeftCorner, GameObjectCollection gameObjects, int leavesAmount,
                           Random random) {
        for (int i = 0; i < leavesAmount; i++) {
            float x = random.nextFloat(-MAX_LEFT_DIS_FROM_TREE, MAX_RIGHT_DIS_FROM_TREE);
            float y = random.nextFloat(-MAX_UP_DIS_FROM_TREE, MAX_DOWN_DIS_FROM_TREE);
            Vector2 leafVector = new Vector2(topLeftCorner).add(new Vector2(x, y));
            addLeaf(leafVector, gameObjects);
        }
    }

    private void addLeaf(Vector2 topLeftCorner, GameObjectCollection gameObjects) {
        Leaf leaf = new Leaf(topLeftCorner);
        leaves.add(leaf);
        gameObjects.addGameObject(leaf, Layer.DEFAULT - 1);
    }

    private void addTrunk(Vector2 topLeftCorner, float trunkWidth , float trunkHeight, GameObjectCollection gameObjects){
        Vector2 trunkDimensions = new Vector2(trunkWidth,trunkHeight);
        GameObject trunk = new GameObject(topLeftCorner,trunkDimensions,TRUNK_RENDERABLE);
        trunk.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS-1);
        trunk.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        gameObjects.addGameObject(trunk);
    }

    @Override
    public void onJump() {
//        for (Leaf leaf:leaves){
//
//        }
        for (Fruit fruit: fruits){
            fruit.onJump();
        }

    }


    public Tree(Vector2 position) {
        super(position, null, null);

//        Block trunkBlock = new Block(position, trunkRectangleRenderable);
//        GameObject trunk = new GameObject();


    }

}
