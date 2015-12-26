package mattmerr47.piplot.io.drawdata;

import mattmerr47.piplot.io.path.Path;

public class PathData extends DrawData {
	
	private double width;
	private double height;
	
	private Path[] paths;
	
	public PathData(double width, double height, Path[] paths) {
		
		this.width = width;
		this.height = height;
		
		this.paths = paths;
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2701290994488651647L;

	@Override
	public double getWidth() {
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
