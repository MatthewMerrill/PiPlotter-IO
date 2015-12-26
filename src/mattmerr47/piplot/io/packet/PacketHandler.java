package mattmerr47.piplot.io.packet;

import mattmerr47.piplot.io.Server;
import mattmerr47.piplot.io.plotter.IPenPlotter;

public class PacketHandler {

	private Server server;
	private IPenPlotter plotter;
	
	public PacketHandler(Server server, IPenPlotter plotter) {
		this.server = server;
		this.plotter = plotter;
	}
	
	public void handle(Packet packet) {
		
		if (packet instanceof ServerPacket) {
			((ServerPacket)packet).run(server);
		} else if (packet instanceof PlotterPacket) {
			((PlotterPacket)packet).run(plotter);
		}
		
	}
	

}
