import javax.swing.JComponent;
import java.awt.*;

public class CarpetBallComponent extends JComponent {

	private GameState state;
	public CarpetBallComponent(GameState state) {
        setPreferredSize(new Dimension(300,700));
		this.state = state;
	}
}
