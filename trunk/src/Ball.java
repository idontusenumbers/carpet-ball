import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Ball {
    public static final int BALL_RADIUS = 12;
    private int number;
    private Color color;
    private Point2D location;
    private float rotation;
    private float velocity;

    public Ball(int number, Color color, Point2D location) {
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

    public void setLocation(Point2D location) {
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

	public boolean isOnBall(Point2D ballPos, Point2D otherPos) {
		ballPos.setLocation(ballPos.getX() + Ball.BALL_RADIUS, ballPos.getY() + Ball.BALL_RADIUS);
		return Point2D.distance(ballPos.getX(), ballPos.getY(), otherPos.getX(), otherPos.getY()) < Ball.BALL_RADIUS;
	}
}
