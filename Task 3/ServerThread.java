import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class ServerThread extends Thread {
	
	Socket socket;
	JTextArea console;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		Server.addUser(this);
	}
	
	public void run() {
		String input;
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			try {
				while ((input = br.readLine()) != null) {
					Server.print(input);
				}
				socket.close();
			} catch (IOException e) {
				Server.print("Server: User disconnected");
				e.printStackTrace();
			}
		} catch (IOException e1) {
			Server.print("error in get input stream");
			e1.printStackTrace();
		}
		
	}
	
	public void broadcast(String text) throws IOException {
		PrintStream printStream = new PrintStream(socket.getOutputStream());
		printStream.println(text);
	}
}
