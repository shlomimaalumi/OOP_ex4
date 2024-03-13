package pepse.world;

import danogl.GameObject;

@FunctionalInterface
/**
 * Functional interface for removing game objects.
 */
public interface RemoveObjectInterface {
    /**
     * Removes the specified game object.
     *
     * @param gameObject The game object to be removed.
     */
    void removeObject(GameObject gameObject);
}
