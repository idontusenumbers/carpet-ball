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
	public Point2D wall(int rise, int run, Point2D location)
	{
		float X;
		float Y;
		Point2D wall = null;
		if (location.getX() < 300 && location.getX() > 0)
		{
			wall = null;
		}
		else
		{
			X = (float) location.getX();
			Y = (float) location.getY();
			wall = new Point2D.Float(X, Y);
		}
		if (location.getY() > 0)
		{
			wall = null;
		}
		location.setLocation(location.getX() + run, location.getY() + rise);
		return wall;
	}


    // BallListener implementation

    public void ballSentIntoMotion(Ball b, float speed, float angle) {

    }
    public void ballRelocated(Ball b, Point2D p) {

    }
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {

    }

	public float getImpactSpeed(Ball a, Ball b, boolean forMovingBall) {
		if (forMovingBall)
			return (float) (Math.max(a.getSpeed(), b.getSpeed()) * 0.75);
		else
			return (float) (Math.max(a.getSpeed(), b.getSpeed()) * 0.5);
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