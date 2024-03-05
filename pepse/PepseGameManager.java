package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;

import java.util.List;

public class PepseGameManager extends GameManager {

    private static final int SEED = 0;
    private static final int BASE_X = 0;
    private static final float CYCLE_LENGTH = 8;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()), Layer.BACKGROUND);
        createTerrain(windowController);
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH),
                Layer.FOREGROUND);
        gameObjects().addGameObject(Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH));
        gameObjects().addGameObject(SunHalo.create(Sun.create(windowController.getWindowDimensions(),
                CYCLE_LENGTH)));




    }

    private void createTerrain(WindowController windowController) {
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), SEED);
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
