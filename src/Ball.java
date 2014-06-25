import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Ball {
    private int number;
    private Color color;
    private Point2D location;
    private float rotation;
    private float velocity;

    public Ball(int number, Color color, Point location) {
        this.number = number;
        this.color = color;
        this.location = location;
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
