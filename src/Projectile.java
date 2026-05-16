import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Abstract Projectile class.
 * serve as prototype for all projectile objects such as fireballs and bullets
 * Handles construction, update, and rendering of projectile objects
 */

public abstract class Projectile extends RoomEntity {

    // constant
    private static String SPEED_SUFFIX = "Speed";
    private static String IMAGE_SUFFIX = ".png";

    // attributes
    private double dx;
    private double dy;
    private boolean active = true;
    private final Properties gameProps;

    // constructor
    public Projectile(Properties gameProps, Point startPos, Point targetPos, String key) {
        this.gameProps = gameProps;

        this.setPosition(startPos);
        this.setImage(new Image("res/" + key + IMAGE_SUFFIX));
        this.box(this.getImage());

        // Calculate normalized direction
        double dirX = targetPos.x - startPos.x;
        double dirY = targetPos.y - startPos.y;
        double length = Math.sqrt(dirX * dirX + dirY * dirY);

        // Get speed from properties
        double speed = Double.parseDouble(gameProps.getProperty(key + SPEED_SUFFIX));
        this.dx = (dirX / length) * speed;
        this.dy = (dirY / length) * speed;
    }

    // handles general movement of projectiles
    public void update(ArrayList<Wall> walls, ArrayList<Door> doors) {

        // stop updating if its already deactivated
        if (!active) return;

        // get current position and box it
        Point current = this.getPosition();
        this.box(this.getImage());

        // if current position goes of screen deaactivate it
        if (current.x < 0 || current.x > Window.getWidth() || current.y < 0 || current.y > Window.getHeight()) {
            deactivate();
        }

        // if collide with anywalls deactivate it
        if(walls!=null) {
            for (Wall wall : walls) {
                if(wall.isColliding(this)){
                    deactivate();
                }
            }
        }

        // if collide with any closed doors deactivate it
        for (Door door : doors) {
            if(door.isColliding(this) && !door.isOpen()){
                deactivate();
            }
        }

        // set next position
        this.setPosition(new Point(current.x + dx, current.y + dy));
    }

    @Override
    public void render() {
        if (isActive() && this.getPosition() != null) {
            super.render();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
