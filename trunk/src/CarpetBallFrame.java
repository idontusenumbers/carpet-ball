import javax.swing.JFrame;
import java.awt.*;

public class CarpetBallFrame extends JFrame {
	private GameState state;
	public CarpetBallFrame(GameState state) throws HeadlessException {
		super("Carpet Ball");
		this.state = state;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
