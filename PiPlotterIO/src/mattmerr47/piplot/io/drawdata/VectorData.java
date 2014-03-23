package mattmerr47.piplot.io.drawdata;

import java.io.Serializable;

import mattmerr47.piplot.io.path.Path;

public class VectorData extends DrawData implements Serializable {

	private static final long serialVersionUID = -1984594188283594877L;
	
	private double width;
	private double height;
	
	private Path[] paths;
	
	VectorData( double height, double width, Path[] paths) {
		
	}
	
	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public Path[] getPaths() {
		return paths;
	}
	
	
	

}
