import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Arrays;

public class CarpetBall{
	public static boolean DEBUG_PHYSICS = false;
	private Engine engine;
	private NetworkHandler networkHandler;
	private ControlHandler controlHandler;
	private Table table;
	private GameState state;
	final CarpetBallFrame frame;

	private String localPlayerName;
	private String networkPlayerName;



	public CarpetBall() throws IOException {
		table = new Table(700f, 300f, 200f, 50f);
		state = new GameState();
		state.reset(table);
		state.setSettingUp(true);
		frame = new CarpetBallFrame(this);

		engine = new BoxEngine(new BallListener() {
			public void ballSentIntoMotion(Ball b, float speed, float angle) {
			}

			public void ballRelocated(Ball b, Point2D p) {
			}

			public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {

			}

			public void ballCollidedWithWall(Ball b, float speed, float angle) {

			}
		}, state, table);
		networkHandler = new NetworkHandler(table, state, new BallListener() {
			public void ballSentIntoMotion(Ball b, float speed, float angle) {
				engine.ballSentIntoMotion(b, speed, angle);
			}

			public void ballRelocated(Ball b, Point2D p) {
				engine.ballRelocated(b, p);
			}

			public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {

			}


			public void ballCollidedWithWall(Ball b, float speed, float angle) {
			}
		}, frame);

		controlHandler = new ControlHandler(table, state, new BallListener() {
			public void ballSentIntoMotion(Ball b, float speed, float angle) {
				engine.ballSentIntoMotion(b, speed, angle);
				networkHandler.ballSentIntoMotion(b, speed, angle);
			}

			public void ballRelocated(Ball b, Point2D p) {
				engine.ballRelocated(b, p);
				networkHandler.ballRelocated(b, p);
			}

			public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {
				engine.ballImpacted(a, b, impactPoint);
			}

			@Override
			public void ballCollidedWithWall(Ball b, float speed, float angle) {
			}
		});

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				networkHandler.shutdown();
			}
		});

		Timer timer = new Timer(1000 / 60, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				engine.tick();
				frame.repaint();


				if (DEBUG_PHYSICS) {
					state.setInGame(true);
					state.setSettingUp(false);
					state.setMyTurn(true);
				}

			}
		});
		timer.start();


	}

	public Engine getEngine() {
		return engine;
	}

	public NetworkHandler getNetworkHandler() {
		return networkHandler;
	}

	public ControlHandler getControlHandler() {
		return controlHandler;
	}

	public Table getTable() {
		return table;
	}

	public GameState getState() {
		return state;
	}

	public String getLocalPlayerName() {
		return localPlayerName;
	}

	public void setLocalPlayerName(String localPlayerName) {
		this.localPlayerName = localPlayerName;
		networkHandler.LocalPlayerNameChanged(localPlayerName);
	}

	public String getNetworkPlayerName() {
		return networkPlayerName;
	}

	public void setNetworkPlayerName(String networkPlayerName) {
		this.networkPlayerName = networkPlayerName;
		frame.NetworkPlayerNameChanged(networkPlayerName);
	}

	public static void main(String[] args) throws IOException {

		DEBUG_PHYSICS = Arrays.asList(args).contains("debugphysics");
		new CarpetBall();

	}
}
