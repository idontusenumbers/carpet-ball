import javax.swing.JComponent;
import java.awt.*;

public class CarpetBallComponent extends JComponent {


	private GameState state;
    private Table table;

    public CarpetBallComponent(Table table, GameState state) {
        this.table = table;
        setPreferredSize(new Dimension((int)table.getWidth(),(int)table.getHeight()));
		this.state = state;
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(200, 165, 80));
        g.fillRect(0, 0, (int)table.getWidth(), (int)table.getHeight());

        g.setColor(Color.BLACK);
        g.fillRect(5, 6, (int)table.getWidth() - 10, (int)table.getGutterDepth());
        g.fillRect(5, (int)table.getHeight() - 60, (int)table.getWidth() - 10, (int)table.getGutterDepth());
        g.setColor (new Color (117, 117, 117));
        g.fillRect(5, (int)table.getGutterDepth(), (int)table.getWidth() - 10, (int)table.getHeight() - (int)table.getGutterDepth() * 2 - 10);
        g.setColor(new Color(200, 165, 80));
        g.fillRect(0, 200, (int)table.getWidth(), 5);
        g.fillRect(0, (int)table.getHeight() - 210, (int)table.getWidth(), 5);
    }
}
