import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarpetBall {
	public static void main(String[] args) {

		GameState state = new GameState();

        ControlHandler controlHandler = new ControlHandler(state);
		NetworkHandler networkHandler = new NetworkHandler(state);
		final Engine engine = new Engine(state);

        CarpetBallFrame frame = new CarpetBallFrame(state);
		CarpetBallComponent component = new CarpetBallComponent(state);

		frame.add(component);
		component.addMouseListener(controlHandler);
		component.addKeyListener(controlHandler);

		frame.setSize(400, 900);
		frame.setVisible(true);

        Timer timer = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.tick();
            }
        });


	}
}
