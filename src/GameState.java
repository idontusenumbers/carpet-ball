import java.awt.Color;
import java.awt.geom.Point2D;
public class GameState {
	public static final int NUMBER_OF_BALLS_PER_PLAYER = 6;
	private Ball[] myBalls;
	private Ball[] theirBalls;
    private Ball cueBall;
    private boolean myTurn = true;
    private boolean inGame = false;

    public GameState() {

    }
    public void reset(Table table){
        int r = Ball.BALL_RADIUS;


        myBalls = new Ball[NUMBER_OF_BALLS_PER_PLAYER];
        theirBalls = new Ball[NUMBER_OF_BALLS_PER_PLAYER];

        int number = 0;

        Point2D.Float location;

        location = new Point2D.Float(table.getWidth()/2, table.height/2);
        cueBall = new Ball(number, location);
        number++;

        for (int i = 0; i < myBalls.length; i++) {
            location = new Point2D.Float(i * 2 * r + r, table.height - r);
            myBalls[i] = new Ball(number, location);
            number++;
        }



        for (int i = 0; i < theirBalls.length; i++) {
            location = new Point2D.Float(table.width-( i*2* r + r), r);
            theirBalls[i] = new Ball(number, location);
            number++;
        }
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
    public Ball[] getMyBalls() {
        return myBalls;
    }
    public Ball[] getTheirBalls() {
        return theirBalls;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}