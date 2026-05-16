import bagel.Image;

/**
 * Abstract Character class.
 * Serves as the prototype of character objects
 */

public abstract class Character {

    // Character attributes
    private String name;
    private String skill;
    private Image imageLeft;
    private Image imageRight;
    private Image image;
    private Image sprite;

    // Getters and Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public void setImageLeft(Image imageLeft) {
        this.imageLeft = imageLeft;
    }

    public Image getImageLeft() {
        return imageLeft;
    }

    public void setImageRight(Image imageRight) {
        this.imageRight = imageRight;
    }

    public Image getImageRight() {
        return imageRight;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
