import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class GameState {

	public static final int NUMBER_OF_BALLS_PER_PLAYER = 6;

	private Ball[] myBalls;
	private Ball[] theirBalls;
    private Ball cueBall;
    private boolean myTurn;


    public GameState() {

    }

    public void reset(Table table){
        int r = Ball.BALL_RADIUS;


        myBalls = new Ball[NUMBER_OF_BALLS_PER_PLAYER];
        theirBalls = new Ball[NUMBER_OF_BALLS_PER_PLAYER];

        int number = 1;
        for (int i = 0; i < myBalls.length; i++) {
            Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            Point2D.Float location = new Point2D.Float(i * 2 * r + r, table.height - r);
            myBalls[i] = new Ball(number, c, location);
        }

        for (int i = 0; i < theirBalls.length; i++) {
            Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
            Point2D.Float location = new Point2D.Float(table.width-( i*2* r + r), r);
            myBalls[i] = new Ball(number, c, location);
        }
    }

    public Ball[] getMyBalls() {
        return myBalls;
    }

    public Ball[] getTheirBalls() {
        return theirBalls;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    private boolean settingUp;
    public boolean isSettingUp() {
        return settingUp;
    }
    public void setSettingUp(boolean settingUp) {
        this.settingUp = settingUp;
    }
    public Ball getCueBall() {
        return cueBall;
    }
    public void setCueBall(Ball cueBall) {
        this.cueBall = cueBall;
    }
}