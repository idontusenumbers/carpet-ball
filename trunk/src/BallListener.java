import java.awt.geom.Point2D;
public interface BallListener {
    public void ballSentIntoMotion(Ball b, float speed, float angle);
    public void ballRelocated(Ball b, Point2D p);
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint);
}
