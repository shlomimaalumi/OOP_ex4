package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.world.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flora {
    private static final int MIN_TRUNK_HEIGHT = 200;
    private static final int MAX_TRUNK_HEIGHT = 300;
    private final static int MIN_TRUNK_WIDTH = 25;
    private final static int MAX_TRUNK_WIDTH = 55;
    private static final int MIN_LEAVES_AMOUNT = 20;
    private static final int MAX_LEAVES_AMOUNT = 30;
    private static final int MIN_FRUIT_AMOUNT = 2;
    private static final int MAX_FRUIT_AMOUNT = 6;
    private static final int SPACE_BETWEEN_TRUNKS_RATIO = 3;
    private static final int CREATE_TREE = 0;
    private static final int CHANCE_FOR_CREATE_TREE = 10;
    private final AddObjectInterface addObject;
    private final RemoveObjectInterface removeObject;
    private final Terrain terrain;
    private final Random random;


    public Flora(AddObjectInterface addObject, RemoveObjectInterface removeObject, Terrain terrain){
        this.addObject = addObject;
        this.removeObject = removeObject;

        this.terrain=terrain;
        this.random = new Random();
    }
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        for (int i = minX; i<= maxX;i+= Block.SIZE ){
            if (random.nextInt(CHANCE_FOR_CREATE_TREE)==CREATE_TREE){
                float groundHeight = terrain.groundHeightAt((float) i);
                int trunkHeight = random.nextInt(MIN_TRUNK_HEIGHT,MAX_TRUNK_HEIGHT);
                int trunkWidth=random.nextInt(MIN_TRUNK_WIDTH,MAX_TRUNK_WIDTH);
                int leavesAmount = random.nextInt(MIN_LEAVES_AMOUNT,MAX_LEAVES_AMOUNT);
                int fruitAmount = random.nextInt(MIN_FRUIT_AMOUNT,MAX_FRUIT_AMOUNT);
                Vector2 topLeftCorner = new Vector2(i,groundHeight-trunkHeight);
                Tree tree = new Tree(topLeftCorner, trunkWidth,(float) trunkHeight,addObject,removeObject,leavesAmount,
                        fruitAmount,random);
                i+= SPACE_BETWEEN_TRUNKS_RATIO * trunkWidth;
                trees.add(tree);
            }
        }
        return trees;
    }
}
