import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarpetBall {
	public static void main(String[] args) {

		Table table =  new Table(700f,300f,200f,25f);
        GameState state = new GameState();
        state.reset(table);

        ControlHandler controlHandler = new ControlHandler(state);
		NetworkHandler networkHandler = new NetworkHandler(state);
		final Engine engine = new Engine(state);

        CarpetBallFrame frame = new CarpetBallFrame(state);
		CarpetBallComponent component = new CarpetBallComponent(table, state);

        frame.setLayout(new BorderLayout());
		frame.add(component, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);

		component.addMouseListener(controlHandler);
		component.addKeyListener(controlHandler);
        
		frame.setVisible(true);

        Timer timer = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.tick();
            }
        });


	}
}
