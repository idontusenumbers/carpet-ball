import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class GameState {
	public static final int NUMBER_OF_BALLS_PER_PLAYER = 6;
	private Ball[] myBalls;
	private Ball[] theirBalls;
    private Ball cueBall;
    private boolean myTurn = false;
    private boolean inGame = false;
    private boolean settingUp = false;
    private boolean waiting = true;
    private boolean ready = false;
    private boolean theirTurn = false;
    private boolean endOfGame = false;


    private class InvalidStateException extends Exception {

        public InvalidStateException(String message) {
            super(message);
        }
    }

//    boolean connected = false;

    public GameState() {
    }
    public void reset(Table table){
        float r = Ball.BALL_RADIUS;

        myBalls = new Ball[NUMBER_OF_BALLS_PER_PLAYER];
        theirBalls = new Ball[NUMBER_OF_BALLS_PER_PLAYER];

        int number = 0;
        Point2D.Float location;

        location = new Point2D.Float(table.getWidth()/2, table.getHeight()/2);
        cueBall = new Ball(number, location);
        number++;

        for (int i = 0; i < myBalls.length; i++) {
            location = new Point2D.Float(i * 2 * r + r, table.getHeight() - r - table.getGutterDepth());
//            location = new Point2D.Float(table.getWidth()-( i*2* r + r), r + table.getGutterDepth());
            myBalls[i] = new Ball(number, location);
            number++;
        }

        for (int i = 0; i < theirBalls.length; i++) {
//            location = new Point2D.Float(i * 2 * r + r, table.getHeight() - r - table.getGutterDepth());
            location = new Point2D.Float(table.getWidth()-( i*2* r + r), r + table.getGutterDepth());
            theirBalls[i] = new Ball(number, location);
            number++;
        }
    }

    public Ball getBall(int ballNumber){
        if (ballNumber == 0)
            return cueBall;
        for (Ball b : myBalls) {
            if (b.getNumber() == ballNumber)
                return b;
        }
        for (Ball b : theirBalls) {
            if (b.getNumber() == ballNumber)
                return b;
        }
        throw new RuntimeException("Could not find ball: " + ballNumber);
    }
    public boolean isSettingUp() {
        return settingUp;
    }
    public void setSettingUp(boolean settingUp) {
        this.settingUp = settingUp;
    }
    public Ball getCueBall() {
        return cueBall;
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
        try {
            ensureOnlyOneStateActive();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    public boolean isWaiting(){
        return waiting;
    }
    public boolean isReady() {
        return ready;
    }
    public boolean isTheirTurn() {
        return theirTurn;
    }
    public boolean isEndOfGame() {
        return endOfGame;
    }
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
        try {
            ensureOnlyOneStateActive();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
        }
    public void setReady(boolean ready) {
        this.ready = ready;
        try {
            ensureOnlyOneStateActive();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }
    public void setTheirTurn(boolean theirTurn) {
        this.theirTurn = theirTurn;
        try {
            ensureOnlyOneStateActive();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }
    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
        try {
            ensureOnlyOneStateActive();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
    }


	public ArrayList<Ball> getAllBalls() {
		ArrayList<Ball> result = new ArrayList<Ball>(myBalls.length + theirBalls.length +1);
		Collections.addAll(result, myBalls);
		Collections.addAll(result, theirBalls);
		result.add(cueBall);
		return result;
	}

    private void ensureOnlyOneStateActive() throws InvalidStateException {
        int trueCount = 0;
        if (waiting) trueCount++;
        if (settingUp) trueCount++;
        if (ready) trueCount++;
        if (myTurn) trueCount++;
        if (theirTurn) trueCount++;
        if (endOfGame) trueCount++;

        if (trueCount > 1) {
            throw new InvalidStateException("Too many states are active!");
        } else if (trueCount < 1) {
            throw new InvalidStateException("Not enough states are active!");
        }

    }
}
