package mattmerr47.piplot.io.plotter;

public interface IStepperMotor {
	
	public double getStepsPerRev();
	public int getStepsMoved();
	
	public void turn(double degrees, double stepInterval);

}
