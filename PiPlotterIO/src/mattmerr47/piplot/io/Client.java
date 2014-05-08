package mattmerr47.piplot.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Client {
	
	private Socket socket;
	
	ArrayList<Object> toSend = new ArrayList<Object>();
	
	public Client() {
	}
	
	public void connectToServer() throws UnknownHostException, IOException {
		
			//File file = new File("D:/stuff/duck.svg");
			socket = new Socket(Server.IP, Server.PORT);
			/*OutputStream outputStream = socket.getOutputStream();
			byte [] buffer = new byte[(int)toSend.length()];
			FileInputStream fis = new FileInputStream(toSend);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(buffer,0,buffer.length);
			outputStream.write(buffer,0,buffer.length);
			outputStream.flush();
			
			bis.close();
			/*InputStreamEntity reqEntity = new InputStreamEntity(
					new FileInputStream(file), -1);
			reqEntity.setContentType("binary/octet-stream");
			reqEntity.setChunked(true); // Send in multiple parts if needed
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			//Do something with response...*/
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		
	}
	
	public void disconnectFromServer() throws IOException{
		socket.shutdownInput();
		socket.shutdownOutput();
		socket.close();
		
		socket = null;
	}
	
	public void addToQueue(Object obj){
		toSend.add(obj);
	}
	
	public boolean send = false;
	public void startSending() throws IOException{
		send = true;
		while (toSend.size() > 0) {
			
			if (!send)
				break;
			
			send(toSend.get(0));
			toSend.remove(0);
		}
	}
	
	public void stopSending() {
		send = false;
	}
	
	private void send(Object send) throws IOException {

		//OutputStream outputStream = socket.getOutputStream();
		/*byte [] buffer = new byte[(int)send.length()];
		FileInputStream fis = new FileInputStream(send);
		
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		bis.read(buffer,0,buffer.length);
		outputStream.write(buffer,0,buffer.length);
		outputStream.flush();
		*/
		
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(send);
		
		oos.flush();
		oos.close();
		
		//fis.close();
		//bis.close();
	}

}
