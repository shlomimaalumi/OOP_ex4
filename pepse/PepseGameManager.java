package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;


import java.util.List;

public class PepseGameManager extends GameManager {

    private static final int SEED = 0;
    private static final int BASE_X = 0;
    private static final float CYCLE_LENGTH = 30;
    private static final float AVATAR_WIDTH = 50;
    private static final float AVATAR_HEIGHT = 50;
    private Terrain terrain;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()), Layer.BACKGROUND);
        createTerrain(windowController);
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH),
                Layer.FOREGROUND);
        Vector2 sunCenterPoint = new Vector2(windowController.getWindowDimensions().x()/2,
                terrain.groundHeightAt(windowController.getWindowDimensions().x()/4));
        gameObjects().addGameObject(Sun.create(sunCenterPoint, CYCLE_LENGTH),
                Layer.BACKGROUND);
        gameObjects().addGameObject(SunHalo.create(Sun.create(sunCenterPoint, CYCLE_LENGTH)), Layer.BACKGROUND);
        Avatar avatar= new Avatar(new Vector2(AVATAR_WIDTH,AVATAR_HEIGHT),inputListener,imageReader);
        gameObjects().addGameObject(avatar);
        Flora flora =new Flora(gameObjects()::addGameObject,gameObjects()::removeGameObject,
                Terrain.create(windowController.getWindowDimensions(),SEED));
        List<Tree> trees = flora.createInRange(0,(int) windowController.getWindowDimensions().x());
        for (Tree tree:trees){
            avatar.addJumpObserver(tree);
        }
        EnergyNumeric energyIndicator = new EnergyNumeric(new Vector2(0,0),new Vector2(100,100),avatar::getEnergy);
        gameObjects().addGameObject(energyIndicator,Layer.UI);
    }

    private void createTerrain(WindowController windowController) {
        this.terrain = Terrain.create(windowController.getWindowDimensions(), SEED);
        List<Block> blocks = terrain.createInRange(BASE_X, (int) windowController.getWindowDimensions().x());
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    @Override
    public void run() {
        super.run();
    }


    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
