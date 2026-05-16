import bagel.Image;

/**
 * Marine class.
 * Handles consturction of marine characters
 */

public class Marine extends Character{

    // Constants
    private final String PLAYER_LEFT_IMAGE_PATH = "res/marine_left.png";
    private final String PLAYER_RIGHT_IMAGE_PATH = "res/marine_right.png";
    private final String PLAYER_IMAGE_PATH = "res/marine.png";

    // Constructors
    public Marine() {
        this.setName("Marine");
        this.setSkill("MARINE: NO INJURY IN RIVERS");
        this.setImageLeft(new Image(PLAYER_LEFT_IMAGE_PATH));
        this.setImageRight(new Image(PLAYER_RIGHT_IMAGE_PATH));
        this.setImage(new Image(PLAYER_IMAGE_PATH));
    }
}
