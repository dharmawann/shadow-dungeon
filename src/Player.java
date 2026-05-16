import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Player class.
 * Handles quick construction, movement, rendering, getters and setters functions of Player objects.
 */

public class Player extends RoomEntity{

    // Constants
    private static final String PLAYER_START_KEY = "player.start";
    private static final String PLAYER_STATS_FONT_KEY = "playerStats.fontSize";
    private static final String FONT_KEY = "font";
    private static final String MOVING_SPEED_KEY = "movingSpeed";
    private static final String HEALTH_STAT_KEY = "healthStat";
    private static final String COIN_STAT_KEY = "coinStat";
    private static final String KEY_STAT_KEY = "keyStat";
    private static final String WEAPON_STAT_KEY = "weaponStat";
    private static final String INITIAL_HEALTH_KEY = "initialHealth";
    private static final String INITIAL_COINS_KEY = "initialCoins";
    private static final String BULLET_KEY = "bullet";
    private static final String PLAYER_LEFT_IMAGE_PATH = "res/player_left.png";
    private static final String PLAYER_RIGHT_IMAGE_PATH = "res/player_right.png";
    private static final String FREQUENCY_SUFFIX = "Freq";
    private static final String BASKET_COIN_KEY = "basketCoin";
    private static final String STANDARD_WEAPON_KEY = "weaponStandardDamage";
    private static final String ADVANCE_WEAPON_KEY = "weaponAdvanceDamage";
    private static final String ELITE_WEAPON_KEY = "weaponEliteDamage";
    private static final String ROBOT_SKILL_KEY = "robotExtraCoin";

    // Player object specific attributes
    private boolean facingRight;
    private final double SPEED;
    private final Font STATS_FONT;
    private final Point HEALTH_POSITION;
    private final Point COINS_POSITION;
    private final Point KEYS_POSITION;
    private final Point WEAPON_POSITION;
    private final Image IMAGE_LEFT;
    private final Image IMAGE_RIGHT;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private ShadowDungeon game;
    private int shootCooldown = 0;
    private final int BULLET_FREQ;

    // Static variables to track player stats
    private static double initial_health;
    private static int initial_coins;
    private static double health;
    private static int coins;
    private static int keys;
    public static boolean initialized = false;
    private static int weapon_level;
    private static int current_damage;
    private static int[] weapon_damage;

    // Default spawn point constructor
    public Player(Properties gameProps, ShadowDungeon game) {
        this(gameProps, IOUtils.parseCoords(gameProps.getProperty(PLAYER_START_KEY)), game);
    }

    // Constructor with custom position
    public Player(Properties gameProps, Point position, ShadowDungeon game) {

        // Fill in room entity attributes
        this.setPosition(position);

        // Fill in Player specific attributes
        IMAGE_LEFT = new Image(PLAYER_LEFT_IMAGE_PATH);
        IMAGE_RIGHT = new Image(PLAYER_RIGHT_IMAGE_PATH);
        facingRight = false; // Default facing for custom spawn
        BULLET_FREQ = Integer.parseInt(game.getGAME_PROPS().getProperty(BULLET_KEY + FREQUENCY_SUFFIX));
        weapon_damage = new int[]{Integer.parseInt(gameProps.getProperty(STANDARD_WEAPON_KEY)), Integer.parseInt(gameProps.getProperty(ADVANCE_WEAPON_KEY)), Integer.parseInt(gameProps.getProperty(ELITE_WEAPON_KEY))};

        // Fill in Player Stats
        String fontPath = gameProps.getProperty(FONT_KEY);
        int statsSize = Integer.parseInt(gameProps.getProperty(PLAYER_STATS_FONT_KEY));
        STATS_FONT = new Font(fontPath, statsSize);
        SPEED = Double.parseDouble(gameProps.getProperty(MOVING_SPEED_KEY));
        HEALTH_POSITION = IOUtils.parseCoords(gameProps.getProperty(HEALTH_STAT_KEY));
        COINS_POSITION = IOUtils.parseCoords(gameProps.getProperty(COIN_STAT_KEY));
        KEYS_POSITION = IOUtils.parseCoords(gameProps.getProperty(KEY_STAT_KEY));
        WEAPON_POSITION = IOUtils.parseCoords(gameProps.getProperty(WEAPON_STAT_KEY));
        initial_health = Double.parseDouble(gameProps.getProperty(INITIAL_HEALTH_KEY));
        initial_coins = Integer.parseInt(gameProps.getProperty(INITIAL_COINS_KEY));
        this.game = game;
        current_damage = weapon_damage[weapon_level];

        if(!initialized) {
            health = initial_health;
            coins = initial_coins;
            weapon_level = 0;
            keys = 0;
        }

        initialized = true;

    }

