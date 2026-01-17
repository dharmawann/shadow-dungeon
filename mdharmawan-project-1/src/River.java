import bagel.Image;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Properties;

/**
 * River class.
 * Handles quick construction, rendering, and interaction of river objects.
 */

public class River extends RoomEntity {

    // Constants
    private static final String RIVER_IMAGE_PATH = "res/river.png";
    private static final String RIVER_DAMAGE_KEY = "riverDamagePerFrame";
    private static final String NO_RIVERS = "0";
    private static final String COORDINATE_SEPARATOR = ";";

    // River damage
    private static double RIVER_DAMAGE;

    // Constructor
    public River(Properties gameProp, Point point) {

        // Fill in room entity attributes
        this.setImage(new Image(RIVER_IMAGE_PATH));
        this.setPosition(point);
        this.box(this.getImage());

        // Fill in river damage
        River.setRiverDamage(Double.parseDouble(gameProp.getProperty(RIVER_DAMAGE_KEY)));
    }

    // Get River Damage
    public static double getRiverDamage() {
        return RIVER_DAMAGE;
    }

    // Set River Damage
    public static void setRiverDamage(double riverDamage) {
        RIVER_DAMAGE = riverDamage;
    }

    // Wrapper to construct multiple river objects
    public static ArrayList<River> rivers(Properties gameProps, String key){

        if(gameProps.getProperty(key) != null) {
            // Prepare the output array
            ArrayList<River> rivers = new ArrayList<River>();

            // If there are no rivers, terminate
            if(gameProps.getProperty(key).equals(NO_RIVERS)){
                return null;
            }

            // Get the river coordinates
            String[] riverCoordinates = gameProps.getProperty(key).split(COORDINATE_SEPARATOR);

            // For each coordinate, create the river object and put it into the array
            for (String coordinate : riverCoordinates) {
                Point coordinatePoint = IOUtils.parseCoords(coordinate);
                River river = new River(gameProps, coordinatePoint);
                rivers.add(river);
            }
            // Return the array
            return rivers;
        } else{
            return null;
        }
    }

    // Player interaction with river objects
    public void update(Player player, ShadowDungeon game){

        if(!game.getCurrentCharacter().getName().equals("Marine")){
            // If the player is touching the river and is not dead yet
            if(player.isColliding(this) && Player.getHealth() > 0) {

                // Harm the player
                Player.decreaseHealth(River.getRiverDamage());
            }
        }
    }
}
