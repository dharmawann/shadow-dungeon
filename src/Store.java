import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import java.util.Properties;

/**
 * Store class for Shadow Dungeon
 * handles construction, rendering, and player upgrade and restart mechanism
 */

public class Store {

    // Constants for property keys
    private static final String STORE_KEY = "store";
    private static final String HEALTH_PURCHASE_KEY = "healthPurchase";
    private static final String HEALTH_BONUS_KEY = "healthBonus";
    private static final String WEAPON_PURCHASE_KEY = "weaponPurchase";
    private static final String STORE_IMAGE_PATH = "res/store.png";

    // Store attributes
    private final Point position;
    private final Image image;
    private final int healthPurchaseCost;
    private final int healthBonus;
    private final int weaponPurchaseCost;

    // Constructor
    public Store(Properties gameProps) {

        // Load store coordinates
        String[] coords = gameProps.getProperty(STORE_KEY).split(",");
        this.position = new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));

        // Load store image
        this.image = new Image(STORE_IMAGE_PATH);

        // Load purchase properties
        this.healthPurchaseCost = Integer.parseInt(gameProps.getProperty(HEALTH_PURCHASE_KEY));
        this.healthBonus = Integer.parseInt(gameProps.getProperty(HEALTH_BONUS_KEY));
        this.weaponPurchaseCost = Integer.parseInt(gameProps.getProperty(WEAPON_PURCHASE_KEY));
    }

    // run the store
    public void run(Input input, ShadowDungeon game, Properties gameProps, Properties messageProps) {
        handleInput(input, game, gameProps, messageProps);
        render();
    }

    // upgrade and restart mechanism
    private void handleInput(Input input, ShadowDungeon game, Properties gameProps, Properties messageProps) {

        // Buy health, increase health and decrease coins
        if (input.wasPressed(Keys.E)) {
            if (Player.getCoins() >= healthPurchaseCost) {
                Player.addHealth(healthBonus);
                Player.increaseCoins(-healthPurchaseCost);
            }
        }

        // Upgrade weapon, increase weaponlevel and decrease coins
        if (input.wasPressed(Keys.L)) {
            if (Player.getCoins() >= weaponPurchaseCost && Player.getWeapon_level() < 2) {
                Player.upgradeWeapon();
                Player.increaseCoins(-weaponPurchaseCost);
            }
        }

        // Restart
        if (input.wasPressed(Keys.P)) {
            game.reversePause();
            game.gameReset(gameProps, messageProps);
        }
    }

    // render the store
    private void render() {
        image.draw(position.x, position.y);
    }
}
