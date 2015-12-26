package mattmerr47.piplot.io.path;

import java.io.Serializable;

import mattmerr47.piplot.io.PositionHelper;

public class Line implements Path, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5465726814641184581L;
	public final Point from;
	public final Point to;
	
	public Line(Point from, Point to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	public void draw(PositionHelper posHelper) {
		posHelper.line(from, to, 20, 1/5.0);
	}

}
