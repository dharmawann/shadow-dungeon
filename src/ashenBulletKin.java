import bagel.Image;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Properties;


/**
 * ashenBulletKin class.
 * Handles construction, shooting and rendering of fireballs by ashenbulletkin
 */

public class ashenBulletKin extends Enemy{

    // Constants
    private final static String IMAGE_PATH = "res/ashen_bullet_kin.png";
    private final static String HEALTH_SUFFIX = "Health";
    private final static String ASHEN_BULLET_KIN_KEY = "ashenBulletKin";
    private final static String REWARD_SUFFIX = "Coin";
    private final static String COOLDOWN_KEY = "ashenBulletKinShootFrequency";

    // Single constructor for one ashenBulletKins
    public ashenBulletKin(Properties gameProps, Point position) {
        super(gameProps);
        this.setEnemyImagePath(IMAGE_PATH);
        this.setImage(new Image(this.getEnemyImagePath()));
        this.setPosition(position);
        this.box(this.getImage());
        this.setMaxHealth(Double.parseDouble(gameProps.getProperty(ASHEN_BULLET_KIN_KEY + HEALTH_SUFFIX)));
        this.setHealth(this.getMaxHealth());
        this.setReward(Integer.parseInt(gameProps.getProperty(ASHEN_BULLET_KIN_KEY + REWARD_SUFFIX)));
        this.setCoolDown(Integer.parseInt(gameProps.getProperty(COOLDOWN_KEY)));
        this.setInitialCoolDown(Integer.parseInt(gameProps.getProperty(COOLDOWN_KEY)));
        this.setDefeated(false);
    }

    // Static wrapper to create multiple ashenBulletKins
    public static ArrayList<ashenBulletKin> ashenBulletKins(Properties gameProps, String key) {
        if (gameProps.getProperty(key) == null) {
            return null;
        }

        String prop = gameProps.getProperty(key);
        if (prop.equals("0")) {
            return null;
        }

        ArrayList<ashenBulletKin> ashenBulletKins = new ArrayList<>();
        String[] coords = prop.split(";");

        for (String coord : coords) {
            Point point = IOUtils.parseCoords(coord);
            ashenBulletKin bk = new ashenBulletKin(gameProps, point);
            ashenBulletKins.add(bk);
        }

        return ashenBulletKins;
    }

    // Shooting mechanism
    public void shoot(Player player, Properties gameProps, ArrayList<Wall> walls) {

        // Only shoot (make new fireballs) if enemy not defeated
        if (!this.isDefeated()) {

            // Decrease cooldown each frame
            if (this.getCoolDown() > 0) {
                decreaseCoolDown();
            }

            // If cooldown is 0, shoot a fireball
            if (this.getCoolDown() <= 0) {
                Point startPos = this.getPosition();
                Point targetPos = player.getPosition();
                getFireballs().add(new Fireball(gameProps, startPos, targetPos));

                // Reset cooldown
                resetCoolDown(this.getInitialCoolDown());
            }
        }
    }

    // Handles the movement of the shooted fireballs
    public void updateFireballs(Player player, Properties gameProps, ArrayList<Wall> walls, ArrayList<Door> doors) {

        // Update all fireball and handle collisions with player
        for (Fireball fireball : getFireballs()) {

            // Move the fireball and handle if the fireball goes offscreen
            fireball.update(walls, doors);

            // Handle collision with player
            if (fireball.isColliding(player) && Player.getHealth() > 0) {
                Player.takeDamage(fireball);
                fireball.deactivate();
            }

        }

        // Remove inactive bullets
        getFireballs().removeIf(f -> !f.isActive());
    }
}
