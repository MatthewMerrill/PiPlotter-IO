package mattmerr47.piplot.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import mattmerr47.piplot.io.drawdata.DrawData;
import mattmerr47.piplot.io.packet.Packet;
import mattmerr47.piplot.io.packet.PacketHandler;
import mattmerr47.piplot.io.plotter.IPenPlotter;

public class Server {
	
	public static final String IP = "192.168.42.1";
	public static final int PORT = 470;

	private ServerSocket serverSocket;
	private boolean listening = false;
	
	private PrintWriter out;
	private OutputStream os;
	private InputStream in;
	
	private ServerListener listener;
	private PacketHandler packetHandler;
	
	public Server(ServerListener listener, IPenPlotter plotter) {
		this.listener = listener;
		this.packetHandler = new PacketHandler(this, plotter);
	}
	
	public void listenForClients() {

		if (listening)
			return;
		//
		boolean success = startServer();
		if (!success)
			return;
		
		listening = true;		
		while(listening) {
			
			Socket clientSocket;
			try {
				
				clientSocket = serverSocket.accept();
				System.out.println("found client.");
				
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = (clientSocket.getInputStream());
				
				ObjectInputStream ois = new ObjectInputStream(in);
				Object data = ois.readObject();
				ois.close();
				
				if (data instanceof DrawData) {
					listener.onCompletion((DrawData) data);
				} else if (data instanceof Packet) {
					packetHandler.handle((Packet) data);
				}
				out.println("you a'ight bruthah. you a'ight.");
				
				out.flush();
				out.close();
				in.close();
				clientSocket.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		closeServer();
		
		listening = false;
	}
	
	public void stopListening() {
		listening = false;
	}
	
	public boolean isListening() {
		return listening;
	}
	
	private boolean startServer() {
		if (serverSocket != null)
			return true;
		
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Listening at ip: " + serverSocket.getInetAddress());
			System.out.println("Local Socket Address: " + serverSocket.getLocalSocketAddress());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;		
	}
	
	private boolean closeServer() {

		if (serverSocket == null)
			return true;
		
		try {
			
			in.close();
			os.flush();
			os.close();
			
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void setServerListener(ServerListener serverListener) {
		
	}
	
}
