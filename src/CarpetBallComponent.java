import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class CarpetBallComponent extends JComponent {


	private GameState state;
    private Table table;
    BufferedImage[] balls = new BufferedImage[13];

    public CarpetBallComponent(Table table, GameState state) {
        this.table = table;
        setPreferredSize(new Dimension((int)table.getWidth(),(int)table.getHeight()));
		this.state = state;
        for (int x = 0; x < balls.length; x++){

            balls[x] = getBufferedImage(new File(x + "Ball.png"));
        }

	}

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g.setColor(new Color(200, 165, 80));
        g.fillRect(0, 0, (int) table.getWidth(), (int) table.getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(5, 6, (int)table.getWidth() - 10, (int)table.getGutterDepth());
        g.fillRect(5, (int) table.getHeight() - 60, (int) table.getWidth() - 10, (int) table.getGutterDepth());
        g.setColor(new Color(117, 117, 117));
        g.fillRect(5, (int)table.getGutterDepth(), (int)table.getWidth() - 10, (int)table.getHeight() - (int)table.getGutterDepth() * 2 - 10);
        g.setColor(new Color(200, 165, 80));
        g.fillRect(0, (int) table.getBarDistance(), (int) table.getWidth(), 5);
        g.fillRect(0, (int)table.getHeight() - (int)table.getBarDistance() - 10, (int)table.getWidth(), 5);


        for (Ball ball : state.getMyBalls()) {
            drawBall(g2, ball);
        }
        for (Ball ball : state.getTheirBalls()){
            drawBall(g2, ball);
        }
        drawBall(g2, state.getCueBall());

    }
    private BufferedImage getBufferedImage(File input){
        try {
            return ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
    }
    private void drawBall(Graphics g, Ball b){
        Point2D loc = b.getLocation();
        int size = Ball.BALL_RADIUS;
        g.drawImage(balls[b.getNumber()], (int)loc.getX(), (int)loc.getY(), size * 2, size * 2, null);
    }
}
