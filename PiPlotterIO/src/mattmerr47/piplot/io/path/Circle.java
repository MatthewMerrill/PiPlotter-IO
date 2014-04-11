package mattmerr47.piplot.io.path;

import mattmerr47.piplot.io.PositionHelper;

public class Circle implements Path {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5826037953798429280L;
	public final Point center;
	public final double radius;
	
	public Circle(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	@Override
	public void draw(PositionHelper posHelper) {
		posHelper.circle(center, radius);
	}

}
