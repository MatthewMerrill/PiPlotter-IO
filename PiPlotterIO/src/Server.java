import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	
	public static final String IP = "169.254.35.47";
	public static final int PORT = 470;

	private ServerSocket serverSocket;
	private boolean listening = false;
	
	private PrintWriter out;
	private OutputStream os;
	private InputStream in;
	
	public void listenForClients() {

		File file = new File("PiPlotter/recieved.svg");
		if (listening)
			return;
		
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
	            os = new FileOutputStream(file);
		        in = (clientSocket.getInputStream());
		        
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            //read from is to buffer
	            while((bytesRead = in.read(buffer)) !=-1){
	                os.write(buffer, 0, bytesRead);
	            }
	            out.println("you a'ight bruthah. you a'ight.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	
}
