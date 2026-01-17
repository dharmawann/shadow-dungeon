import bagel.Image;
import bagel.Input;
import bagel.Keys;
import java.util.Properties;

/**
 * Restart Area class.
 * Handles quick construction and player interaction of Restart Area class objects.
 */

public class RestartArea extends RoomEntity {

    // Constants
    private static final String RESTART_IMAGE_PATH = "res/restart_area.png";
    private static final Keys INTERACT_KEY = Keys.ENTER;

    // Requirements to restart the game
    private final ShadowDungeon game;
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;

    // Constructor
    public RestartArea(Properties gameProps, Properties messageProps, String key, ShadowDungeon game) {

        // Fill in room entity properties
        this.setImage(new Image(RESTART_IMAGE_PATH));
        this.setPosition(IOUtils.parseCoords(gameProps.getProperty(key)));
        this.box(this.getImage());

        // Fill in the restart requirements
        this.game = game;
        GAME_PROPS = gameProps;
        MESSAGE_PROPS = messageProps;
    }

    // Player interaction with Restart Area objects
    public void update(Player player, Input input){

        // Reset the game if INTERACT_KEY is pressed and the player is colliding with the object
        if(input.wasPressed(INTERACT_KEY) && player.isColliding(this)){
            game.gameReset(GAME_PROPS, MESSAGE_PROPS);
        }
    }
}
