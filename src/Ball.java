import java.awt.Color;
import java.awt.geom.Point2D;

public class Ball {
    public static final float BALL_RADIUS = 12;
    private int number;
    private Color color;
    private Point2D location;
    private float angle;
    private float speed;
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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


	public boolean isOnBall(Point2D ballPos) {
        return getLocation().distance(ballPos) < BALL_RADIUS;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }
}
