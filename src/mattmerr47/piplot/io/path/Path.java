package mattmerr47.piplot.io.path;

import java.io.Serializable;

import mattmerr47.piplot.io.PositionHelper;

public interface Path extends Serializable {
	
	public abstract void draw(PositionHelper posHelper);

}
