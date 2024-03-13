package pepse.world;

import danogl.GameObject;

/**
 * Interface for adding game objects to the world.
 */
public interface AddObjectInterface {
    /**
     * Adds a game object to the world with a specified ID.
     *
     * @param gameObject The game object to add.
     * @param id         The ID of the layer where the game object should be added.
     */
    void addObject(GameObject gameObject, int id);
}
