import com.sun.xml.internal.bind.v2.runtime.NamespaceContext2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CarpetBallFrame extends JFrame implements GameListener {
	final CarpetBallComponent component;
	final GameState state = new GameState();



	JTextField nameField;
	JButton nameButton;
	JLabel namesP1;
	JLabel namesP2;
	JButton readyup;

	public CarpetBallFrame(final CarpetBall carpetball) throws HeadlessException {
		super("Carpet Ball");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

//button is clicked
//text in Jtextfield is taken and namesP1 is set to that value
//when another player connects or inputs their name, namesP2 is set to that player's name
//when Jlabel Names P1 = JText field enter set up && Jlabel P2 != "" > enter setup (this goes in the carpet ball component)

		component = new CarpetBallComponent(carpetball);
		nameField = new JTextField("Name Here");
		nameField.setMaximumSize(new Dimension(100,100));
		nameButton = new JButton("Confirm Name");
		namesP1 = new JLabel();
		namesP2 = new JLabel();
		readyup = new JButton("READY UP");





		nameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carpetball.setLocalPlayerName(nameField.getText());
				namesP1.setText(nameField.getText());
				carpetball.getNetworkHandler().NameChanged(namesP1.getText());
				nameButton.setVisible(false);
				readyup.setVisible(true);
				if (state.isSettingUp() == true) {
					;
				}

		readyup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    state.advancePhase(GamePhase.READY);
                } catch (GameState.InvalidStateException e1) {
                    e1.printStackTrace();
                }
                try {

                    carpetball.getNetworkHandler().sendReady();
                    System.out.println("sent ready");
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                readyup.setVisible(false);
//				nameButton.setVisible(true);
			}
		});


			}
		});

		setLayout(new GridBagLayout());
		add(nameField, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NORTH, new Insets(10, 10, 0, 10), 1, 1));
		add(nameButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NORTHWEST, new Insets(10, 10, 0, 10), 1, 1));
		add(component, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 1, 1));
		add(namesP1, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.SOUTHEAST, new Insets(10, 10, 0, 50), 1, 1));
		add(namesP2, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.SOUTHWEST, new Insets(10, 10, 0, 50), 1, 1));
		add(readyup, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NORTHWEST, new Insets(10, 10, 0, 10), 1, 1));
		readyup.setVisible(false);
		pack();
		getContentPane().setBackground(new Color(200, 165, 80));
		setResizable(false);


		setVisible(true);


	}
	public void addControlHandlerListeners(ControlHandler controlHandler) {
		component.addMouseListener(controlHandler);
		component.addMouseMotionListener(controlHandler);

	}

	@Override
	public void LocalPlayerNameChanged(String playerName) {
		// There's no need to update because that comes from the text box
	}

	@Override
	public void NetworkPlayerNameChanged(String playerName) {
		namesP2.setText(playerName);
	}
}
