public class CarpetBall {
	public static void main(String[] args) {

		GameState state = new GameState();
		ControlHandler controlHandler = new ControlHandler(state);
		NetworkHandler networkHandler = new NetworkHandler(state);
		CarpetBallFrame frame = new CarpetBallFrame(state);
		CarpetBallComponent component = new CarpetBallComponent(state);

		frame.add(component);
		component.addMouseListener(controlHandler);
		component.addKeyListener(controlHandler);

		frame.setSize(400, 900);
		frame.setVisible(true);



	}
}
