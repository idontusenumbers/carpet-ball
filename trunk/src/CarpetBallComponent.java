import javax.swing.JComponent;
import java.awt.*;

public class CarpetBallComponent extends JComponent {


	private GameState state;
    private Table table;

    public CarpetBallComponent(Table table, GameState state) {
        this.table = table;
        setPreferredSize(new Dimension(300,650));
		this.state = state;
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(200, 165, 80));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        g.fillRect(5, 6, getWidth() - 10, 50);
        g.fillRect(5, getHeight() - 60, getWidth() - 10, 50);
        g.setColor (new Color (117, 117, 117));
        g.fillRect(5, 55, 300, 545);
        g.setColor(new Color(200, 165, 80));
        g.fillRect(0, 200, getWidth(), 5);
        g.fillRect(0, getHeight() - 210, getWidth(), 5);
    }
}
