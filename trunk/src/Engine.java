import java.awt.geom.Point2D;

public class Engine implements BallListener{
    private GameState state;
    public Engine(GameState state)
	{
        this.state = state;
    }

    public void tick()
	{

    }
	public void speed()
	{
		float newX = 0;
		float newY = 0;
		state.getCueBall().setLocation(new Point2D.Float(newX, newY));
	}
	public Point2D wall(int rise, int run, Point2D location, int loop)
	{
		double X;
		double Y;
		Point2D wall = null;
		for (int i = 0; i < loop; i+=1)
		{
			if (location.getX() < 300 && location.getX() > 0)
			{
				wall = null;
			}
			else
			{
				X = location.getX();
				Y = location.getY();
				wall = new Point2D.Double(X, Y);
			}
			if (location.getY() > 0)
			{
				wall = null;
			}
			location.setLocation(location.getX() + run, location.getY() + rise);
		}
		return wall;
	}


    // BallListener implementation

    public void ballSentIntoMotion(Ball b, float speed, float angle) {

    }
    public void ballRelocated(Ball b, Point2D p) {

    }
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {

    }
}
/*
What happens when the cue ball hits the carpet balls.
What happens when the carpet balls hit other carpet balls.
What happens When the balls hit the wall.
What happens to the ball when it gets thrown.
How much power the ball gets thrown with.
How much spin the ball has.
The angle the ball gets thrown.
What happens when the ball reaches the end of the board."Set velocity to 0"
 */