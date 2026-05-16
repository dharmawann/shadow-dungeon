import java.util.ArrayList;
import java.util.Properties;

/**
 * Abstract class for all enemies in the game.
 * Handles enemy health, damage, rewards, defeat status, and interactions with the player.
 */

public abstract class Enemy extends RoomEntity implements Defeatable{

    // attributes
    private String ENEMY_IMAGE_PATH;
    private double maxHealth;
    private double health;
    private double damage;
    private double contactDamage;
    private static final String ENEMY_CONTACT_DAMAGE_KEY = "riverDamagePerFrame";
    private int reward;
    private int coolDown;
    private int initialCoolDown;
    private boolean defeated;
    private final ArrayList<Fireball> fireballs = new ArrayList<>();

    // General Enemy Constructor
    public Enemy(Properties gameProps){
        contactDamage = Double.parseDouble(gameProps.getProperty(ENEMY_CONTACT_DAMAGE_KEY));
    }

    // Implementation of Defeatable interface
    @Override
    public void takeDamage() {
        health = health - Player.getCurrent_damage();
    }

    @Override
    public void defeat() {
        defeated = true;
    }

    // Getters and Setters
    public void setEnemyImagePath(String enemyImagePath) {
        ENEMY_IMAGE_PATH = enemyImagePath;
    }

    public static String getEnemyContactDamageKey() {
        return ENEMY_CONTACT_DAMAGE_KEY;
    }

    public String getEnemyImagePath() {
        return ENEMY_IMAGE_PATH;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void decreaseCoolDown(){
        coolDown--;
    }

    public void resetCoolDown(int initialCoolDown){
        coolDown = initialCoolDown;
    }

    public void setInitialCoolDown(int initialCoolDown) {
        this.initialCoolDown = initialCoolDown;
    }

    public int getInitialCoolDown() {
        return initialCoolDown;
    }

    public void shoot(Player player, Properties gameProps, ArrayList<Wall> walls){}

    public void updateFireballs(Player player, Properties gameProps, ArrayList<Wall> walls, ArrayList<Door> doors){}

    // Player interaction with enemy objects
    public void update(Player player, Door primaryDoor){

        // If the player is touching the enemy and is not dead yet
        if(player.isColliding(this) && Player.getHealth() > 0 && !this.isDefeated()) {

            // Harm the player
            Player.decreaseHealth(contactDamage);
        }
    }

    // Only render the enemy if it is not defeated yet
    public void render(Door primaryDoor) {

        // make sure it is initialized
        if(this.getPosition() != null){

            // render the enemies if not defeated and player already entered the room
            if (!this.isDefeated() && !primaryDoor.isOpen() && !primaryDoor.isFirstEntry()){
                this.getImage().draw(this.getPosition().x, this.getPosition().y);
            }

            // render every fireball
            for(Fireball fireball:fireballs){
                fireball.render();
            }
        }
    }

}
