package mattmerr47.piplot.io.packet;

import mattmerr47.piplot.io.plotter.IPenPlotter;

public abstract class PlotterPacket extends Packet {
	
	public abstract void run(IPenPlotter plotter);

}
