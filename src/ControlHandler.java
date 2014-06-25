import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
public class ControlHandler implements MouseListener, KeyListener {
    private GameState state;
    double velocity = 0;
    float rotation = 0;
    public ControlHandler(GameState state) {
        this.state = state;
    }
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            state.setSettingUp(false);
        }
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < 6; i++) {
            if (state.getMyBalls()[i].getLocation().getX() == e.getX() && state.getMyBalls()[i].getLocation().getY() == e.getY()) {
                state.getMyBalls()[i] = null;
            }
        }
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
        state.getCueBall().setVelocity((float) velocity);
        state.getCueBall().setRotation(rotation);
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
        if (e.getX() > 50 && e.getX() < 150 && e.getY() > 50 && e.getY() < 150) {
            state.getCueBall().setLocation(new Point2D.Float(e.getX(), e.getY()));
        }
    }
    public void mouseDragged(MouseEvent e) {
        if (e.getX() > 50 && e.getX() < 150 && e.getY() > 50 && e.getY() < 150) {
            if (state.isSettingUp()) {
                for (int i = 0; i < GameState.NUMBER_OF_BALLS_PER_PLAYER; i ++) {
                    double distance = Point2D.distance(state.getMyBalls()[i].getLocation().getX(), state.getMyBalls()[i].getLocation().getY(), e.getX(), e.getY());
                    if (distance < Ball.BALL_RADIUS) {
                        state.getMyBalls()[i].setLocation(new Point2D.Float (e.getX(), e.getY()));
                    }
                }
            } else {
                float mouseX = e.getX();
                float mouseY = e.getY();
                velocity = Math.sqrt(Math.pow((state.getCueBall().getLocation().getY() - mouseY), 2) + Math.pow((state.getCueBall().getLocation().getX() - mouseX), 2));
                rotation = (float)Math.atan((state.getCueBall().getLocation().getX() - mouseX) / (state.getCueBall().getLocation().getY() - mouseY));
            }
        }
    }
}