import bagel.Image;
import bagel.util.Point;

import java.util.Properties;

/**
 * Table class
 * Acts as an obstacle that the player cannot pass through.
 * Can be destroyed when hit by a bullet.
 */
public class Table extends RoomEntity implements Destroyable {

    // Constants
    private static final String TABLE_IMAGE_PATH = "res/table.png";

    // Keep track wether the table is destroyed or not
    private boolean destroyed = false;

    // Constructor
    public Table(Properties gameProps, String key) {
        if(gameProps.getProperty(key) != null) {
            this.setPosition(IOUtils.parseCoords(gameProps.getProperty(key)));
            this.setImage(new Image(TABLE_IMAGE_PATH));
            this.box(getImage());
        }
    }

    // Implementation of Destroyable interface
    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    // Override render so it only draws if not destroyed
    @Override
    public void render() {
        if (!destroyed && this.getPosition() != null) {
            super.render();
        }
    }
}
