public class Table {
    private float height;
    private float width;
    private float barDistance;
    private float gutterDepth;

    public Table(float height, float width, float barDistance, float gutterDepth) {
        this.height = height;
        this.width = width;
        this.barDistance = barDistance;
        this.gutterDepth = gutterDepth;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth()  {
		return width;
	}

    public float getBarDistance() {
        return barDistance;
    }

    public float getGutterDepth() {
        return gutterDepth;
    }
}
