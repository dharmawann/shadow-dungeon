import bagel.Image;
import bagel.util.Point;

/**
 * Key class.
 * Handles construction, rendering, and update of key objects
 */

public class Key extends RoomEntity{
    private final static String IMAGE_PATH = "res/key.png";
    private boolean collected;

    public Key(Point spawnPoint) {
        this.setImage(new Image(IMAGE_PATH));
        this.setPosition(spawnPoint);
        this.collected = false;
        this.box(this.getImage());
    }

    public void render() {
        if (!collected && this.getPosition() != null) {
            super.render();
        }
    }

    public void update(Player player) {
        if (!this.isCollected() && this.getPosition() != null && player.isColliding(this)) {
            if(!this.isCollected()) {
                Player.collectKeys();
            }
            collected = true;
        }
    }

    public boolean isCollected() {
        return collected;
    }
}
