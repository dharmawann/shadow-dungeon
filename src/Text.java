import bagel.Font;
import bagel.Window;
import bagel.util.Point;
import java.util.Properties;

/**
 * Text class.
 * Handles quick construction and rednering of Text class objects.
 */

public class Text {

    // Keys from gameProps and messageProps
    private static final String FONT_PATH_KEY = "font";
    private static final String TITLE_KEY = "title";
    private static final String PROMPT_KEY = "prompt";
    private static final String MOVE_MESSAGE_KEY = "moveMessage";
    private static final String GAME_END_WON_KEY = "gameEnd.won";
    private static final String GAME_END_LOST_KEY = "gameEnd.lost";
    private static final String MARINE_KEY = "marineMessage";
    private static final String ROBOT_KEY = "robotMessage";
    private static final String MARINE_DESCRIPTION_KEY = "marineDescription";
    private static final String ROBOT_DESCRIPTION_KEY = "robotDescription";
    private static final String PLAYER_STATS_FONT_KEY = "playerStats.fontSize";
    private static final String SELECT_KEY = "selectMessage";

    // Status values
    private static final String STATUS_PREP = "prep";
    private static final String STATUS_WON = "won";

    // Suffix constants
    private static final String FONT_SIZE_SUFFIX = ".fontSize";
    private static final String Y_SUFFIX = ".y";

    // Text attributes
    private final Font FONT;
    private final Point POSITION;
    private final String MESSAGE;

    // Constructor
    public Text(Properties gameProps, Properties messageProps, String key, String status){

        // Construct the object based on the provided key by filling in required attributes
        double x, y;
        String fontPath = gameProps.getProperty(FONT_PATH_KEY);

        if(key.equals(TITLE_KEY)){
            int titleSize = Integer.parseInt(gameProps.getProperty(TITLE_KEY + FONT_SIZE_SUFFIX));
            FONT = new Font(fontPath, titleSize);
            if(status.equals(STATUS_PREP)){
                MESSAGE = messageProps.getProperty(TITLE_KEY);
            } else if (status.equals(STATUS_WON)){
                MESSAGE = messageProps.getProperty(GAME_END_WON_KEY);
            } else {
                MESSAGE = messageProps.getProperty(GAME_END_LOST_KEY);
            }
            y = Double.parseDouble(gameProps.getProperty(TITLE_KEY + Y_SUFFIX));
            x = (Window.getWidth() / 2.0) - (FONT.getWidth(MESSAGE) / 2.0);
            POSITION = new Point(x, y);

        } else if(key.equals(PROMPT_KEY)){
            int promptSize = Integer.parseInt(gameProps.getProperty(PROMPT_KEY + FONT_SIZE_SUFFIX));
            FONT = new Font(fontPath, promptSize);
            MESSAGE = messageProps.getProperty(MOVE_MESSAGE_KEY);
            y = Double.parseDouble(gameProps.getProperty(MOVE_MESSAGE_KEY + Y_SUFFIX));
            x = (Window.getWidth() / 2.0) - (FONT.getWidth(MESSAGE) / 2.0);
            POSITION = new Point(x, y);

        } else if(key.equals(MARINE_KEY)){
            int promptSize = Integer.parseInt(gameProps.getProperty(PLAYER_STATS_FONT_KEY));
            FONT = new Font(fontPath, promptSize);
            MESSAGE = messageProps.getProperty(MARINE_DESCRIPTION_KEY);
            POSITION = IOUtils.parseCoords(gameProps.getProperty(MARINE_KEY));

        } else if(key.equals(ROBOT_KEY)){
            int promptSize = Integer.parseInt(gameProps.getProperty(PLAYER_STATS_FONT_KEY));
            FONT = new Font(fontPath, promptSize);
            MESSAGE = messageProps.getProperty(ROBOT_DESCRIPTION_KEY);
            POSITION = IOUtils.parseCoords(gameProps.getProperty(ROBOT_KEY));

        } else if(key.equals(SELECT_KEY)){
            int promptSize = Integer.parseInt(gameProps.getProperty(PROMPT_KEY + FONT_SIZE_SUFFIX));
            FONT = new Font(fontPath, promptSize);
            MESSAGE = messageProps.getProperty(SELECT_KEY);
            y = Double.parseDouble(gameProps.getProperty(SELECT_KEY + Y_SUFFIX));
            x = (Window.getWidth() / 2.0) - (FONT.getWidth(MESSAGE) / 2.0);
            POSITION = new Point(x, y);
        }

        else{
            int keySize = Integer.parseInt(gameProps.getProperty(key + FONT_SIZE_SUFFIX));
            FONT = new Font(fontPath, keySize);
            MESSAGE = messageProps.getProperty(key);
            y = Double.parseDouble(gameProps.getProperty(key + Y_SUFFIX));
            x = (Window.getWidth() / 2.0) - (FONT.getWidth(MESSAGE) / 2.0);
            POSITION = new Point(x, y);
        }
    }

    // Render Text objects
    public void render(){
        FONT.drawString(MESSAGE, POSITION.x, POSITION.y);
    }
}
