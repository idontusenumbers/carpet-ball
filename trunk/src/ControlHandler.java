import java.awt.*;
import java.awt.event.*;
public class ControlHandler implements MouseListener, KeyListener {
    private GameState state;
    final Rectangle carpetBall = new Rectangle(75, 75, 10, 10);
    Rectangle border = new Rectangle(50, 50, 100, 100);
    int velocity = 0;
    public ControlHandler(GameState state) {
        this.state = state;
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent mouseEvent) {
        if (mouseEvent.getX() > 50 && mouseEvent.getX() < 150 && mouseEvent.getY() > 50 && mouseEvent.getY() < 150) {
            carpetBall.setLocation(mouseEvent.getX(), mouseEvent.getY());
        }
    }
    public void mouseDragged(MouseEvent qwerty) {
    }
}