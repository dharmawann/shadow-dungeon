import bagel.Image;

/**
 * Original class.
 * Handles consturction of original characters
 */

public class Original extends Character{

    // Constants
    private final String PLAYER_LEFT_IMAGE_PATH = "res/player_left.png";
    private final String PLAYER_RIGHT_IMAGE_PATH = "res/player_right.png";

    // Constructors
    public Original(){
        this.setName("Original");
        this.setImageLeft(new Image(PLAYER_LEFT_IMAGE_PATH));
        this.setImageRight(new Image(PLAYER_RIGHT_IMAGE_PATH));
    }
}
