import bagel.*;
import java.util.Properties;

/**
 * This is written for SWEN20003 Semester 2 2025 Project 1
 *
 * Main game entity class for ShadowDungeon.
 * Handles properties, room initialization, room transitions,
 * and restart mechanism for the game
 *
 * @author Michael Dharmawan | 1674171
 */

public class ShadowDungeon extends AbstractGame {

    // Property Keys
    private static final String WINDOW_WIDTH_KEY = "window.width";
    private static final String WINDOW_HEIGHT_KEY = "window.height";
    private static final String TITLE_KEY = "title";

    // Room Identifiers
    private static final String ROOM_PREP = "prep";
    private static final String ROOM_A = "A";
    private static final String ROOM_B = "B";
    private static final String ROOM_WIN = "end";
    private static final String ROOM_LOSE = "lose";

    // End Statuses
    private static final String STATUS_WON = "won";
    private static final String STATUS_LOST = "lost";

    // Properties
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;

    // Property Paths
    private static final String APP_PROPERTIES_PATH = "res/app.properties";
    private static final String MESSAGE_PROPERTIES_PATH = "res/message.properties";

    // Rooms
    private PrepRoom prepRoom;
    private BattleRoom battleRoomA;
    private BattleRoom battleRoomB;
    private EndRoom winRoom;
    private EndRoom loseRoom;

    // Store
    private Store store;

    // Characters
    private Original original;
    private Robot robot;
    private Marine marine;

    // Current Character Tracker, starting from original character
    private Character currentCharacter;

    // Current Room Tracker, starting from prep room
    private String currentRoomString = ROOM_PREP;

    // Game paused tracker
    private boolean isPaused = false;

    // Constructor
    public ShadowDungeon(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty(WINDOW_WIDTH_KEY)),
                Integer.parseInt(gameProps.getProperty(WINDOW_HEIGHT_KEY)),
                messageProps.getProperty(TITLE_KEY));

        // Fill in properties
        GAME_PROPS = gameProps;
        MESSAGE_PROPS = messageProps;

        // Initialize Rooms
        prepRoom = new PrepRoom(GAME_PROPS, MESSAGE_PROPS, this);
        battleRoomA = new BattleRoom(GAME_PROPS, this, ROOM_A);
        battleRoomB = new BattleRoom(GAME_PROPS, this, ROOM_B);
        winRoom = new EndRoom(GAME_PROPS, MESSAGE_PROPS, this, STATUS_WON);
        loseRoom = new EndRoom(GAME_PROPS, MESSAGE_PROPS, this, STATUS_LOST);

        // Initialize Store
        store = new Store(GAME_PROPS);

        // Initialize Characters
        original = new Original();
        robot = new Robot();
        marine = new Marine();
        currentCharacter = original;
    }

    // Set current room string
    public void setCurrentRoomString(String currentRoomString) {
        this.currentRoomString = currentRoomString;
    }

    // Reverse pause
    public void reversePause() {
        this.isPaused = !isPaused;
    }

    // get gameProperties
    public Properties getGAME_PROPS() {
        return GAME_PROPS;
    }

    // Get current room string
    public String getCurrentRoomString() {
        return currentRoomString;
    }

    // Get current character
    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    // Set current character
    public void setCurrentCharacter(Character currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    // Get marine
    public Marine getMarine() {
        return marine;
    }

    // Get robot
    public Robot  getRobot() {
        return robot;
    }

    // The main processor of the game which controls what happens every frame
    @Override
    public void update(Input input) {

        // Toggle pause
        if (input.wasPressed(Keys.SPACE)) {
            isPaused = !isPaused;
        }


        // If paused, render the last frame and activate the store
        if (isPaused) {
            if (currentRoomString.equals(ROOM_PREP)) {
                prepRoom.render();
            } else if (currentRoomString.equals(ROOM_A)) {
                battleRoomA.render();
            } else if (currentRoomString.equals(ROOM_B)) {
                battleRoomB.render();
            } else if (currentRoomString.equals(ROOM_WIN)) {
                winRoom.render();
            } else if (currentRoomString.equals(ROOM_LOSE)) {
                loseRoom.render();
            }

            store.run(input, this, GAME_PROPS, MESSAGE_PROPS);
            return;  // Skip the rest so entities don’t move
        }

        // If not paused, manage room transitions by running rooms based on the current room tracker
        if (currentRoomString.equals(ROOM_PREP)) {
            prepRoom.run(input);
        } else if (currentRoomString.equals(ROOM_A)) {
            battleRoomA.run(input);
        } else if (currentRoomString.equals(ROOM_B)) {
            battleRoomB.run(input);
        } else if (currentRoomString.equals(ROOM_WIN)) {
            winRoom.run(input);
        } else if (currentRoomString.equals(ROOM_LOSE)) {
            loseRoom.run(input);
        }

        // If Player died, immediately run the lose room
        if (Player.getHealth() <= 0){
            currentRoomString = ROOM_LOSE;
        }

        // Close the window when escape key is pressed
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
    }

    // Game Reset Mechanism
    public void gameReset(Properties gameProps, Properties messageProps){
        // Reset current room tracker back to prep room
        currentRoomString = ROOM_PREP;

        // Reset current chracter tracker back to original
        currentCharacter = original;

        // Reset player health and coins
        Player.resetHealth();
        Player.resetCoins();
        Player.resetKeys();
        Player.resetWeapon();

        // Re-initialize rooms to reset room entities
        prepRoom = new PrepRoom(gameProps, messageProps, this);
        battleRoomA = new BattleRoom(gameProps, this, ROOM_A);
        battleRoomB = new BattleRoom(gameProps, this, ROOM_B);
        winRoom = new EndRoom(gameProps, messageProps, this, STATUS_WON);
        loseRoom = new EndRoom(gameProps, messageProps, this, STATUS_LOST);
    }

    // Program entry point
    public static void main(String[] args) {
        Properties gameProps = IOUtils.readPropertiesFile(APP_PROPERTIES_PATH);
        Properties messageProps = IOUtils.readPropertiesFile(MESSAGE_PROPERTIES_PATH);
        ShadowDungeon game = new ShadowDungeon(gameProps, messageProps);
        game.run();
    }
}
