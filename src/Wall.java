import bagel.Image;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Wall class.
 * Handles quick construction and rendering of wall objects.
 */

public class Wall extends RoomEntity {

    // Constants
    private static final String WALL_IMAGE_PATH = "res/wall.png";
    private static final String NO_WALLS = "0";
    private static final String COORDINATE_SEPARATOR = ";";

    // Constructor
    public Wall(Point point) {

        // Fill in room entity attributes
        this.setImage(new Image(WALL_IMAGE_PATH));
        this.setPosition(point);
        this.box(this.getImage());
    }

    // Wrapper to construct multiple walls
    public static ArrayList<Wall> walls(Properties gameProps, String key){

        // if such key exist
        if(gameProps.getProperty(key) != null){

            // Prepare array output
            ArrayList<Wall> walls = new ArrayList<Wall>();

            // If there are no walls, terminate
            if(gameProps.getProperty(key).equals(NO_WALLS)){
                return null;
            }

            // Get the wall coordinates
            String[] wallCoordinates = gameProps.getProperty(key).split(COORDINATE_SEPARATOR);

            // For each coordinate
            for (String coordinate : wallCoordinates) {

                // Create the corresponding wall object and add to the array
                Point coordinatePoint = IOUtils.parseCoords(coordinate);
                Wall wall = new Wall(coordinatePoint);
                walls.add(wall);
            }

            // Return the array
            return walls;
        } else{
            return null;
        }
    }
}
