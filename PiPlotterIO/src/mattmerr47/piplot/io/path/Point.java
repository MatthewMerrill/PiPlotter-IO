package mattmerr47.piplot.io.path;


public class Point {
	
	public final double X;
	public final double Y;

	public Point(double x, double y) {
		X = x;
		Y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Point) {
			Point p = (Point)obj;
			return (X == p.X) && (Y == p.Y);
		}
		
		return false;
	}
	
	@Override
	public String toString(){
		return "(" +X+ ", " +Y+ ")";
	}
	
	/**
	 * distance to another point
	 * @param point
	 * @return
	 */
	public double dist(Point point) {
		return dist(this, point);
	}
	
	/**
	 * distance between two points
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double dist(Point p1, Point p2) {
		double dx = p1.X - p2.X;
		double dy = p1.Y - p2.Y;
		return Math.sqrt( (dx*dx) + (dy*dy) );
	}
	
	/**
	 * Finds the point t% along the line starting at start and ending at end.
	 * Very helpful for drawing Beziers.
	 */
	public static Point getPointAlongLine(Point start, Point end, double t) {
		
		if (start == end)
			return start;
		
		
		double dx = ((end.X - start.X) * (t/100));
		double dy = ((end.Y - start.Y) * (t/100));
		
		double px = start.X + dx;
		double py = start.Y + dy;
		
		return new Point(px, py);
	}
	
	public static Point getCriticalPoint(Point start, Point end, Point[] controls, double t) {
		
		Point[] points = new Point[2 + controls.length];
		
		points[0] = start;
		
		for (int i = 1; i <= controls.length; i++)
			points[i] = controls[i-1];
		
		points[points.length-1] = end; 

		//Recursively use the critical points to find the next level critical points until 1 remains.
		while (points.length != 1) {
			points = getCriticalPoints(points, t);
		}
		
		return points[0];
	}
	
	public static Point[] getCriticalPoints(Point[] points, double t) {
		
		Point[] points2 = new Point[points.length-1];
		
		//Take every pair and find the critical point.
		for (int i = 0; i < points.length-1; i++) {
			points2[i] = getPointAlongLine(points[i], points[i+1], t);
		}
		
		return points2;		
	}
	
	public static Point findPointOnCircle(Point center, double radius, double angle) {
		
		double startX = center.X + (radius * Math.cos(Math.toRadians(angle)));
		double startY = center.Y + (radius * Math.sin(Math.toRadians(angle)));
		
		return new Point(startX, startY);
	}
}
