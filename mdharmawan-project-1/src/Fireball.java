import bagel.util.Point;
import java.util.Properties;

/**
 * Fireball class.
 * Handles construction of fireball objects
 */

public class Fireball extends Projectile {

    // Constant
    private final static String DAMAGE_KEY = "fireballDamage";

    // Attributes
    private int damage;

    // Constructor
    public Fireball(Properties gameProps, Point startPos, Point targetPos) {

        // "fireball" is the key in gameProps for speed and image
        super(gameProps, startPos, targetPos, "fireball");
        damage = Integer.parseInt(gameProps.getProperty(DAMAGE_KEY));
    }

    // getters and setters
    public int getDamage() {
        return damage;
    }
}
