import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Door class.
 * Handles quick construction, rendering, and interaction of door objects.
 */

public class Door extends RoomEntity {

    // Constants
    private static final String LOCKED_DOOR_PATH = "res/locked_door.png";
    private static final String UNLOCKED_DOOR_PATH = "res/unlocked_door.png";
    private static final String NO_DOORS = "0";
    private static final String PRIMARY = "primary";
    private static final String END = "end";
    private static final String PREP = "prep";
    private static final String SPLIT_COMMA = ",";
    private static final int DOOR_TARGET_INDEX = 2;

    // Door specific attributes to handle room transitions
    private Image doorLocked;
    private Image doorUnlocked;
    private String doorTarget;
    private boolean doorOpen = false;
    private boolean firstEntry = false;
    private boolean doorPermanentlyOpen = false;
    private boolean doorPermanentlyClosed = false;
    private boolean isPrep = false;
    private ShadowDungeon game;

    // Constructor
    public Door(Properties game_props, String key, ShadowDungeon game) {

        if(game_props.getProperty(key)!=null){
            if (!game_props.getProperty(key).equals(NO_DOORS)) {

                // Get door data
                String[] doorData = game_props.getProperty(key).split(SPLIT_COMMA);

                // Fill in door specific attributes
                this.game = game;
                doorLocked = new Image(LOCKED_DOOR_PATH);
                doorUnlocked = new Image(UNLOCKED_DOOR_PATH);
                if(key.contains(PRIMARY) || key.contains(END)){
                    firstEntry = true;
                }
                if(key.contains(PREP)){
                    isPrep = true;
                }
                doorTarget = doorData[DOOR_TARGET_INDEX].trim();

                // Fill in room entity attributes
                this.setPosition(IOUtils.parseCoords(game_props.getProperty(key)));
                this.box(doorLocked);
            }
        }
    }

    // Check if the door is entered for the first time
    public boolean isFirstEntry() {
        return firstEntry;
    }

    // Get the door target
    public String getTarget() {
        return doorTarget;
    }

    // Open the door
    public void openDoor() {
        doorOpen = true;
    }

    // Close the door
    public void closeDoor() {
        doorOpen = false;
        firstEntry = false;
    }

    // Open the door permanently
    public void openDoorPermanently() {
        this.openDoor();
        doorPermanentlyOpen = true;
    }

    // Close the door permanently
    public void closeDoorPermanently() {
        this.closeDoor();
        doorPermanentlyClosed = true;
    }

    // Check if the door is open
    public boolean isOpen() {
        return doorOpen;
    }

    // Door rendering
    @Override
    public void render(){

        if(this.getPosition() != null){
            // Render it as locked if not open, unlocked otherwise
            if (!doorOpen) {
                doorLocked.draw(this.getPosition().x, this.getPosition().y);
            } else {
                doorUnlocked.draw(this.getPosition().x, this.getPosition().y);
            }
        }
    }

    // Player interaction with doors in a room with no enemies
    public void update(Player player, Input input){

        if(this.getPosition() != null){
            // If R or M is pressed and the current room is Prep Room
            if ((input.wasPressed((Keys.R)) || input.wasPressed((Keys.M))) && (isPrep)) {

                // Open that door permanently
                this.openDoorPermanently();
            }

            // If the permanentlyOpen flag is up
            if (doorPermanentlyOpen){

                // Open the door (make sure to never let the door close)
                this.openDoor();
            }

            // If the permanentlyClosed flag is up
            if (doorPermanentlyClosed){

                // Open the door (makesure to never let the door open)
                this.closeDoor();
            }

            // If player just entered a new room and still standing in the door
            if (firstEntry && player.isColliding(this)){

                // open the door
                this.openDoor();
            } else if (doorOpen && !player.isColliding(this)){

                // the moment the player no longer stands in the door,
                // set the first entry flag down so now the door is functioning
                firstEntry = false;
            }

            // if the door is open, the player is running towards the door, and it's not a first entry
            if (this.isOpen() && player.isColliding(this)
                    && !firstEntry) {

                // transition to the next room
                game.setCurrentRoomString(this.getTarget());

                // reset the first to prepare the door for the next time it is being used
                firstEntry = true;
            }
        }
    }

    // Player interaction with doors in a room with enemy
    public void update(Player player, ArrayList<Enemy> enemies){

        if(this.getPosition() != null){

            // If there is enemy
            if(enemies!=null){

                boolean allDefeated = true;
                for (Enemy e : enemies) {
                    if (!e.isDefeated()) {
                        allDefeated = false;
                        break; // no need to continue
                    }
                }

                // If all defeated, open door permanently
                if (allDefeated) {
                    this.openDoorPermanently();
                }

                // If player just entered a new room and still standing in the door
                if (firstEntry && player.isColliding(this)){

                    // Open the door (just a safety measure to never let the door close)
                    this.openDoor();
                } else if (doorOpen && !player.isColliding(this)){

                    // the moment the player no longer stands in the door,
                    // if it is not permanently open (meaning the enemy is not defeated yet)
                    if(!doorPermanentlyOpen){

                        // close the door back
                        this.closeDoor();
                    }

                    // set the first entry flag down so now the door is functioning
                    // if it is opened
                    firstEntry = false;
                }
            }

            // if the door is open, the player is running towards the door, and it's not a first entry
            if (this.isOpen() && player.isColliding(this)
                    && !firstEntry) {

                // transition to the next room
                game.setCurrentRoomString(this.getTarget());

                // reset the first to prepare the door for the next time it is being used
                firstEntry = true;
            }
        }
    }
}
