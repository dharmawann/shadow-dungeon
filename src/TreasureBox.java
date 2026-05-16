import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Treasure Box class.
 * Handles quick construction and rendering of treasure box objects.
 */

public class TreasureBox extends RoomEntity {

    // Constants
    private static final String BOX_IMAGE_PATH = "res/treasure_box.png";
    private static final String INFO_SEPARATOR = ",";
    private static final String BOXES_SEPARATOR = ";";
    private static final Keys INTERACT_KEY = Keys.K;

    // Box status and reward
    private final int REWARD;
    private boolean boxOpen = false;

    // Constructor
    public TreasureBox(String info) {

        // Fill in room entity attributes
        this.setImage(new Image(BOX_IMAGE_PATH));
        String[] infoArray = info.split(INFO_SEPARATOR);
        double x = Double.parseDouble(infoArray[0]);
        double y = Double.parseDouble(infoArray[1]);
        this.setPosition(new Point(x, y));
        this.box(this.getImage());

        // Fill in box reward
        REWARD = Integer.parseInt(infoArray[2]);
    }

    // Wrapper to construct multiple boxes
    public static ArrayList<TreasureBox> treasureBoxes(Properties gameProps, String key) {

        // if such key exist
        if(gameProps.getProperty(key) != null){

            // Prepare the output array
            ArrayList<TreasureBox> treasureBoxes = new ArrayList<>();

            // Get the infos of boxes
            String[] boxInfos = gameProps.getProperty(key).split(BOXES_SEPARATOR);

            // For each info
            for (String info : boxInfos) {

                // Create corresponding box object and add it to the array
                TreasureBox box = new TreasureBox(info);
                treasureBoxes.add(box);
            }

            // Return the array
            return treasureBoxes;
        } else{
            return null;
        }
    }

    // Only render the box if it is not opened
    @Override
    public void render() {
        if(!boxOpen){
            this.getImage().draw(this.getPosition().x, this.getPosition().y);
        }
    }

    // Player interaction with boxes
    public void update(Player player, Input input){

        // If INTERACT_KEY is pressed, box is not opened, and player is colliding with the box
        if(player.isColliding(this) && input.wasPressed(INTERACT_KEY) && !boxOpen && Player.getKeys() > 0){

            // Open the box and update player's coins
            boxOpen = true;
            Player.increaseCoins(REWARD);
            Player.useKeys();
        }
    }
}
