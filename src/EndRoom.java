import java.util.ArrayList;
import java.util.Properties;

/**
 * End room of the game.
 * Handles initiating End Room entities.
 */

public class EndRoom extends Room {

    // Constants
    private static final String ROOM_KEY = "end";
    private static final String RESTART_AREA_KEY = "restartarea.end";
    private static final String STATUS_LOST = "lost";
    private static final String TITLE_KEY = "title";

    // Constructor
    public EndRoom(Properties gameProps, Properties messageProps, ShadowDungeon game, String status) {

        // Call the Room constructor
        super(gameProps, game, ROOM_KEY);

        // Initiate win/lose texts
        this.setWinLoseMessage(new Text(gameProps, messageProps, TITLE_KEY, status));

        // Initiate restart area
        this.setRestartArea(new RestartArea(gameProps, messageProps, RESTART_AREA_KEY, game));

        // Close the door and initiate player based on game status
        if (status.equals(STATUS_LOST)) {
            this.getEndPrepDoor().closeDoorPermanently();
            this.setPlayer(new Player(gameProps, game));
        } else {
            this.setPlayer(new Player(gameProps, this.getEndPrepDoor().getPosition(), game));
        }
    }
}
