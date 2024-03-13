package pepse.world;

import danogl.GameObject;

/**
 * Functional interface for removing game objects.
 */
@FunctionalInterface
public interface RemoveObjectInterface {
    /**
     * Removes the specified game object.
     *
     * @param gameObject The game object to be removed.
     */
    void removeObject(GameObject gameObject);
}
