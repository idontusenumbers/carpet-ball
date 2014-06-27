import java.awt.event.*;
import java.awt.geom.Point2D;
public class ControlHandler implements MouseListener, MouseMotionListener, KeyListener {
    private Table table;
    private GameState state;
    private BallListener ballListener;
    double velocity = 0;
    float rotation = 0;
    Ball activeBall;
    public ControlHandler(Table table, GameState state, BallListener ballListener) {
        this.table = table;
        this.state = state;
        this.ballListener = ballListener;
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        // if (state.isInGame()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                state.setSettingUp(false);
            }
        // }
    }
    public void keyReleased(KeyEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
        // if (state.isInGame()) {

        Ball closestBall = null;
        float closestD = Float.MAX_VALUE;
        for (Ball ball : state.getMyBalls()) {
            float d = (float) ball.getLocation().distance(e.getPoint());
            if (d < closestD){
                closestBall = ball;
                closestD = d;
            }
        }
        if (closestD < Ball.BALL_RADIUS)
            activeBall = closestBall;
        // }
    }
    public void mouseReleased(MouseEvent e) {
        // if (state.isInGame()) {
            activeBall = null;
            if (state.isSettingUp()) {
            } else {
                state.getCueBall().setSpeed((float) velocity);
                state.getCueBall().setAngle(rotation);
                ballListener.ballSentIntoMotion(state.getCueBall(), (float) velocity, rotation);
            }
        // }
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
        // if (state.isInGame()) {
            if (e.getY() > 500f && !state.isSettingUp()) {
                ballListener.ballRelocated(state.getCueBall(), new Point2D.Float(e.getX(), e.getY()));
            }
        // }
    }
    public void mouseDragged(MouseEvent e) {
        // if (state.isInGame()) {
            int y = e.getY();
            int x = e.getX();
            if (state.isSettingUp()) {
                for (int i = 0; i < GameState.NUMBER_OF_BALLS_PER_PLAYER; i++) {

                    if (activeBall != null &&
                            y > table.getHeight()-table.getBarDistance() &&
                            y < table.getHeight()-table.getGutterDepth() &&
                            x > 0f && x < table.getWidth()) {
                        ballListener.ballRelocated(activeBall, new Point2D.Float(x, y));
                        break;
                    }
                }
            } else {

                velocity = Point2D.distance(x, y, state.getCueBall().getLocation().getX(), state.getCueBall().getLocation().getY());
                rotation = (float) Math.atan((state.getCueBall().getLocation().getX() - x) / (state.getCueBall().getLocation().getY() - y));
            }
        // }
    }
}