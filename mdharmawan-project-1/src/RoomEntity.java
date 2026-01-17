import bagel.Image;
import bagel.util.Point;

/**
 * Abstract Room Entity class.
 * Serves as the prototype of room entity objects
 * Handles "boxing", basic rendering, and getter functions of room entity objects.
 */

public abstract class RoomEntity {

    // Room entity attributes
    private Point position;
    private Image image ;
    private double left;
    private double right;
    private double top;
    private double bottom;

    // Getters and Setters
    public Point getPosition() {
        return position;
    }

    public double getBottom() {
        return bottom;
    }

    public double getTop() {
        return top;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public Image getImage() {
        return image;
    }

    public void setPosition(Point POSITION) {
        this.position = POSITION;
    }

    public void setImage(Image IMAGE) {
        image = IMAGE;
    }

    public void setLEFT(double LEFT) {
        this.left = LEFT;
    }

    public void setRight(double right) {
        this.right = right;
    }


    public void setTop(double top) {
        this.top = top;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    // Fill in attributes to "box" the entity, useful for checking Player collisions with other room entities
    public void box(Image image){
        left = position.x - image.getWidth() / 2.0;
        right = position.x + image.getWidth() / 2.0;
        top = position.y - image.getHeight() / 2.0;
        bottom = position.y + image.getHeight() / 2.0;
    }

    // Check whether the current position is colliding with some entity
    public boolean isColliding(RoomEntity entity) {

        return this.getRight() >= entity.getLeft() &&
                this.getLeft() <= entity.getRight() &&
                this.getBottom() >= entity.getTop() &&
                this.getTop() <= entity.getBottom();
    }

    // Basic room entity rendering
    public void render(){
        image.draw(position.x, position.y);
    }

}
