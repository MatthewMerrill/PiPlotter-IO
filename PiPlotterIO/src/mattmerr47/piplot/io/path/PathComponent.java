package mattmerr47.piplot.io.path;

public abstract class PathComponent {
	
	public abstract Point getStartPoint();
	public abstract Point getEndPoint();
	
	public abstract Point getPointAtPercent(double percent);
	
	
	//TODO: length approximation
	//public abstract ;
	
	//public abstract double getLength();
	
	/**
	 * Points to draw lines between.
	 * @return points to draw lines between.
	 */
	//public abstract Point[] getPoints();

}
