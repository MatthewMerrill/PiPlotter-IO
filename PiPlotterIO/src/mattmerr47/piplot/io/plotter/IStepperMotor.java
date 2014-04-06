package mattmerr47.piplot.io.plotter;

public interface IStepperMotor {
	
	public void turn(double degrees, double stepInterval);

	public double getStepsPerRev();

}
