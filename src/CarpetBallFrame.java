import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

public class CarpetBallFrame extends JFrame {
	final CarpetBallComponent component;

	final JTextField nameField;
	final JButton nameButton;
	final JLabel namesP1;
	final JLabel namesP2;

	public CarpetBallFrame(CarpetBall carpetball) throws HeadlessException {
		super("Carpet Ball");
		setDefaultCloseOperation(EXIT_ON_CLOSE);



		component = new CarpetBallComponent(carpetball);
		nameField = new JTextField("Name Here");
		nameButton = new JButton("Confirm Name");
		namesP1 = new JLabel("player1");
		namesP2 = new JLabel("player2");

		setLayout(new GridBagLayout());
		add(nameField, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NORTHWEST, new Insets(10, 10, 0, 10), 1, 1));
		add(nameButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NORTHWEST, new Insets(10, 10, 0, 10), 1, 1));
		add(component, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 1, 1));
		add(namesP1, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.SOUTHEAST, new Insets(10, 10, 0, 50), 1, 1));
		add(namesP2, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.SOUTHWEST, new Insets(10, 10, 0, 50), 1, 1));
		pack();
		getContentPane().setBackground(new Color(200, 165, 80));
		setResizable(false);

		component.addMouseListener(carpetball.getControlHandler());
		component.addMouseMotionListener(carpetball.getControlHandler());
		component.addKeyListener(carpetball.getControlHandler());
		setVisible(true);




	}

}
