import java.util.Properties;

/**
 * Battle room of the game.
 * Handles initiating Battle Room entities.
 */

public class BattleRoom  extends Room{

    // Constructor
    public BattleRoom(Properties gameProps, ShadowDungeon game, String key) {

        // Call the Room constructor
        super(gameProps, game, key);

        // Initiate player
        this.setPlayer(new Player(gameProps, this.getPrimaryDoor().getPosition(), game));

    }
}
