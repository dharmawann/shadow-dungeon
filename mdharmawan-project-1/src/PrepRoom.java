import java.util.Properties;

/**
 * Starting room of the game.
 * Handles initiating Prep Room entities.
 */

public class PrepRoom extends Room {

    // Constants
    private static final String ROOM_KEY = "prep";
    private static final String TITLE_KEY = "title";
    private static final String PROMPT_KEY = "prompt";
    private static final String STATUS_PREP = "prep";
    private static final String RESTART_KEY = "restartarea.prep";
    private static final String MARINE_KEY = "Marine";
    private static final String ROBOT_KEY = "Robot";
    private static final String MARINE_MESSAGE_KEY = "marineMessage";
    private static final String ROBOT_MESSAGE_KEY = "robotMessage";
    private static final String SELECT_MESSAGE_KEY = "selectMessage";
    private static final String MARINE_SPRITE_PATH = "res/marine_sprite.png";
    private static final String ROBOT_SPRITE_PATH = "res/robot_sprite.png";

    // Constructor
    public PrepRoom(Properties gameProps, Properties messageProps, ShadowDungeon game) {

        // Call the Room constructor
        super(gameProps, game, ROOM_KEY);

        // Initiate title and prompt texts
        this.setTitle(new Text(gameProps, messageProps, TITLE_KEY, STATUS_PREP));
        this.setPrompt(new Text(gameProps, messageProps, PROMPT_KEY, STATUS_PREP));
        this.setMarineMessage(new Text(gameProps, messageProps, MARINE_MESSAGE_KEY, STATUS_PREP));
        this.setRobotMessage(new Text(gameProps, messageProps, ROBOT_MESSAGE_KEY, STATUS_PREP));
        this.setSelectMessage(new Text(gameProps, messageProps, SELECT_MESSAGE_KEY, STATUS_PREP));

        // Initiate restart area
        this.setRestartArea(new RestartArea(gameProps, messageProps, RESTART_KEY, game));

        // Initiate Sprites
        this.setMarineSprite(new Sprite(MARINE_SPRITE_PATH, gameProps, MARINE_KEY));
        this.setRobotSprite(new Sprite(ROBOT_SPRITE_PATH, gameProps, ROBOT_KEY));

        // Initiate player
        this.setPlayer(new Player(gameProps, game));
    }


}
