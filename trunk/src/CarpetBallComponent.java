import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class CarpetBallComponent extends JComponent implements BallListener {
	private GameState state;
    private Table table;
    BufferedImage[] balls = new BufferedImage[13];

    public CarpetBallComponent(Table table, GameState state) {
        setFocusable(true);
        this.table = table;
        state.setSettingUp(true);
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int barTwo = (int)table.getHeight() - (int)table.getBarDistance() - 10;
//        g.setColor(new Color(200, 165, 80));
//        g.fillRect(0, 0, (int) table.getWidth(), (int) table.getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int)table.getWidth(), (int)table.getGutterDepth());
        g.fillRect(0, (int) table.getHeight() - 50, (int) table.getWidth(), (int) table.getGutterDepth());
        g.setColor(new Color(117, 117, 117));
        g.fillRect(0, (int)table.getGutterDepth(), (int)table.getWidth(), (int)table.getHeight() - (int)table.getGutterDepth() * 2);
        g.setColor(new Color(200, 165, 80));
        g.fillRect(0, (int) table.getBarDistance(), (int) table.getWidth(), 5);
        g.fillRect(0, barTwo, (int)table.getWidth(), 5);


        for (Ball ball : state.getMyBalls()) {
            drawBall(g2, ball);
        }
        for (Ball ball : state.getTheirBalls()){
            drawBall(g2, ball);
        }
        drawBall(g2, state.getCueBall());
        if (state.isMyTurn() && !state.isSettingUp()){
            g.setColor(Color.RED);
            g.drawString("YOUR", 95, (barTwo - (int)table.getBarDistance()) / 2 + (int)table.getBarDistance());
            g.drawString("TURN", 175, (barTwo - (int)table.getBarDistance()) / 2 + (int)table.getBarDistance());
        }
        if (state.isSettingUp()){
            g.setColor(Color.RED);
            g.drawString("SET", 95, (barTwo - (int)table.getBarDistance()) / 2 + (int)table.getBarDistance());
            g.drawString("UP", 175, (barTwo - (int)table.getBarDistance()) / 2 + (int)table.getBarDistance());
        }
    }
    private BufferedImage getBufferedImage(File input){
        try {
            return ImageIO.read(input);
        } catch (IOException e) {
            System.err.println(input.getAbsolutePath());
            e.printStackTrace();
        }
        return new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
    }
    private void drawBall(Graphics g, Ball b){
        Point2D loc = b.getLocation();
        int size = Ball.BALL_RADIUS;
        g.drawImage(balls[b.getNumber()], (int) loc.getX() - size, (int) loc.getY() - size, size * 2, size * 2, null);
    }


    // BallListener Implementation
    public void ballSentIntoMotion(Ball b, float speed, float angle) {

    }
    public void ballRelocated(Ball b, Point p) {

    }
    public void ballImpacted(Ball a, Ball b, Point impactPoint) {

    }
}