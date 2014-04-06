package mattmerr47.piplot.io.drawdata;

import mattmerr47.piplot.io.path.PathComponent;
import mattmerr47.piplot.io.path.Point;

public class Line extends PathComponent {
	
	private final Point start;
	private final Point end;
	
	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public Point getStartPoint() {
		return start;
	}

	@Override
	public Point getEndPoint() {
		return end;
	}

	@Override
	public Point getPointAtPercent(double percent) {
		return Point.getPointAlongLine(getStartPoint(), getEndPoint(), percent);
	}

}