    // reset Keys
    public static void resetKeys() {
        keys = 0;
    }

    // reset weapons
    public static void resetWeapon() {
        weapon_level = 0;
    }

    // Movement logic of player
    public void move(Input input, ArrayList<Door> doors, ArrayList<Wall> walls, Table table, Basket basket) {

        // Make the player facing the cursor
        this.faceMouse(input);

        // Calculate "new" Position after each WASD input
        double newX = this.getPosition().x;
        double newY = this.getPosition().y;

        if (input.isDown(Keys.W)){
            newY -= SPEED;
        }
        if (input.isDown(Keys.S)){
            newY += SPEED;
        }
        if (input.isDown(Keys.A)){
            newX -= SPEED;
        }
        if (input.isDown(Keys.D)){
            newX += SPEED;
        }

        Point newPos = new Point(newX, newY);

        // If there are doors in this room
        if(doors != null){

            // For each door
            for (Door door : doors) {

                // If the door is closed, not a first entry, and the new position will collide that door,

                // by the way, note the not first entry condition, because during a
                // first entry the "this" player should already be
                // colliding since the beginning with the door, but there would be no time for the door to open
                // "this" player is now colliding with this door because "this" player's position is can not be

                if (!door.isOpen() && !door.isFirstEntry() && willCollide(newPos, door)) {

                    // block the movement
                    return;
                }
            }
        }


        // If there are walls in this room
        if (walls != null){

            // for every wall
            for (Wall wall : walls) {

                // if the player will collide with the wall
                if (willCollide(newPos, wall)) {
                    // Block movement
                    return;
                }
            }
        }

        // if there is table in this room
        if (table.getPosition() != null && !table.isDestroyed()) {
            // if the player will collide with the table
            if (willCollide(newPos, table)) {
                // block movement
                return;
            }
        }

        // if there is basket in this room
        if (basket.getPosition() != null && !basket.isDestroyed()) {
            // if the player will collide with the table
            if (willCollide(newPos, basket)) {
                // block movement
                return;
            }
        }

        //  Update the player's position, ensuring it is within bounds
        double halfWidth = IMAGE_RIGHT.getWidth() / 2.0;
        double halfHeight = IMAGE_RIGHT.getHeight() / 2.0;
        this.setPosition(new Point(
                Math.max(halfWidth, Math.min(newX, Window.getWidth() - halfWidth)),
                Math.max(halfHeight, Math.min(newY, Window.getHeight() - halfHeight))));

        // update the 'box' of the player
        this.box(IMAGE_RIGHT);
    }

    // Check whether the new position will collide with some entity
    public boolean willCollide(Point newPos, RoomEntity entity) {
        double halfWidth = IMAGE_RIGHT.getWidth() / 2.0;
        double halfHeight = IMAGE_RIGHT.getHeight() / 2.0;

        double playerLeft = newPos.x - halfWidth;
        double playerRight = newPos.x + halfWidth;
        double playerTop = newPos.y - halfHeight;
        double playerBottom = newPos.y + halfHeight;

        return playerRight >= entity.getLeft() &&
                playerLeft <= entity.getRight() &&
                playerBottom >= entity.getTop() &&
                playerTop <= entity.getBottom();
    }

    // Check whether the current position is colliding with some entity
    public boolean isColliding(RoomEntity entity) {

        return this.getRight() >= entity.getLeft() &&
                this.getLeft() <= entity.getRight() &&
                this.getBottom() >= entity.getTop() &&
                this.getTop() <= entity.getBottom();
    }

    // Get player health
    public static double getHealth() {
        return health;
    }

    // Get current weapon damage
    public static int getCurrent_damage() {
        return current_damage;
    }

    // Get player coins
    public static int getCoins() {
        return coins;
    }

    // Get Keys
    public static int getKeys() {
        return keys;
    }

    // collect Keys
    public static void collectKeys(){
        keys++;
    }

    // use Keys
    public static void useKeys(){
        keys--;
    }

    // Get Weapon Level
    public static int getWeapon_level() {
        return weapon_level;
    }

    // Increase player coins
    public static void increaseCoins(int reward) {
        coins += reward;
    }

