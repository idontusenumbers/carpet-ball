import debug.SimPanel;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.geom.Point2D;

public class BoxEngine extends Engine {

	public static final int SIXTIETH_OF_A_SECOND = 1000 / 60;
	public static final int VELOCITY_ITERATIONS = 8;
	public static final int POSITION_ITERATIONS = 3;
	public static final float DISPLAY_PANEL_SCALE = 1f;
	public static final float SIM_SCALE = .3f;
	private static final float STEP_DISTANCE = 2.2f;
	public static final float WALL_WIDTH = 10f;


	World world;
	private SimPanel simPanel;
	public BoxEngine(BallListener ballListener, GameState state, Table table) {
		super(ballListener, state, table);
		setupSimulation();

		if (CarpetBall.DEBUG_PHYSICS) {
			final JFrame frame = new JFrame("SimTest");
			this.simPanel = new SimPanel(world, DISPLAY_PANEL_SCALE);
			final SimPanel simPanel = this.simPanel;
			frame.add(simPanel);

			frame.setSize(500, 900);
			frame.setLocation(600, 0);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}
	}
	private void setupSimulation() {
		Vec2 gravity = new Vec2(0, -6f);
		world = new World(gravity);



		// create table

		float x = table.getWidth() / 2;
		float y = table.getHeight() / 2;
		BodyDef wallBody = new BodyDef();
		ChainShape walls = new ChainShape();
		walls.createLoop(new Vec2[]{new Vec2(-x * SIM_SCALE, -y * SIM_SCALE), new Vec2(x * SIM_SCALE, -y * SIM_SCALE), new Vec2(x * SIM_SCALE, y * SIM_SCALE), new Vec2(-x * SIM_SCALE, y * SIM_SCALE)}, 4);
		world.createBody(wallBody).createFixture(walls, 0);

		// TODO add one sided wall for gutters?

		// add balls

		for (Ball ball : state.getAllBalls()) {
			BodyDef ballBody = new BodyDef();
			CircleShape ballShape = new CircleShape();
			ballShape.m_radius = Ball.BALL_RADIUS * SIM_SCALE;
			ballBody.type = BodyType.DYNAMIC;
			ballBody.position.set(((float) ball.getLocation().getX() - table.getWidth()/2) * SIM_SCALE, ((float) ball.getLocation().getY() - table.getHeight() /2) * SIM_SCALE);
			ballBody.userData = ball;
			world.createBody(ballBody).createFixture(ballShape, 1.0f);
		}
	}

	public void tick() {
		world.step(STEP_DISTANCE, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		if (simPanel != null)
			simPanel.repaint();
		for (Body b = world.getBodyList(); b != null; b = b.getNext()) {
			Transform t = b.getTransform();
			Vec2 pos = t.p;
			if (b.getUserData() instanceof Ball){
				Ball ball = (Ball) b.getUserData();

				ball.setLocation(new Point2D.Float((pos.x/SIM_SCALE + table.getWidth()/2), ((pos.y/SIM_SCALE  + table.getHeight()/2) * -1.0f)+ table.getHeight()));
			}
		}
	}


	public float getXComp(float mag, float angle) {
		if (angle < Math.PI / 2)
			return (float) (mag * Math.cos(angle));
		else if (angle < Math.PI)
			return (float) -(mag * (Math.cos(Math.PI - angle)));
		else if (angle < Math.PI * 1.5)
			return (float) -(mag * Math.cos(angle - Math.PI));
		else
			return (float) (mag * Math.cos(Math.PI * 2 - angle));
	}

	public float getYComp(float mag, float angle) {
		if (angle < Math.PI / 2)
			return (float) -(mag * Math.sin(angle));
		else if (angle < Math.PI)
			return (float) -(mag * Math.sin(Math.PI - angle));
		else if (angle < Math.PI * 1.5)
			return (float) (mag * Math.sin(angle - Math.PI));
		else
			return (float) (mag * Math.sin(Math.PI * 2 - angle));
	}


	// BallListener implementation
	public void ballSentIntoMotion(Ball b, float speed, float angle) {
		b.setAngle(angle);
		b.setSpeed(speed);
	}

	public void ballRelocated(Ball b, Point2D p) {
		b.setLocation(p);
	}
	public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {}
	public void ballCollidedWithWall(Ball b, float speed, float angle) {}
}
