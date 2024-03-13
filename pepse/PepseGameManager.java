package pepse;

import danogl.GameManager;
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

/**
 * The PepseGameManager class extends the GameManager class and serves as the main entry point
 * for the game's management and initialization.
 */
public class PepseGameManager extends GameManager {

    /**
     * Seed used for generating terrain and flora.
     */
    private static final int SEED = 0;

    /**
     * Length of the day-night cycle.
     */
    private static final float CYCLE_LENGTH = 30;

    /**
     * Initial position of the avatar.
     */
    private static final float AVATAR_POS = 50;

    /**
     * Starting X position of the world.
     */
    private static final int BEGIN_WORLD_X = 0;

    /**
     * The Terrain instance representing the game's terrain.
     */
    private Terrain terrain;

    /**
     * Initializes the game, including creating the world, terrain, avatar, and energy indicator.
     *
     * @param imageReader      The ImageReader instance for loading images.
     * @param soundReader      The SoundReader instance for loading sounds.
     * @param inputListener    The UserInputListener instance for handling user input.
     * @param windowController The WindowController instance for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        createWorld(windowController);
        List<Tree> trees = createTrees(windowController);
        Avatar avatar = createAvatarAndObservers(imageReader, inputListener, trees);
        createEnergyIndicator(avatar);
    }

    /**
     * Creates the game world, including the sky, terrain, day-night cycle, sun, and sun halo.
     *
     * @param windowController The WindowController instance for managing the game window.
     */
    private void createWorld(WindowController windowController) {
        gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()), Layer.BACKGROUND);
        createTerrain(windowController);
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH),
                Layer.FOREGROUND);
        Vector2 sunCenterPoint = new Vector2(windowController.getWindowDimensions().x() / 2,
                terrain.groundHeightAt(windowController.getWindowDimensions().x() / 4));
        gameObjects().addGameObject(Sun.create(sunCenterPoint, CYCLE_LENGTH),
                Layer.BACKGROUND);
        gameObjects().addGameObject(SunHalo.create(Sun.create(sunCenterPoint, CYCLE_LENGTH)), Layer.BACKGROUND);
    }

    /**
     * Creates an energy indicator for the avatar.
     *
     * @param avatar The Avatar instance for which the energy indicator is created.
     */
    private void createEnergyIndicator(Avatar avatar) {
        EnergyNumeric energyIndicator = new EnergyNumeric(new Vector2(0, 0), new Vector2(100, 100),
                avatar::getEnergy);
        gameObjects().addGameObject(energyIndicator, Layer.UI);
    }

    /**
     * Creates the avatar and its associated jump observers.
     *
     * @param imageReader  The ImageReader instance for loading avatar images.
     * @param inputListener The UserInputListener instance for handling user input.
     * @param trees         The list of trees to observe avatar jumps.
     * @return The created Avatar instance.
     */
    private Avatar createAvatarAndObservers(ImageReader imageReader, UserInputListener inputListener, List<Tree> trees) {
        Avatar avatar = new Avatar(new Vector2(AVATAR_POS, AVATAR_POS), inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        for (Tree tree : trees) {
            avatar.addJumpObserver(tree);
        }
        return avatar;
    }

    /**
     * Creates trees in the game world.
     *
     * @param windowController The WindowController instance for managing the game window.
     * @return The list of created Tree instances.
     */
    private List<Tree> createTrees(WindowController windowController) {
        Flora flora = new Flora(gameObjects()::addGameObject, gameObjects()::removeGameObject,
                Terrain.create(windowController.getWindowDimensions(), SEED));
        return flora.createInRange(0, (int) windowController.getWindowDimensions().x() + 500);
    }

    /**
     * Creates the terrain in the game world.
     *
     * @param windowController The WindowController instance for managing the game window.
     */
    private void createTerrain(WindowController windowController) {
        this.terrain = Terrain.create(windowController.getWindowDimensions(), SEED);
        List<Block> blocks = terrain.createInRange(BEGIN_WORLD_X,
                (int) (windowController.getWindowDimensions().x()));
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    /**
     * Starts running the game loop.
     */
    @Override
    public void run() {
        super.run();
    }

    /**
     * The main entry point for launching the game.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
