import bagel.Image;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Properties;

/**
 * KeyBulletKin class.
 * Moves along predefined route points loaded from app.properties.
 */

public class keyBulletKin extends Enemy {
    private final static String IMAGE_PATH = "res/key_bullet_kin.png";
    private final static String HEALTH_SUFFIX = "Health";
    private final static String KEY_BULLET_KIN_KEY = "keyBulletKin";
    private final ArrayList<Point> routePoints = new ArrayList<>();
    private int currentTargetIndex = 0;
    private Key key;
    private double speed;

    public keyBulletKin(Properties gameProps, String key) {
        super(gameProps);

        // Fill in attributes
        this.setEnemyImagePath(IMAGE_PATH);
        this.setImage(new Image(this.getEnemyImagePath()));
        this.setMaxHealth(Double.parseDouble(gameProps.getProperty(KEY_BULLET_KIN_KEY + HEALTH_SUFFIX)));
        this.setHealth(this.getMaxHealth());
        this.setDefeated(false);

        // Load speed from app.properties
        this.speed = Double.parseDouble(gameProps.getProperty("keyBulletKinSpeed"));

        // Load route points from properties
        if (gameProps.getProperty(key) != null) {
            String[] coords = gameProps.getProperty(key).split(";");
            for (String coord : coords) {
                routePoints.add(IOUtils.parseCoords(coord));
            }

            // Start at the first route point
            if (!routePoints.isEmpty()) {
                this.setPosition(routePoints.get(0));
            }

            // Define bounding box for collisions
            this.box(this.getImage());
        }
    }

    public Key getKey() {
        return key;
    }

    // Update movement and handle collisions.
    @Override
    public void update(Player player, Door primaryDoor) {
        // still damages player on contact
        super.update(player, primaryDoor);

        // move along the route
        if(!this.isDefeated()){
            if(!primaryDoor.isOpen() && !primaryDoor.isFirstEntry()) {
                moveAlongRoute();
            }
        } else {
            // spawn key at enemy's death if not already spawned
            if (key == null) {
                key = new Key(this.getPosition());
            }
            key.update(player); // handle collection
        }
    }

    // Moves the enemy along the predefined route points.
    private void moveAlongRoute() {
        if (routePoints.isEmpty()) return;

        Point current = this.getPosition();
        Point target = routePoints.get(currentTargetIndex);

        double dx = target.x - current.x;
        double dy = target.y - current.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If reached target, go to next point
        if (distance < speed) {
            currentTargetIndex = (currentTargetIndex + 1) % routePoints.size();
            return;
        }

        // Normalize direction vector and move
        double stepX = (dx / distance) * speed;
        double stepY = (dy / distance) * speed;

        this.setPosition(new Point(current.x + stepX, current.y + stepY));
        this.box(this.getImage()); // update hitbox
    }

    // Render the enemy only if not defeated.
    @Override
    public void render() {
        if (this.getPosition() != null && !this.isDefeated()) {
            this.getImage().draw(this.getPosition().x, this.getPosition().y);
            }
    }
}
