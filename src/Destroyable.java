/**
 * Interface for entities that can be destroyed
 */

public interface Destroyable {

    // Mark the entity as destroyed
    void destroy();

    // Check if the entity is destroyed
    boolean isDestroyed();
}
