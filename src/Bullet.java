import bagel.util.Point;
import java.util.Properties;

/**
 * Bullet class.
 * Handles construction.
 */

public class Bullet extends Projectile {

    public Bullet(Properties gameProps, Point startPos, Point targetPos) {
        // "bullet" is the key in gameProps for speed and image
        super(gameProps, startPos, targetPos, "bullet");
    }


}
