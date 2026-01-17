import bagel.Image;

public class Robot extends Character{
    private final String PLAYER_LEFT_IMAGE_PATH = "res/robot_left.png";
    private final String PLAYER_RIGHT_IMAGE_PATH = "res/robot_right.png";
    private final String PLAYER_IMAGE_PATH = "res/robot.png";
    public Robot() {
        this.setName("Robot");
        this.setSkill("ROBOT: +5$ PER KILL");
        this.setImageLeft(new Image(PLAYER_LEFT_IMAGE_PATH));
        this.setImageRight(new Image(PLAYER_RIGHT_IMAGE_PATH));
        this.setImage(new Image(PLAYER_IMAGE_PATH));
    }
}
