package debug;

import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SimPanel extends JPanel {
	private World world;
	private float scale;


	public SimPanel(World world, float scale) {
		this.world = world;
		this.scale = scale;
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(.5f * getWidth(), .5f * getHeight());

		for (Body b = world.getBodyList(); b != null; b = b.getNext()) {
			Transform t = b.getTransform();

			for (Fixture f = b.getFixtureList(); f != null; f = f.getNext()) {
				Shape shape = f.getShape();
				float r = shape.getRadius() * scale;
				float x = t.p.x * scale * -1;
				float y = (t.p.y * scale * -1);
				switch (shape.getType()) {
					case CIRCLE:
						g2.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));

						break;
					case EDGE:

						// TODO Probably buggy
						EdgeShape eShape = (EdgeShape) shape;
						float v1x = eShape.m_vertex1.x;
						float v1y = eShape.m_vertex1.y;
						float v2x = eShape.m_vertex2.x;
						float v2y = eShape.m_vertex2.y;
						g2.drawRect((int) (x + v1x), (int) (y + v1y), (int) (x + v2x), (int) (y + v2y));
						break;
					case CHAIN:
						ChainShape cShape = (ChainShape) shape;
						for (int i = 0; i < cShape.m_vertices.length - 1; i++) {
							Vec2 v1 = cShape.m_vertices[i];
							Vec2 v2 = cShape.m_vertices[i + 1];
							g2.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);
						}
						Vec2 v1 = cShape.m_vertices[cShape.m_vertices.length - 1];
						Vec2 v2 = cShape.m_vertices[0];
						g2.drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y);


						break;
					default:
						g2.drawRect((int) x, (int) y, (int) scale, (int) scale);
						break;
				}
//				if (!b.isActive()) {
//					this.color.set(0.5F, 0.5F, 0.3F);
//					this.drawShape(f, this.xf, this.color);
//				} else if (b.getType() == BodyType.STATIC) {
//					this.color.set(0.5F, 0.9F, 0.3F);
//					this.drawShape(f, this.xf, this.color);
//				} else if (b.getType() == BodyType.KINEMATIC) {
//					this.color.set(0.5F, 0.5F, 0.9F);
//					this.drawShape(f, this.xf, this.color);
//				} else if (!b.isAwake()) {
//					this.color.set(0.5F, 0.5F, 0.5F);
//					this.drawShape(f, this.xf, this.color);
//				} else {
//					this.color.set(0.9F, 0.7F, 0.7F);
//					this.drawShape(f, this.xf, this.color);
//				}
			}
		}
	}
}
