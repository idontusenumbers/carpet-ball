import debug.SimPanel;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.geom.Point2D;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * JBox2D has a cool settings constants .java file that we need velocityThreshold to be 0, it defaults to 1..
 */
public class BoxEngine extends Engine {

	private ControlHandler controlHandler;

	public static final int SIXTIETH_OF_A_SECOND = 1000 / 60;
	public static final int VELOCITY_ITERATIONS = 8;
	public static final int POSITION_ITERATIONS = 3;
	public static final float DISPLAY_PANEL_SCALE = 1f;
	public static final float SIM_SCALE = .3f;
	private static final float STEP_DISTANCE = 2.2f;
	public static final float WALL_WIDTH = 10f;
	Map<Ball, Body> ballToBody = new IdentityHashMap<Ball, Body>();
	Map<Body, Ball> bodyToBall = new IdentityHashMap<Body, Ball>();


	World world;
	Body wallBody;


	private SimPanel simPanel;
	private float carpetFriction;
	public BoxEngine(CarpetBall carpetBall, BallListener ballListener, float carpetFriction) {
		super(carpetBall, ballListener);
		this.carpetFriction = carpetFriction;
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
		Vec2 gravity = new Vec2(0, 0);
		world = new World(gravity);
		world.setContactListener(getContactListener());

		// create table
		float x = getTable().getWidth() / 2;
		float y = getTable().getHeight() / 2;
		BodyDef wallBodyDef = new BodyDef();
		wallBodyDef.type = BodyType.STATIC;
		ChainShape walls = new ChainShape();
		walls.createLoop(new Vec2[]{new Vec2(-x * SIM_SCALE, -y * SIM_SCALE), new Vec2(x * SIM_SCALE, -y * SIM_SCALE), new Vec2(x * SIM_SCALE, y * SIM_SCALE), new Vec2(-x * SIM_SCALE, y * SIM_SCALE)}, 4);
		FixtureDef wallFixture = new FixtureDef();
		wallFixture.shape = walls;
		wallFixture.restitution = 1;
		wallFixture.density = 1;
		wallBody = world.createBody(wallBodyDef);
		wallBody.createFixture(wallFixture);

		// TODO add one sided wall for gutters?


		// add balls
		for (Ball ball : getState().getAllBalls()) {
			BodyDef ballBodyDef = new BodyDef();
			CircleShape ballShape = new CircleShape();
			ballShape.m_radius = Ball.BALL_RADIUS * SIM_SCALE;
			ballBodyDef.type = BodyType.DYNAMIC;
			ballBodyDef.position.set(((float) ball.getLocation().getX() - getTable().getWidth() / 2) * SIM_SCALE, ((float) ball.getLocation().getY() - getTable().getHeight() / 2) * SIM_SCALE);
			ballBodyDef.userData = ball;
			ballBodyDef.linearDamping = carpetFriction;
			Body body = world.createBody(ballBodyDef);
			FixtureDef ballFixture = new FixtureDef();
			ballFixture.shape = ballShape;
			ballFixture.restitution = .5f;
			ballFixture.density = 1;
			ballFixture.friction = 0;
			body.createFixture(ballFixture);

			ballToBody.put(ball, body);
			bodyToBall.put(body, ball);
		}
	}
	private void createWall(Vec2 center, Vec2 bottomRight, Vec2 topLeft) {
		BodyDef wBodyDef = new BodyDef();
		wBodyDef.position.set(center);
		Body wBody = world.createBody(wBodyDef);
		EdgeShape wShape = new EdgeShape();
		FixtureDef wFixture = new FixtureDef();
		wFixture.shape = wShape;
		wShape.set(topLeft, bottomRight);
		wBody.createFixture(wFixture);
	}

