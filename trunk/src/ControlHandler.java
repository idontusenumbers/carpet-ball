import java.awt.event.*;
import java.awt.geom.Point2D;
public class ControlHandler implements MouseListener, MouseMotionListener, KeyListener {
    private GameState state;
    private BallListener ballListener;
    double velocity = 0;
    float rotation = 0;
    Ball activeBall;
    public ControlHandler(GameState state, BallListener ballListener) {
        this.state = state;
        this.ballListener = ballListener;
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        if (state.isInGame()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                state.setSettingUp(false);
            }
        }
    }
    public void keyReleased(KeyEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
        if (state.isInGame()) {
            for (int i = 0; i < 6; i++) {
                if (Point2D.distance(state.getMyBalls()[i].getLocation().getX(), state.getMyBalls()[i].getLocation().getY(), e.getX(), e.getY()) < Ball.BALL_RADIUS && !state.isSettingUp()) {
                    state.getMyBalls()[i].setLocation(new Point2D.Float(150f, 675f));
                    break;
                }
            }
        }
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
        if (state.isInGame()) {
            if (state.isSettingUp()) {
                activeBall = null;
            } else {
                state.getCueBall().setSpeed((float) velocity);
                state.getCueBall().setAngle(rotation);
                ballListener.ballSentIntoMotion(state.getCueBall(), (float) velocity, rotation);
            }
        }
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
        if (state.isInGame()) {
            if (e.getY() > 500f && !state.isSettingUp()) {
                state.getCueBall().setLocation(new Point2D.Float(e.getX(), e.getY()));
            }
        }
    }
    public void mouseDragged(MouseEvent e) {
        if (state.isInGame()) {
            if (state.isSettingUp()) {
                for (int i = 0; i < GameState.NUMBER_OF_BALLS_PER_PLAYER; i++) {
                    double distance = Point2D.distance(state.getMyBalls()[i].getLocation().getX(), state.getMyBalls()[i].getLocation().getY(), e.getX(), e.getY());
                    if (distance < Ball.BALL_RADIUS && activeBall == null && e.getY() > 500f && e.getY() > 650f && e.getX() > 0f && e.getX() < 300f) {
                        activeBall = state.getMyBalls()[i];
                        activeBall.setLocation(new Point2D.Float(e.getX(), e.getY()));
                    } else if (activeBall != null && e.getY() > 500f && e.getY() > 650f && e.getX() > 0f && e.getX() < 300f) {
                        activeBall.setLocation(new Point2D.Float(e.getX(), e.getY()));
                    }
                    ballListener.ballRelocated(state.getCueBall(), new Point2D.Float(e.getX(), e.getY()));
                }
            } else {
                float mouseX = e.getX();
                float mouseY = e.getY();
                velocity = Math.sqrt(Math.pow((state.getCueBall().getLocation().getY() - mouseY), 2) + Math.pow((state.getCueBall().getLocation().getX() - mouseX), 2));
                rotation = (float) Math.atan((state.getCueBall().getLocation().getX() - mouseX) / (state.getCueBall().getLocation().getY() - mouseY));
            }
        }
    }
}