package mattmerr47.piplot.io.path;

import java.io.Serializable;

import mattmerr47.piplot.io.PositionHelper;

public class Bezier implements Path, Serializable {

	private static final long serialVersionUID = -501917553185457264L;
	public final Point from;
	public final Point to;
	
	public final Point[] controls;
	
	public Bezier(Point from, Point to, Point[] controls) {
		this.from = from;
		this.to = to;
		
		this.controls = controls;
	}
	
	@Override
	public void draw(PositionHelper posHelper) {
		posHelper.bezier(from, to, controls);
	}

}
