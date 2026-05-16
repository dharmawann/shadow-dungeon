import bagel.Image;
import java.util.Properties;

/**
 * Basket class.
 * Handles construction, rendering, and destruction mechanism of a basket object
 */

public class Basket extends RoomEntity implements Destroyable {

    // Constants
    private static final String BASKET_IMAGE_PATH = "res/basket.png";

    // Keep track whether the basket is destroyed or not
    private boolean destroyed = false;

    // Constructor
    public Basket(Properties gameProps, String key) {
        if (gameProps.getProperty(key) != null) {
            this.setPosition(IOUtils.parseCoords(gameProps.getProperty(key)));
            this.setImage(new Image(BASKET_IMAGE_PATH));
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

    // Only render if not destroyed and the basket is defined
    @Override
    public void render() {
        if (!destroyed && this.getPosition() != null) {
            super.render();
        }
    }
}
