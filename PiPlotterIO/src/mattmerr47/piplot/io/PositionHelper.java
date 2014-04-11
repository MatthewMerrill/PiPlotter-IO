package mattmerr47.piplot.io;

import java.util.concurrent.CountDownLatch;

import mattmerr47.piplot.io.Logger.LEVEL;
import mattmerr47.piplot.io.path.Point;
import mattmerr47.piplot.io.plotter.IPenPlotter;
import mattmerr47.piplot.io.plotter.IStepperMotor;

public class PositionHelper {
	
	private final IPenPlotter plotter;
	
	private final IStepperMotor left;
	private final IStepperMotor right;
	
	private final Point posL;
	private final Point posR;
	
	private Point origin = new Point(0, 0);
	
	public PositionHelper(IPenPlotter plotter, IStepperMotor left, IStepperMotor right) {
		
		this.plotter = plotter;
		
		this.left = left;
		this.right = right;
		
		posL = new Point(-8, -6.125);
		posR = new Point(8, -6.125);
	}
	
	public Point getPenPosition() {
		double revsL = (left.getStepsMoved() / left.getStepsPerRev());
		double revsR = -(right.getStepsMoved() / right.getStepsPerRev());
		
		double dlengthL = (revsL * (plotter.wheelDiameter() * Math.PI));
		double dlengthR = (revsR * (plotter.wheelDiameter() * Math.PI));
		
		//Motor positions -> shifted to account for strings not meeting in same place.
		Point posL2 = new Point(posL.X + .75, posL.Y);
		Point posR2 = new Point(posR.X - .75, posR.Y);

		double b = posL2.dist(origin) + dlengthL;
		double a = posR2.dist(origin) + dlengthR;
		double c = posL2.dist(posR2);
		
		/*
		 * A____c____B
		 *  \   |   /
		 *  b\  |  /a
		 *	  \ | /
		 *	   \|/
		 *	    V
		 *	    C 
		 * 
		 * Law of cosines: a^2 = b^2 + c^2 - 2bc*cosA
		 * therefore: A = Arccos((a^2 - b^2 - c^2) / 2bc)
		 */
		
		double A = Math.acos( (a*a - b*b - c*c) / (-2*b*c) );
		
		//Results must be shifted because of the buffer between the motors and the eye offsets.
		double x = b * Math.cos(A) - 7.25;
		double y = b * Math.sin(A) - 6.125;
		return new Point(x, y);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CALCULATIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	
	public double[] calculateDegrees(Point from, Point to) {
		
		//Lengths at Starting Position
		Point leftEye = new Point(from.X - .75, from.Y);
		Point rightEye = new Point(from.X + .75, from.Y);
		
		double lengthLi = leftEye.dist(posL);
		double lengthRi = rightEye.dist(posR);
		
		
		//Lengths at Final Position
		leftEye = new Point(to.X - .75, to.Y);
		rightEye = new Point(to.X + .75, to.Y);	
		
		double lengthLf = leftEye.dist(posL);
		double lengthRf = rightEye.dist(posR);
		
		double degreesL = 360 * ((lengthLf - lengthLi) / (plotter.wheelDiameter() * Math.PI));
		double degreesR = -360 * ((lengthRf - lengthRi) / (plotter.wheelDiameter() * Math.PI));
		
		return new double[]{degreesL, degreesR};
	}
	
	public double[] calculateIntervals(Point from, Point to, double inchesPerSec) {
	
		double dist = from.dist(to);
		double time = dist / inchesPerSec;
		
		Logger.print(LEVEL.DEBUG_FINE, "going" +dist+" inches over a timeframe of "+time+" seconds; inchesPerSec = " + inchesPerSec + "???");
		
		double[] degrees = calculateDegrees(from, to);
		double[] steps = new double[2];
		double[] intervals = new double[2];
		
		steps[LEFT] = (degrees[LEFT]/360) * left.getStepsPerRev();
		steps[RIGHT] = (degrees[RIGHT]/360) * right.getStepsPerRev();
		
		intervals[LEFT] = Math.abs(time/steps[LEFT]) * 1000;
		intervals[RIGHT] = Math.abs(time/steps[RIGHT]) * 1000;
		
		return intervals;
	}

	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SHAPES / DRAWING COMPONENTS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void moveTo(Point to) {
		moveTo(to, .5);
	}
	
	public void moveTo(Point to, double inchesPerSec) {
		if (getPenPosition().dist(to) < 0.0001){
			System.out.println("SAME POINT!");
			return;
		}
		
		Point from = getPenPosition();
		
		Logger.print(LEVEL.DEBUG_ROUGH, "Moving from " + from + " to " + to + ".");
		
		final double[] degrees = calculateDegrees(from, to);
		final double[] intervals = calculateIntervals(from, to, inchesPerSec);
		
		final CountDownLatch startSignal = new CountDownLatch(1);
		final CountDownLatch doneSignal = new CountDownLatch(2);
		
		Thread t1 = new Thread(){
			public void run(){

				try {
					startSignal.await();
					
					Logger.print(LEVEL.DEBUG_FINE, "l:"+degrees[LEFT] + "degrees w/ interval:" + intervals[LEFT]);
					left.turn(degrees[LEFT], intervals[LEFT]);
					Logger.print(LEVEL.DEBUG_FINE, "[" + System.currentTimeMillis() + "] left done!");
					
					doneSignal.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}};
				
		Thread t2 = new Thread(){
			public void run(){
				try {
					startSignal.await();
					
					Logger.print(LEVEL.DEBUG_FINE, "r:"+degrees[RIGHT] + "degrees w/ interval:" + intervals[RIGHT]);
					right.turn(degrees[RIGHT], intervals[RIGHT]);
					Logger.print(LEVEL.DEBUG_FINE, "[" + System.currentTimeMillis() + "] right done!");
					
					doneSignal.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}};

		t1.start();
		t2.start(); 

		Logger.print(LEVEL.DEBUG_FINE, "\nall threads starting");
		startSignal.countDown();	  // let all threads proceed
				
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Logger.print(LEVEL.DEBUG_FINE, "done!\n\n");
	}
	
	public void line(Point to, double segsPerInch, double inchesPerSec) {
		line(null, to, segsPerInch, inchesPerSec);
	}
	
	public void line(Point from, Point to, double segsPerInch, double inchesPerSec) {
		
		if (from != null && getPenPosition().dist(from) > 0.0001) {
			System.out.println(getPenPosition() + "!=" + from);
			
			//plotter.setMarking(false);
			moveTo(from);
			//plotter.setMarking(false);
		}
		
		from = getPenPosition();
		
		double dx = to.X - from.X;
		double dy = to.Y - from.Y;
		double distance = Math.sqrt(dx*dx + dy*dy);
		
		for (double i = 1; i < distance * segsPerInch; i++) {
			
			double percentAlongLine = (i / (distance * segsPerInch)) * 100;
			Point segPos = Point.getPointAlongLine(from, to, percentAlongLine);
			
			moveTo(segPos, inchesPerSec);
		}
		moveTo(to, inchesPerSec);
		
	}
	
	public void sector(Point center, double radius, double startAngle, double centralAngle) {
		moveTo(Point.findPointOnCircle(center, radius, startAngle));
		
		double angleChange = (centralAngle >= 0)?1:-1;
		for (double i = startAngle; i <= (startAngle + centralAngle); i += angleChange) {
			this.moveTo(Point.findPointOnCircle(center, radius, i));
		}
	}
	
	public void circle(Point center, double radius) {
		sector(center, radius, 0, 360);
	}
	
	public void bezier(Point start, Point end, Point[] controls) {
		
		for (double t = 0; t <= 1000; t++) {
			line(Point.getCriticalPoint(start, end, controls, t/10), 1, .5);
		}
	}
	
	public void flower() {				
		
		moveTo(new Point(0, 6));
		
		double angle = 0;
		double distance = 8;
		
		boolean b = false;
		while (true){
			for (double i = 1; i < 8 * 10; i++) {
	
				double dx = 4 * Math.cos(Math.toRadians(angle));
				double dy = 4 * Math.sin(Math.toRadians(angle));
						
				Point from = new Point(0 - dx,6 - dy);
				Point to = new Point(0 + dx,6 + dy);
				
				double percentAlongLine = (i / (distance * 8)) * 100;
				Point segPos = Point.getPointAlongLine(b?from:to, b?to:from, percentAlongLine);
						
				moveTo(segPos, .25);
				angle+=.125;
			}
			b = !b;
		}
	}

}
