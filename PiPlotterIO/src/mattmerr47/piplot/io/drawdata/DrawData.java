package mattmerr47.piplot.io.drawdata;

import java.io.Serializable;

import mattmerr47.piplot.io.path.Path;

public abstract class DrawData implements Serializable {
	
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -3318439700661420627L;

	public abstract double getWidth();
	public abstract double getHeight();
	
	public abstract Path[] getPaths();
}
