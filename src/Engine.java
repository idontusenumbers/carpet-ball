import java.awt.*;
import java.awt.geom.Point2D;

public class Engine implements BallListener {
    private Table table;
    private GameState state;
    private BallListener ballListener;

    public Engine(Table table, GameState state, BallListener ballListener) {
        this.table = table;
        this.state = state;
        this.ballListener = ballListener;
    }

    public void tick() {
        // computer understands where the balls are using a for loop
        for (Ball ball : state.getMyBalls()) {
            speed(ball);
        }
        for (Ball ball : state.getTheirBalls()) {
            speed(ball);
        }
        speed(state.getCueBall());
        for (Ball myBall : state.getMyBalls()) {
            // ball collisions or/and impactions
            for (Ball theirBall : state.getTheirBalls()) {
                if (myBall.getLocation().distance(theirBall.getLocation()) < Ball.BALL_RADIUS) {
                    Point2D center = new Point2D.Float((float) (myBall.getLocation().getX() + theirBall.getLocation().getX()) / 2, (float) (myBall.getLocation().getY() + theirBall.getLocation().getY()) / 2);
                    ballListener.ballImpacted(myBall, theirBall, center);
                }
            }
            // impactions where the ball the computer is not focusing on is being impacted
            for (Ball myOtherBall : state.getMyBalls()) {
                if (myBall.getLocation().distance(myOtherBall.getLocation()) < Ball.BALL_RADIUS && myOtherBall != myBall) {
                    Point2D center = new Point2D.Float((float) (myBall.getLocation().getX() + myOtherBall.getLocation().getX()) / 2, (float) (myBall.getLocation().getY() + myOtherBall.getLocation().getY()) / 2);
                    ballListener.ballImpacted(myBall, myOtherBall, center);
                }
            }
            // cue ball impacting other things
            if (myBall.getLocation().distance(state.getCueBall().getLocation()) < Ball.BALL_RADIUS) {
                Point2D center = new Point2D.Float((float) (myBall.getLocation().getX() + state.getCueBall().getLocation().getX()) / 2, (float) (myBall.getLocation().getY() + state.getCueBall().getLocation().getY()) / 2);
                ballListener.ballImpacted(myBall, state.getCueBall(), center);
            }
        }

    }

    public void speed(Ball b) {
        //sets balls in gutter to have a speed of 0
        if (isInGutter(b)) {
            b.setSpeed(0);
            return;
        }

        float a = b.getAngle();
        float s = b.getSpeed();
        float dX = getXComp(s, a);
        float dY = getYComp(s, a);
        float x = (float) (b.getLocation().getX() + dX);
        float y = (float) (b.getLocation().getY() + dY);
        possiblyRepositionDueToWall(b, new Point2D.Float(x, y));


        if (s > 0) {
            s *= .9f;
            if (s < .2)
                s = 0;
        }
        b.setSpeed(s);
    }


    public float getXComp(float mag, float angle) {
        if (angle < Math.PI / 2)
            return (float) (mag * Math.cos(angle));
        else if (angle < Math.PI)
            return (float) -(mag * (Math.cos(Math.PI - angle)));
        else if (angle < Math.PI * 1.5)
            return (float) -(mag * Math.cos(angle - Math.PI));
        else
            return (float) (mag * Math.cos(Math.PI * 2 - angle));
    }

    public float getYComp(float mag, float angle) {
        if (angle < Math.PI / 2)
            return (float) -(mag * Math.sin(angle));
        else if (angle < Math.PI)
            return (float) -(mag * Math.sin(Math.PI - angle));
        else if (angle < Math.PI * 1.5)
            return (float) (mag * Math.sin(angle - Math.PI));
        else
            return (float) (mag * Math.sin(Math.PI * 2 - angle));
    }

    // defines whether or not the ball is in the area the gutter occupies on the screen
    public boolean isInGutter(Ball b) {
        float y = (float) b.getLocation().getY();
        // defines the area of the gutter on the screen
        return (y > 0 && y < table.getGutterDepth()) || (y > table.getHeight() - table.getGutterDepth() && y < 700);
    }

    private void possiblyRepositionDueToWall(Ball b, Point2D.Float newLocation) {

        b.setLocation(newLocation);
        float x = newLocation.x;
        float y = newLocation.y;
        // Check to run into wall
        //For x > table.getWidth() - Ball.BALL_RADIUS and x < Ball.BALL_RADIUS the symbals might need to be fliped
        if (x > table.getWidth() - Ball.BALL_RADIUS) {
            reflectY(b);
            b.setLocation(new Point2D.Float(table.getWidth() - (x - table.getWidth()) - Ball.BALL_RADIUS, y));

        } else if (x < Ball.BALL_RADIUS) {
            reflectY(b);
            b.setLocation(new Point2D.Float(-x + Ball.BALL_RADIUS, y));

        }
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
    public void ballSentIntoMotion(Ball b, float speed, float angle) {
        b.setAngle(angle);
        b.setSpeed(speed);
    }

    public void ballRelocated(Ball b, Point2D p) {
        b.setLocation(p);
    }

    public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {
//		if (a.getSpeed() > b.getSpeed())
//		{
//			ballSentIntoMotion(a, getImpactSpeed(a, b, true), a.getHorizontalSpeed());
//			ballSentIntoMotion(b, getImpactSpeed(a, b, false), b.getHorizontalSpeed());
//		}
//		else if (a.getSpeed() < b.getSpeed())
//		{
//			ballSentIntoMotion(a, getImpactSpeed(a, b, false), a.getHorizontalSpeed());
//			ballSentIntoMotion(b, getImpactSpeed(a, b, true), b.getHorizontalSpeed());
//		}
    }


    public void ballCollidedWithWall(Ball b, float speed, float angle) {
    }

    public void reflectY(Ball b) {
        float newAngle;
        float a = b.getAngle();
        if (a > (Math.PI / 2) && a < (3 * Math.PI / 2)) {
            newAngle = (float) ((Math.PI / 2) - (a - Math.PI / 2));
        } else {
            newAngle = (float) (a - (Math.PI / 2 - a));
        }
        b.setAngle(newAngle);
    }

    public float getImpactSpeed(Ball a, Ball b, boolean forMovingBall) {
        if (forMovingBall)
            return (float) (Math.max(a.getSpeed(), b.getSpeed()) * 0.75);
        else
            return (float) (Math.max(a.getSpeed(), b.getSpeed()) * 0.5);
    }

    public Point2D.Double reflection(int x1, int y1, int xi, int yi, double nx, double ny) {
        double rx, ry;
        double dot = (xi - x1) * nx + (yi - y1) * ny;
        rx = (xi - x1) - 2 * nx * dot;
        ry = (yi - y1) - 2 * ny * dot;
        return new Point2D.Double(rx, ry);
    }
}
/*    public static Vector2 Reflect(Ball vector, Ball normal)
    {
        return vector - 2 * Ball.Dot(vector, normal) * normal;
    }
What happens when the cue ball hits the carpet balls.
What happens when the carpet balls hit other carpet balls.
What happens When the balls hit the wall.
What happens to the ball when it gets thrown.
How much power the ball gets thrown with.
How much spin the ball has.
The angle the ball gets thrown.
What happens when the ball reaches the end of the board."Set velocity to 0"
 */