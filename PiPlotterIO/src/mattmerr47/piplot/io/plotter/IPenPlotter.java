package mattmerr47.piplot.io.plotter;

import mattmerr47.piplot.io.path.Point;

public interface IPenPlotter {
	
	public void gotoPosition(Point pos);
	public Point getPosition();
	
	public IStepperMotor[] getSteppers();
	
	public void setMarking(boolean penDown);
	public boolean getMarking();
	
	/**
	 * Distance between the wheel centers in inches.
	 */
	public double wheelDistance();
	/**
	 * Diameter of the wheels in inches.
	 */
	public double wheelDiameter();
	
	/**
	 * Width of the paper.
	 */
	public double paperWidth();
	/**
	 * Height of the paper.
	 */
	public double paperHeight();

	public double getXPad();
	public double getYPad();

}
