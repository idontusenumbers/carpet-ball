import java.awt.Color;
import java.awt.Point;

public class Ball {
	private int number;
	private Color color;
	private Point location;

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

	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
}
