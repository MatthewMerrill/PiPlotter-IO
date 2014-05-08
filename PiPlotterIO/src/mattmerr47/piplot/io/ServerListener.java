package mattmerr47.piplot.io;

import mattmerr47.piplot.io.drawdata.DrawData;

public interface ServerListener {
	
	public void onCompletion(DrawData received);
	
}
