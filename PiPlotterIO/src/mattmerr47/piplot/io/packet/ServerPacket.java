package mattmerr47.piplot.io.packet;

import mattmerr47.piplot.io.Server;

public abstract class ServerPacket extends Packet {
	
	public abstract void run(Server server);

}
