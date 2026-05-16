/**
 * Interface for entities that can take damage and be defeated.
 */

public interface Defeatable {

    // Take damage
    void takeDamage();

    // Check if the entity is defeated
    boolean isDefeated();

    // Mark the entity as defeated
    void defeat();
}
