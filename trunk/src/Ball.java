import java.awt.Color;
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

    public void setNumber(int number) {
        this.number = number;
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

	/**
	 * Compares the speed of two balls
	 *
	 * @return 1 if A is faster than B, -1 if B is faster than A, and 0 if they are the same
	 */
	public int compareSpeedTo(Ball b) {
		if (getVelocity() > b.getVelocity())
			return 1;
		else if (getVelocity() < b.getVelocity())
			return -1;
		else
			return 0;
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
