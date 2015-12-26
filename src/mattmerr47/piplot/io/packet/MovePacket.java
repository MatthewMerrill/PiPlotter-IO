package mattmerr47.piplot.io.packet;

import mattmerr47.piplot.io.path.Point;
import mattmerr47.piplot.io.plotter.IPenPlotter;

public class MovePacket extends PlotterPacket {

	Point point;
	
	public MovePacket(Point point) {
		this.point = point;
	}
	
	@Override
	public void run(IPenPlotter plotter) {
		plotter.gotoPosition(point);
	}

}
