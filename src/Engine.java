import java.awt.geom.Point2D;

public abstract class Engine implements BallListener {

	private CarpetBall carpetBall;
	private BallListener ballListener;
	public Engine(CarpetBall carpetBall, BallListener ballListener) {

		this.carpetBall = carpetBall;
		this.ballListener = ballListener;
	}

	public GameState getState() {return carpetBall.getState();}
	public Engine getEngine() {return carpetBall.getEngine();}
	public NetworkHandler getNetworkHandler() {return carpetBall.getNetworkHandler();}
	public Table getTable() {return carpetBall.getTable();}
	public CarpetBall getCarpetBall() {
		return carpetBall;

	}
	public abstract void tick();

	// BallListener implementation
	@Override
	public abstract void ballSentIntoMotion(Ball b, float speed, float angle);

	public abstract void ballRelocated(Ball b, Point2D p);

	public abstract void ballImpacted(Ball a, Ball b, Point2D impactPoint);

	public abstract void ballCollidedWithWall(Ball b, float speed, float angle);
	public BallListener getBallListener() {
		return ballListener;
	}
}