    // Decrease player's health
    public static void decreaseHealth(double damage) {
        health -= damage;
    }

    // Add player health
    public static void addHealth(double healthBonus) {
        health += healthBonus;
    }

    // Reset player's health
    public static void resetHealth(){
        health = initial_health;
    }

    // Reset player coins
    public static void resetCoins(){
        coins = initial_coins;
    }

    // Take damage from enemis
    public static void takeDamage(Fireball fireball){
        health -= fireball.getDamage();
    }

    // Upgrade weapon
    public static void upgradeWeapon(){
        weapon_level += 1;
        current_damage = weapon_damage[weapon_level];
    }

    // Render the player sprite and Sprites
    public void render() {

        // Face right or left based on current character
        if (facingRight) {
            this.game.getCurrentCharacter().getImageRight().draw(this.getPosition().x, this.getPosition().y);
        } else {
            this.game.getCurrentCharacter().getImageLeft().draw(this.getPosition().x, this.getPosition().y);
        }

        // Render bullets
        for (Bullet bullet : bullets) {
            bullet.render();
        }

        // Stats
        STATS_FONT.drawString("Health " + String.format("%.1f", Player.getHealth()), HEALTH_POSITION.x, HEALTH_POSITION.y);
        STATS_FONT.drawString("Coins " + Player.getCoins(), COINS_POSITION.x, COINS_POSITION.y);
        STATS_FONT.drawString("Keys " + Player.getKeys(), KEYS_POSITION.x, KEYS_POSITION.y);
        STATS_FONT.drawString("Weapon Level " + Player.getWeapon_level(), WEAPON_POSITION.x, WEAPON_POSITION.y);
    }

    // Make the player face the cursor
    public void faceMouse(Input input) {
        double mouseX = input.getMouseX();

        // Face right if mouse is to the right of player, face left otherwise
        if (mouseX >= this.getPosition().x) {
            facingRight = true;
        } else {
            facingRight = false;
        }
    }

    // Shooting Mechanism
    public void shoot(Input input, Table table, Basket basket, ArrayList<Enemy> enemies, ArrayList<Wall> walls, ArrayList<Door> doors) {

        // Decrease cooldown each frame
        if (shootCooldown > 0) {
            shootCooldown--;
        }

        // If mouse is pressed and cooldown is 0, shoot a bullet
        if (input.isDown(MouseButtons.LEFT) && shootCooldown <= 0) {

            // create and append the new bullet to the list
            Point startPos = this.getPosition();
            Point targetPos = input.getMousePosition();
            bullets.add(new Bullet(game.getGAME_PROPS(), startPos, targetPos));

            // Reset cooldown
            shootCooldown = (int) BULLET_FREQ;
        }

        // Update all bullets and handle collisions
        for (Bullet bullet : bullets) {

            // Move the bullet and handle if the bullet goes offscreen and hit walls
            bullet.update(walls, doors);

            // Handle collision with table
            if(bullet.isColliding(table) && !table.isDestroyed()){

                // Destroy table upon collision and deactivate the bullet
                table.destroy();
                bullet.deactivate();
            }

            // Handle collision with basket
            if(bullet.isColliding(basket) && !basket.isDestroyed()){

                // Destroy basket upon collision, deactivate bullet, and reward the player
                basket.destroy();
                bullet.deactivate();
                Player.increaseCoins(Integer.parseInt(game.getGAME_PROPS().getProperty(BASKET_COIN_KEY)));
            }

            // Handle collision with enemies
            for(Enemy enemy:enemies){

                // If the enemy is still alive
                if(bullet.isColliding(enemy) && !enemy.isDefeated()){

                    // deactivate the bullet, damage the enemy
                    bullet.deactivate();
                    enemy.takeDamage();

                    // If the enemy's health got below 0 after getting hit
                    if(enemy.getHealth() <= 0){

                        // mark the enemy as defeated, reward the player if
                        // its not keyBulletKin, and reward extra if the character is robot
                        enemy.defeat();
                        Player.increaseCoins(enemy.getReward());
                        if(game.getCurrentCharacter().getName().equals("Robot") && !(enemy instanceof keyBulletKin)) {
                            Player.increaseCoins(Integer.parseInt(game.getGAME_PROPS().getProperty(ROBOT_SKILL_KEY)));
                        }
                    }

                }
            }
        }


        // Remove inactive bullets
        bullets.removeIf(bullet -> !bullet.isActive());
    }
}
