import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.geom.Point2D;

public class CarpetBall {

    final CarpetBallComponent component;
    final Engine engine;
    final NetworkHandler networkHandler;
    final ControlHandler controlHandler;

    public CarpetBall() throws IOException {
        Table table =  new Table(700f,300f,200f,50f);
        GameState state = new GameState();
        state.reset(table);




        engine = new Engine(table, state, new BallListener(){
            public void ballSentIntoMotion(Ball b, float speed, float angle) {
            }

            public void ballRelocated(Ball b, Point2D p) {
            }

            public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {
                component.ballImpacted(a, b, impactPoint);
            }

            public void ballCollidedWithWall(Ball b, float speed, float angle) {

            }
        });
        component = new CarpetBallComponent(table, state);

        networkHandler = new NetworkHandler(table, state, new BallListener(){
            public void ballSentIntoMotion(Ball b, float speed, float angle) {
                engine.ballSentIntoMotion(b, speed, angle);
            }
            public void ballRelocated(Ball b, Point2D p) {
                engine.ballRelocated(b,p);
            }
            public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {
                component.ballImpacted(a, b, impactPoint);
            }


            public void ballCollidedWithWall(Ball b, float speed, float angle) {}
        });

        controlHandler = new ControlHandler(table, state, new BallListener() {
            public void ballSentIntoMotion(Ball b, float speed, float angle) {
                engine.ballSentIntoMotion(b, speed, angle);
                networkHandler.ballSentIntoMotion(b, speed, angle);
            }
            public void ballRelocated(Ball b, Point2D p) {
                engine.ballRelocated(b, p);
                networkHandler.ballRelocated(b, p);
            }
            public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {
                engine.ballImpacted(a, b, impactPoint);
            }

            @Override
            public void ballCollidedWithWall(Ball b, float speed, float angle) {}
        });


        CarpetBallFrame frame = new CarpetBallFrame(state);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                networkHandler.shutdown();
            }
        });

        frame.setLayout(new GridBagLayout());
        frame.add(component, new GridBagConstraints(0, 0, 1, 1, 1, 1,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 1, 1));
        frame.pack();
        frame.getContentPane().setBackground(new Color(200, 165, 80));
        frame.setResizable(false);

        component.addMouseListener(controlHandler);
        component.addMouseMotionListener(controlHandler);
        component.addKeyListener(controlHandler);
        frame.setVisible(true);

        Timer timer = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.tick();
                component.repaint();
            }
        });
        timer.start();

    }

    public static void main(String[] args) throws IOException {


        new CarpetBall();

	}
}
