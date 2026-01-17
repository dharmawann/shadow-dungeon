import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Abstract Room class.
 * Serves as the prototype for all rooms in the game.
 * Manages general initialisation, interaction, and rendering of the room.
 */

public abstract class Room {

    // File paths
    private static final String BACKGROUND_IMAGE_PATH = "res/background.png";

    // Room entity key prefixes
    private static final String PRIMARY_DOOR_PREFIX = "primarydoor.";
    private static final String SECONDARY_DOOR_PREFIX = "secondarydoor.";
    private static final String END_PREP_DOOR_PREFIX = "door.";
    private static final String RIVER_PREFIX = "river.";
    private static final String WALL_PREFIX = "wall.";
    private static final String TREASURE_BOX_PREFIX = "treasurebox.";
    private static final String KEY_BULLET_KIN_PREFIX = "keyBulletKin.";
    private static final String BULLET_KIN_PREFIX = "bulletKin.";
    private static final String ASHEN_BULLET_KIN_PREFIX = "ashenBulletKin.";
    private static final String TABLE_PREFIX = "table.";
    private static final String BASKET_PREFIX = "basket.";

    // The current game entity in which the room is contained
    private ShadowDungeon game;

    // Background Image
    private Image background_image = new Image(BACKGROUND_IMAGE_PATH);

    // Texts
    private Text title;
    private Text prompt;
    private Text winLoseMessage;
    private Text marineMessage;
    private Text robotMessage;
    private Text selectMessage;

    // Room entities
    private ArrayList<Door> doors;
    private Door primaryDoor;
    private Door secondaryDoor ;
    private Door endPrepDoor;
    private Sprite marineSprite;
    private Sprite robotSprite;
    private ArrayList<River> rivers;
    private ArrayList<TreasureBox> treasureBoxes;
    private ArrayList<Wall> walls;
    private ArrayList<BulletKin> bulletKins;
    private ArrayList<ashenBulletKin> ashenBulletKins;
    private ArrayList<Enemy> enemies;
    private RestartArea restartArea;
    private keyBulletKin keyBulletKin;
    private Table table;
    private Basket basket;
    private Player player;

