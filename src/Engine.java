import java.awt.*;
import java.awt.geom.Point2D;

	public class Engine implements BallListener{
    private GameState state;
    public Engine(GameState state)
	{
        this.state = state;
    }

    public void tick()
	{
		for (Ball Balls: state.getMyBalls())
		{
			speed(Balls);
		}
		for (Ball balls: state.getTheirBalls())
		{
			speed(balls);
		}
		speed(state.getCueBall());

		for (Ball myBall : state.getMyBalls())
		{
			for (Ball theirBall : state.getTheirBalls())
			{
				if (myBall.getLocation().distance(theirBall.getLocation()) < Ball.BALL_RADIUS) {
					Point2D center = new Point2D.Float((float) (myBall.getLocation().getX() + theirBall.getLocation().getX()) / 2, (float) (myBall.getLocation().getY() + theirBall.getLocation().getY()) / 2);
					ballImpacted(myBall, theirBall, center);
				}
			}
			for (Ball myOtherBall : state.getMyBalls())
			{
				if (myBall.getLocation().distance(myOtherBall.getLocation()) < Ball.BALL_RADIUS && myOtherBall != myBall) {
					Point2D center = new Point2D.Float((float) (myBall.getLocation().getX() + myOtherBall.getLocation().getX()) / 2, (float) (myBall.getLocation().getY() + myOtherBall.getLocation().getY()) / 2);
					ballImpacted(myBall, myOtherBall, center);
				}
			}
			if (myBall.getLocation().distance(state.getCueBall().getLocation()) < Ball.BALL_RADIUS) {
				Point2D center = new Point2D.Float((float) (myBall.getLocation().getX() + state.getCueBall().getLocation().getX()) / 2, (float) (myBall.getLocation().getY() + state.getCueBall().getLocation().getY()) / 2);
				ballImpacted(myBall, state.getCueBall(), center);
			}
		}

    }
	public void speed(Ball regballs)
	{
        regballs.setLocation(new Point2D.Float((float) (regballs.getLocation().getX() + regballs.getHorizontalSpeed()), (float) (regballs.getLocation().getY() + regballs.getSpeed())));
		wall(regballs);

		for (Ball Balls: state.getMyBalls()){
			if (Balls.isInGutter())
			{
				Balls.setSpeed(0);
			}
		}
		for (Ball balls: state.getTheirBalls()){
			if (balls.isInGutter())
			{
				balls.setSpeed(0);
			}
		}
		if (state.getCueBall().isInGutter())
		{
				state.getCueBall().setSpeed(0);
		}

	}
	public void wall(Ball b)
	{
		float x = (float) b.getLocation().getX();
		float y = (float) b.getLocation().getY();
		// Check to run into wall
		if (x >= 700 || x <= 0)
			ballCollidedWithWall(b, b.getSpeed(), b.getAngle());
		// Check to run into your balls
		for (Ball ball : state.getMyBalls()) {
			if (Point2D.distance(ball.getLocation().getX(), ball.getLocation().getY(), x, y) < Ball.BALL_RADIUS) {
				Point2D newLoc = new Point2D.Float((float) ((ball.getLocation().getX() + x) / 2), (float) ((ball.getLocation().getY() + y) / 2));
				ballImpacted(b, state.getCueBall(), newLoc);
			}
		}
		// Check to run into their balls
		for (Ball ball : state.getTheirBalls()) {
			if (Point2D.distance(ball.getLocation().getX(), ball.getLocation().getY(), x, y) < Ball.BALL_RADIUS) {
				Point2D newLoc = new Point2D.Float((float) ((ball.getLocation().getX() + x) / 2), (float) ((ball.getLocation().getY() + y) / 2));
				ballImpacted(b, state.getCueBall(), newLoc);
			}
		}
		// Check for cue ball
		Point2D cueLocation = state.getCueBall().getLocation();
		if (Point2D.distance(cueLocation.getX(), cueLocation.getY(), x, y) < Ball.BALL_RADIUS) {
			Point2D newLoc = new Point2D.Float((float) ((cueLocation.getX() + x) / 2), (float) ((cueLocation.getY() + y) / 2));
			ballImpacted(b, state.getCueBall(), newLoc);
		}
	}

    // BallListener implementation
	@Override
    public void ballSentIntoMotion(Ball b, float speed, float angle)
	{
		b.setAngle(angle);
		b.setSpeed(speed);
    }
    public void ballRelocated(Ball b, Point2D p) {
		b.setLocation(p);
    }
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint)
	{
		if (a.getSpeed() > b.getSpeed())
		{
			ballSentIntoMotion(a, getImpactSpeed(a, b, true), a.getHorizontalSpeed());
			ballSentIntoMotion(b, getImpactSpeed(a, b, false), b.getHorizontalSpeed());
		}
		else if (a.getSpeed() < b.getSpeed())
		{
			ballSentIntoMotion(a, getImpactSpeed(a, b, false), a.getHorizontalSpeed());
			ballSentIntoMotion(b, getImpactSpeed(a, b, true), b.getHorizontalSpeed());
		}
    }

		@Override
		public void ballCollidedWithWall(Ball b, float speed, float angle) {
			float newAngle;
			if (angle > (Math.PI / 2) && angle < (3 * Math.PI / 2)) {
				newAngle = (float) ((Math.PI / 2) - (angle - Math.PI / 2));
			} else {
				newAngle = (float) (angle - (Math.PI / 2 - angle));
			}
			ballSentIntoMotion(b, (float) (b.getSpeed() * 0.75), newAngle);
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