	public void tick() {
		world.step(STEP_DISTANCE, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		if (simPanel != null)
			simPanel.repaint();
		for (Body b = world.getBodyList(); b != null; b = b.getNext()) {
			Transform t = b.getTransform();
			Vec2 pos = t.p;
			if (b.getUserData() instanceof Ball) {
				Ball ball = (Ball) b.getUserData();

				//	ball.setLocation(new Point2D.Float((pos.x / SIM_SCALE + getTable().getWidth() / 2), ((pos.y / SIM_SCALE + getTable().getHeight() / 2) * -1.0f) + getTable().getHeight()));
				// TODO update ball speed
				// TODO update ball angle


				//		ball.setLocation(new Point2D.Float(((pos.x / SIM_SCALE + getTable().getWidth() / 2) * -1.0f) + getTable().getWidth(),
				//										   ((pos.y / SIM_SCALE + getTable().getHeight() / 2) * -1.0f) + getTable().getHeight()));
				ball.setLocation(ball.getLocation());
			}
		}
	}


	// BallListener implementation
	public void ballSentIntoMotion(Ball b, float speed, float angle) {
		Body body = ballToBody.get(b);

		// TODO change from a fixed direction
		body.applyLinearImpulse(new Vec2(controlHandler.adjacent, controlHandler.opposite), new Vec2(5, 5));

		//b.setAngle(angle);
		//b.setSpeed(speed);
	}

	public void ballRelocated(Ball b, Point2D p) {
		ballToBody.get(b).setTransform(new Vec2((float) p.getX(), (float) p.getY()), 0f);
		b.setLocation(p);
	}
	public void ballImpacted(Ball a, Ball b, Point2D impactPoint) {}
	public void ballCollidedWithWall(Ball b) {}
	public ContactListener getContactListener() {
		return new ContactListener() {
			public void beginContact(Contact contact) {
				WorldManifold worldManifold = new WorldManifold();
				contact.getWorldManifold(worldManifold);

				Fixture a = contact.getFixtureA();
				Fixture b = contact.getFixtureB();

				handlePossibleBallColision(worldManifold, a, b);
				handlePossibleWallColision(worldManifold, a, b);


				// handle one sided gutters
				/*
				Fixture platformFixture = null;
				Fixture otherFixture = null;
				if (a == m_platformFixture) {
					platformFixture = a;
					otherFixture = b;
				} else if (b == m_platformFixture) {
					platformFixture = b;
					otherFixture = a;
				}

				if (platformFixture == null)
					return;

				Body platformBody = platformFixture.getBody();
				Body otherBody = otherFixture.getBody();

				int numPoints = contact.getManifold().pointCount;

				// check if contact points are moving downward
				for (int i = 0; i < numPoints; i++) {
					Vec2 pointVel =
							otherBody.getLinearVelocityFromWorldPoint(worldManifold.points[i]);
					if (pointVel.y < 0)
						return; // point is moving down, leave contact solid and exit
				}

				//no points are moving downward, contact should not be solid
				contact.setEnabled(false);*/
			}
			public void endContact(Contact contact) {
				contact.setEnabled(true);
			}
			public void preSolve(Contact contact, Manifold oldManifold) {

			}
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
		};
	}
	private void handlePossibleWallColision(WorldManifold worldManifold, Fixture a, Fixture b) {
		Ball ballA = bodyToBall.get(a.getBody());
		Ball ballB = bodyToBall.get(b.getBody());

		if (ballA != null && b.getBody() == wallBody ||
			ballB != null && a.getBody() == wallBody){

			getBallListener().ballCollidedWithWall(ballA != null ? ballA : ballB);
		}
	}
	public void handlePossibleBallColision(WorldManifold worldManifold, Fixture a, Fixture b) {
		Ball ballA = bodyToBall.get(a.getBody());
		Ball ballB = bodyToBall.get(b.getBody());
		if (ballA != null && ballB != null) {
			Vec2 p = worldManifold.points[0];
			getBallListener().ballImpacted(ballA, ballB, new Point2D.Float(p.x, p.y));
		}
	}
}
