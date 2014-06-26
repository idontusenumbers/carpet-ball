import java.awt.*;

/**
 * Created by Charlie Hayes on 6/26/2014.
 */
public interface BallListener {
    public void ballSentIntoMotion(Ball b, float speed, float angle);
    public void ballRelocated(Ball b, Point p);
    public void ballImpacted(Ball a, Ball b, Point impactPoint);
}
