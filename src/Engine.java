import java.awt.geom.Point2D;

public abstract class Engine implements BallListener {
	protected Table table;
	protected GameState state;
	protected BallListener ballListener;
	public Engine(BallListener ballListener, GameState state, Table table) {
		this.ballListener = ballListener;
		this.state = state;
		this.table = table;
	}

	public abstract void tick();

	// BallListener implementation
	@Override
	public abstract void ballSentIntoMotion(Ball b, float speed, float angle);

	public abstract void ballRelocated(Ball b, Point2D p);

	public abstract void ballImpacted(Ball a, Ball b, Point2D impactPoint);

	public abstract void ballCollidedWithWall(Ball b, float speed, float angle);
}
