import java.awt.*;
import java.awt.event.*;
public class ControlHandler implements MouseListener, KeyListener {
    private GameState state;
    final Rectangle carpetBall = new Rectangle(75, 75, 10, 10);
    Rectangle border = new Rectangle(50, 50, 100, 100);
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
        state.getMyBalls()[0].setVelocity((float) velocity);
        state.getMyBalls()[0].setRotation(rotation);
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
        if (e.getX() > 50 && e.getX() < 150 && e.getY() > 50 && e.getY() < 150) {
            carpetBall.setLocation(e.getX(), e.getY());
        }
    }
    public void mouseDragged(MouseEvent e) {
        if (e.getX() > 50 && e.getX() < 150 && e.getY() > 50 && e.getY() < 150) {
            if (state.isSettingUp()) {
                carpetBall.setLocation(e.getX(), e.getY());
            } else {
                float mouseX = e.getX();
                float mouseY = e.getY();
                velocity = Math.sqrt(Math.pow((state.getCueBall().getLocation().getY() - mouseY), 2) + Math.pow((state.getCueBall().getLocation().getX() - mouseX), 2));
                rotation = (float)Math.atan((state.getCueBall().getLocation().getX() - mouseX) / (state.getCueBall().getLocation().getY() - mouseY));
            }
        }
    }
}
/*
1. Drag the balls into wanted position. (mouseDragged)
2. Enter "Ready" state. (keyTyped "Enter")
3. Choose position from where you want to throw the cue ball. (mouseMoved)
4. Set velocity and angle to shoot the ball at. (mouseDragged)
5. Throw the ball. (mouseReleased)
6. Sacrifice balls. (mouseClicked)
*/