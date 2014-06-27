import java.awt.*;
import java.awt.geom.Point2D;
/**
 * Created by Charlie Hayes on 6/26/2014.
 */
public interface BallListener {
    public void ballSentIntoMotion(Ball b, float speed, float angle);
    public void ballRelocated(Ball b, Point2D p);
    public void ballImpacted(Ball a, Ball b, Point2D impactPoint);
}
