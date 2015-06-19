import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class CarpetBallFrame extends JFrame {
	final CarpetBallComponent component;

	final JTextField nameField;
	final JButton nameButton;
	final JLabel namesP1;
	final JLabel namesP2;

	public CarpetBallFrame(CarpetBall carpetball) throws HeadlessException {
		super("Carpet Ball");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

//button is clicked
//text in Jtextfield is taken and namesP1 is set to that value
//when another player connects or inputs their name, namesP2 is set to that player's name
//when Jlabel Names P1 = JText field enter set up && Jlabel P2 != "" > enter setup (this goes in the carpet ball component)

		component = new CarpetBallComponent(carpetball);
		nameField = new JTextField("Name Here");
		nameButton = new JButton("Confirm Name");
		String NameP1 = null;
		namesP1 = new JLabel(NameP1);
		String NameP2 = null;
		namesP2 = new JLabel(NameP2);
		NameP1 = "";
		NameP2 = "";
		nameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText() != "Name Here") {
//					NameP1 = nameField.getText();
				}


			}
		});


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

	public void MouseClicked(MouseEvent event) {


	}

}