    // Getters and Setters
    public ShadowDungeon getGame() {
        return game;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setGame(ShadowDungeon game) {
        this.game = game;
    }

    public Image getBackgroundImage() {
        return background_image;
    }

    public void setBackgroundImage(Image BACKGROUND_IMAGE) {
        this.background_image = BACKGROUND_IMAGE;
    }

    public Text getTitle() {
        return title;
    }

    public void setTitle(Text TITLE) {
        this.title = TITLE;
    }

    public Text getPrompt() {
        return prompt;
    }

    public void setPrompt(Text PROMPT) {
        this.prompt = PROMPT;
    }

    public void setSelectMessage(Text SELECT_MESSAGE) {
        this.selectMessage = SELECT_MESSAGE;
    }

    public Text getWinLoseMessage() {
        return winLoseMessage;
    }

    public void setMarineMessage(Text MARINE_MESSAGE) {
        this.marineMessage = MARINE_MESSAGE;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public void setRobotMessage(Text ROBOT_MESSAGE) {
        this.robotMessage = ROBOT_MESSAGE;
    }

    public void setWinLoseMessage(Text WIN_LOSE_MESSAGE) {
        this.winLoseMessage = WIN_LOSE_MESSAGE;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public void setDoors(ArrayList<Door> doors) {
        this.doors = doors;
    }

    public Door getPrimaryDoor() {
        return primaryDoor;
    }

    public void setPrimaryDoor(Door primaryDoor) {
        this.primaryDoor = primaryDoor;
    }

    public Door getSecondaryDoor() {
        return secondaryDoor;
    }

    public void setSecondaryDoor(Door secondaryDoor) {
        this.secondaryDoor = secondaryDoor;
    }

    public Door getEndPrepDoor() {
        return endPrepDoor;
    }

    public void setEndPrepDoor(Door endPrepDoor) {
        this.endPrepDoor = endPrepDoor;
    }

    public ArrayList<River> getRivers() {
        return rivers;
    }

    public void setRivers(ArrayList<River> rivers) {
        this.rivers = rivers;
    }

    public ArrayList<TreasureBox> getTreasureBoxes() {
        return treasureBoxes;
    }

    public void setTreasureBoxes(ArrayList<TreasureBox> treasureBoxes) {
        this.treasureBoxes = treasureBoxes;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    public RestartArea getRestartArea() {
        return restartArea;
    }

    public void setRestartArea(RestartArea restartArea) {
        this.restartArea = restartArea;
    }

    public keyBulletKin getKeyBulletKin() {
        return keyBulletKin;
    }

    public void setKeyBulletKin(keyBulletKin enemy) {
        this.keyBulletKin = enemy;
    }

    public void setBulletKins(ArrayList<BulletKin> bulletKins) {
        this.bulletKins = bulletKins;
    }

    public void setAshenBulletKins(ArrayList<ashenBulletKin> ashenBulletKins) {
        this.ashenBulletKins = ashenBulletKins;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMarineSprite(Sprite marineSprite) {
        this.marineSprite = marineSprite;
    }

    public void setRobotSprite(Sprite robotSprite) {
        this.robotSprite = robotSprite;
    }

    // BulletKins getter
    public ArrayList<BulletKin> getBulletKins() {
        return bulletKins;
    }

    // AshenBulletKins getter
    public ArrayList<ashenBulletKin> getAshenBulletKins() {
        return ashenBulletKins;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    // Constructor
    public Room(Properties gameProps, ShadowDungeon game, String key){

        // Keep track of the current ShadowDungeon entity
        this.setGame(game);

        // Initiate common room entities
        this.setRivers(River.rivers(gameProps, RIVER_PREFIX + key));
        this.setWalls(Wall.walls(gameProps, WALL_PREFIX + key));
        this.setDoors(new ArrayList<Door>());
        this.setPrimaryDoor(new Door(gameProps, PRIMARY_DOOR_PREFIX + key, game));
        this.setSecondaryDoor(new Door(gameProps, SECONDARY_DOOR_PREFIX + key, game));
        this.setEndPrepDoor(new Door(gameProps, END_PREP_DOOR_PREFIX + key, game));
        this.getDoors().add(this.getPrimaryDoor());
        this.getDoors().add(this.getSecondaryDoor());
        this.getDoors().add(this.getEndPrepDoor());
        this.setTreasureBoxes(TreasureBox.treasureBoxes(gameProps, TREASURE_BOX_PREFIX + key));
        this.setKeyBulletKin(new keyBulletKin(gameProps, KEY_BULLET_KIN_PREFIX + key));
        this.setBulletKins(BulletKin.bulletKins(gameProps, BULLET_KIN_PREFIX + key));
        this.setAshenBulletKins(ashenBulletKin.ashenBulletKins(gameProps, ASHEN_BULLET_KIN_PREFIX + key));
        this.setEnemies(new ArrayList<>());
        if (getBulletKins() != null) {
            getEnemies().addAll(getBulletKins());
        }

        if (getAshenBulletKins() != null) {
            getEnemies().addAll(getAshenBulletKins());
        }

        if (getKeyBulletKin() != null && getKeyBulletKin().getPosition() != null) {
            getEnemies().add(getKeyBulletKin());
        }

        this.setTable(new Table(gameProps, TABLE_PREFIX + key));
        this.setBasket(new Basket(gameProps, BASKET_PREFIX + key));
    }

    // Player interaction with the room
    private void update(Input input) {

        // Character Selection
        if(game.getCurrentRoomString().equals("prep") && input.wasPressed((Keys.M))){
            game.setCurrentCharacter(game.getMarine());
        }

        if(game.getCurrentRoomString().equals("prep") && input.wasPressed((Keys.R))){
            game.setCurrentCharacter(game.getRobot());
        }

        // Door interaction
        if(doors != null){
            for(Door door:doors){
                if(keyBulletKin.getPosition()!=null){
                    door.update(player, enemies);
                } else{
                    door.update(player, input);
                }
            }
        }

        // Move player
        player.move(input, doors, walls, table, basket);

        // River interaction
        if(rivers != null){
            for (River river : rivers) {
                river.update(player, game);
            }
        }

        // Shooting Mechanism
        player.shoot(input, table, basket, enemies, walls, doors);

        // Box interaction
        if(treasureBoxes != null){
            for (TreasureBox treasureBox : treasureBoxes) {
                treasureBox.update(player, input);
            }
        }

        // RestartArea interaction
        if(restartArea!=null){
            restartArea.update(player, input);
        }

        // Enemy interaction (only after the player leaves the door)
        if (enemies != null) {
            for (Enemy enemy : enemies) {
                enemy.update(player, primaryDoor);
                enemy.shoot(player, game.getGAME_PROPS(), walls);
                enemy.updateFireballs(player, game.getGAME_PROPS(), walls, doors);
            }
        }

        // Key interaction (collect the key if player touches it)
        if (keyBulletKin != null && keyBulletKin.getKey() != null) {
            keyBulletKin.getKey().update(player);
        }
    }

    // Room rendering
    public void render(){

        // Render background
        background_image.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        // Render door
        if(doors != null){
            for (Door door : doors){
                door.render();
            }
        }

        // Render river
        if(rivers != null){
            for (River river : rivers){
                river.render();
            }
        }

        // Render enemies and fireballs
        if(enemies != null){
            for (Enemy enemy : enemies){
                enemy.render(primaryDoor);
            }
        }

        // Render Keys only if not collected
        if (keyBulletKin.getKey() != null && !keyBulletKin.getKey().isCollected()) {
            keyBulletKin.getKey().render();
        }

        // Render treasure box
        if(treasureBoxes != null){
            for (TreasureBox treasureBox : treasureBoxes){
                treasureBox.render();
            }
        }

        // Render wall
        if(walls != null){
            for (Wall wall : walls){
                wall.render();
            }
        }

        // Render restart area
        if(restartArea!=null){
            restartArea.render();
        }

        // Render title
        if(title !=null){
            title.render();
        }

        // Render prompt
        if(prompt !=null){
            prompt.render();
        }
        if(robotMessage !=null){
            robotMessage.render();
        }
        if(marineMessage !=null){
            marineMessage.render();
        }
        if(selectMessage !=null){
            selectMessage.render();
        }

        // Render win/lose message
        if(winLoseMessage !=null){
            winLoseMessage.render();
        }

        // Render table
        if(table != null){
            table.render();
        }

        // Render basket
        if(basket != null){
            basket.render();
        }

        // Render sprites
        if(marineSprite!=null){
            marineSprite.render();
        }

        if(robotSprite!=null){
            robotSprite.render();
        }

        // Render player
        if(player!=null){
            player.render();
        }


    }

    // Wrapper function to run the room
    public void run(Input input){
        this.update(input);
        this.render();
    }
}
