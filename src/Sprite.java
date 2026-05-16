import bagel.Image;
import java.util.Properties;

/**
 * Sprite class
 * Handles construction of sprite objects
 */

public class Sprite extends RoomEntity{
    public Sprite(String imagePath, Properties gameProps, String key){
        this.setImage(new Image(imagePath));
        this.setPosition(IOUtils.parseCoords(gameProps.getProperty(key)));
    }
}
