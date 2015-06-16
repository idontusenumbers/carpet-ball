import java.awt.event.*;
import java.awt.geom.Point2D;

public class ControlHandler implements MouseListener, MouseMotionListener, KeyListener {
    private Table table;
    private GameState state;
    private BallListener ballListener;
    double velocity = 0;
    float angle = 0;
    Ball activeBall;

    public ControlHandler(Table table, GameState state, BallListener ballListener) {
        this.table = table;
        this.state = state;
        this.ballListener = ballListener;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (shouldAllowControl()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                state.setSettingUp(false);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (shouldAllowControl()) {

            Ball closestBall = null;
            float closestD = Float.MAX_VALUE;
            for (Ball ball : state.getMyBalls()) {
                float d = (float) ball.getLocation().distance(e.getPoint());
                if (d < closestD) {
                    closestBall = ball;
                    closestD = d;
                }
            }
            if (closestD < Ball.BALL_RADIUS)
                activeBall = closestBall;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (shouldAllowControl()) {
            activeBall = null;
            if (state.isSettingUp()) {

            } else if (canControlMyTurn()) {
                Ball cueBall = state.getCueBall();
                cueBall.setSpeed((float) velocity);
                cueBall.setAngle(angle);
                ballListener.ballSentIntoMotion(cueBall, (float) velocity, angle);
                state.setMyTurn(false);
            }
        }
    }

    private boolean shouldAllowControl() {
        //return true;
        return state.isInGame();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        if (shouldAllowControl()) {
            if (!state.isSettingUp() && canControlMyTurn() &&
                    e.getY() > table.getHeight() - table.getBarDistance()) {
                int x = (int) Math.max(Ball.BALL_RADIUS, Math.min(table.getWidth() - Ball.BALL_RADIUS, e.getX()));
                int y = (int) Math.max(table.getHeight() - table.getBarDistance(),
                        Math.min(table.getHeight()- table.getGutterDepth(), e.getY()));
                ballListener.ballRelocated(state.getCueBall(), new Point2D.Float(x, y));
            }
        }
    }

    private boolean canControlMyTurn() {
//        return true;
        return state.getCueBall().getSpeed() < 0.001  && state.isMyTurn();
    }

    public void mouseDragged(MouseEvent e) {
        if (shouldAllowControl()) {
            int mouseY = e.getY();
            int mouseX = e.getX();
            if (state.isSettingUp()) {
                for (int i = 0; i < GameState.NUMBER_OF_BALLS_PER_PLAYER; i++) {

                    if (activeBall != null &&
                            mouseY > table.getHeight() - table.getBarDistance() &&
                            mouseY < table.getHeight() - table.getGutterDepth() &&
                            mouseX > 0f && mouseX < table.getWidth()) {
                        ballListener.ballRelocated(activeBall, new Point2D.Float(mouseX, mouseY));
                        break;
                    }
                }
            } else if (state.isMyTurn()) {
                velocity = Point2D.distance(mouseX, mouseY, state.getCueBall().getLocation().getX(), state.getCueBall().getLocation().getY());
                double x = mouseX - state.getCueBall().getLocation().getX();
                double y = state.getCueBall().getLocation().getY() - mouseY;


                angle = (float) (Math.atan(Math.abs(y)/Math.abs(x)));
                if (x<0 && y >0)
                    angle= (float) (Math.PI - angle);
                if (x<0 && y<=0)
                    angle+=Math.PI;
                if (x>=0 && y<0)
                    angle= (float) (2* Math.PI - angle);
                System.out.println(angle);
            }
        }
    }
}