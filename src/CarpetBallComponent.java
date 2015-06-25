import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CarpetBallComponent extends JComponent {
	public static final int GUTTER_HEIGHT = 50;
	BufferedImage[] balls = new BufferedImage[13];
	private CarpetBall carpetBall;
	private CarpetBallFrame carpetBallFrame;


	public CarpetBallComponent(CarpetBall carpetBall) {
		this.carpetBall = carpetBall;

		Table table = carpetBall.getTable();

		setFocusable(true);
		setPreferredSize(new Dimension((int) table.getWidth(), (int) table.getHeight()));

		for (int x = 0; x < balls.length; x++) {
			balls[x] = getBufferedImage(new File(x + "Ball.png"));
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		GameState state = carpetBall.getState();
		Table table = carpetBall.getTable();

		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int barTwo = (int) table.getHeight() - (int) table.getBarDistance() - 11;
		//       g.setColor(new Color(200, 165, 80));
		//       g.fillRect(0, 0, (int) table.getWidth(), (int) table.getHeight());

		g.setColor(Color.BLACK);
		// top gutter
		g.fillRect(0, 0, (int) table.getWidth(), (int) table.getGutterDepth());
		// bottom gutter
		g.fillRect(0, (int) table.getHeight() - GUTTER_HEIGHT, (int) table.getWidth(), (int) table.getGutterDepth());

		//table segment (gray part)
		g.setColor(new Color(117, 117, 117));
		// sets table area to gray
		g.fillRect(0, (int) table.getGutterDepth(), (int) table.getWidth(), (int) table.getHeight() - (int) table.getGutterDepth() * 2);


//		g.setColor(new Color(200, 165, 80));
//		// top bar
//		g.fillRect(0, (int) table.getBarDistance(), (int) table.getWidth(), 5);
//		// bottom bar
//		g.fillRect(0, barTwo, (int) table.getWidth(), 5);


		for (Ball ball : state.getAllBalls()) {
			drawBall(g2, ball);
		}
		if (!state.isInGame()) {
			g.setColor(Color.RED);
			g.drawString("WAITING FOR PLAYER 2", 85, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance() - 20);
		} else {

			if (state.isMyTurn() && !state.isSettingUp()) {
				g.setColor(Color.RED);
				g.drawString("YOUR", 95, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
				g.drawString("TURN", 175, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
			}
			if (state.isSettingUp()) {
				g.setColor(Color.RED);
				g.drawString("SET", 95, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
				g.drawString("UP", 175, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
			}
			if (state.isTheirTurn() && !state.isMyTurn()){
				g.setColor(Color.RED);
				g.drawString("THEIR", 95, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
				g.drawString("TURN", 175, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
			}
			if (state.isEndOfGame()){
				g.setColor(Color.RED);
				g.drawString("THE", 95, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
				g.drawString("END", 175, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
				g.drawString("WINS", 85, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance() - 20);
			}

		}

		g.setColor(new Color(200, 165, 80));
		//top bar
		g.fillRect(0, (int) table.getBarDistance()+5, (int) table.getWidth(), 5);
		// bottom bar
		g.fillRect(0, barTwo, (int) table.getWidth(), 5);

	}

	private BufferedImage getBufferedImage(File input) {
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			System.err.println(input.getAbsolutePath());
			e.printStackTrace();
		}
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}

	private void drawBall(Graphics g, Ball b) {
		Point2D loc = b.getLocation();
		int size = (int) Ball.BALL_RADIUS;
		if (b.isHovered()) {
			g.setColor(Color.WHITE);
			g.fillOval((int) loc.getX() - size - 5, (int) loc.getY() - size - 5, (size + 5) * 2, (size + 5) * 2);
		}

		g.drawImage(balls[b.getNumber()], (int) loc.getX() - size, (int) loc.getY() - size, size * 2, size * 2, null);
	}


	public void play(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}