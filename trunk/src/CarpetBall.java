import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CarpetBall {
	public static void main(String[] args) throws IOException {

		Table table =  new Table(700f,300f,200f,50f);
        GameState state = new GameState();
        state.reset(table);

        final Engine engine = new Engine(state);

        final NetworkHandler networkHandler = new NetworkHandler(state, new BallListener(){
            public void ballMoved(Ball b, float speed, float angle) {
                engine.ballMoved(b,speed,angle);
            }
        });

        ControlHandler controlHandler = new ControlHandler(state, new BallListener() {
            public void ballMoved(Ball b, float speed, float angle) {
                engine.ballMoved(b,speed,angle);
                networkHandler.ballMoved(b,speed,angle);
            }
        });

        CarpetBallFrame frame = new CarpetBallFrame(state);
		final CarpetBallComponent component = new CarpetBallComponent(table, state);

        frame.setLayout(new GridBagLayout());
		frame.add(component, new GridBagConstraints(0, 0, 1, 1, 1, 1,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 1, 1), 1, 1));
        frame.pack();
        frame.getContentPane().setBackground(new Color (200, 165, 80));
        frame.setResizable(false);

		component.addMouseListener(controlHandler);
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
}
