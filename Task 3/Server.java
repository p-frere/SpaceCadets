import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Server {
	
	int PORT = 1342;
	static JTextArea console;
	Socket socket = null;
	static ArrayList<ServerThread> userlist = new ArrayList<ServerThread>();

	public static void main(String[] args) throws IOException {
			
		Server server = new Server();
		server.initGUI();
		server.initSocket();
				
	}
	
	public static void addUser(ServerThread st) {
		userlist.add(st);
	}
	
	/*public void read() throws IOException {
		String text;
		Scanner sc = new Scanner(socket.getInputStream());
		text = sc.nextLine();
		
		print(text);
		
	}*/
	
	public void initSocket() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		print("Server Started");
		
		while (true) {
			//server listens for connections
			socket = serverSocket.accept();		
			new ServerThread(socket).start();		
		}
		
	}
	
	public void initGUI() {
		JFrame frame = new JFrame("Server");
		JPanel panel = new JPanel();
		
		frame.add(panel);
		frame.setSize(500,600);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		JLabel l = new JLabel("SERVER");
		console = new JTextArea("", 30,30);
				
		panel.add(console);
		panel.add(l);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void print(String text) {
		console.append("\n"+text);
		for(ServerThread st : userlist){
            try {
				st.broadcast(text);
			} catch (IOException e) {
				System.out.println("can't relay info to all threads");
				e.printStackTrace();
			}
        }
		//ServerThread.broadcast(text);
		
	}

}
