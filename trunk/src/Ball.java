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
    private boolean isHovered;

    public Ball(int number, Point2D location) {
        this.number = number;

        this.location = location;
    }

    public int getNumber() {
        return number;
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

	public boolean isOnBall(Point2D ballPos) {
//		Point2D.Float newBallPos = new Point2D.Float((float) ballPos.getX() + Ball.BALL_RADIUS, (float) ballPos.getY() + Ball.BALL_RADIUS);
//		Point2D.Float otherPos = new Point2D.Float((float) getLocation().getX() + Ball.BALL_RADIUS, (float) getLocation().getY() + Ball.BALL_RADIUS);
//		return Point2D.distance(newBallPos.getX(), newBallPos.getY(), otherPos.getX(), otherPos.getY()) < Ball.BALL_RADIUS;

        return getLocation().distance(ballPos) < BALL_RADIUS;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }
}
