import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {
	
	JTextArea console;
	JTextArea chat;
	JTextField usernameTxt;
	Socket socket = null;
	String name = "test";
	
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Client Started");
		
		Client client = new Client();
		client.initGUI();
		client.login();
		client.initSocket();
	}
	
	public void login() {
		name = Long.toString(System.currentTimeMillis());
	}
	
	public void initSocket() throws IOException {	
		try {
			socket = new Socket("127.0.0.1", 1342);
		} catch (IOException e) {
			print("socket thing");
			System.out.println("socket delceration error");
			e.printStackTrace();
		}	
				
		//accepting something from the server	
		Scanner serverScanner = new Scanner(socket.getInputStream());
		String temp;
		
		while(true) {
			temp = serverScanner.nextLine();
			print("\n"+temp);
		}
	}
	
	public void Send() {
		String text = read();
		
		//passes to the server
		PrintStream ps;
		try {
			ps = new PrintStream(socket.getOutputStream());
			ps.println(getName() + ": "+text);
			clear();
			
		} catch (IOException e) {
			print("stream thing");
			System.out.println("error in print stream");
			e.printStackTrace();
		}	
	}
		
	//Creates GUI
	public void initGUI() {
		JFrame frame = new JFrame("Client");
		JPanel panel = new JPanel();
		
		frame.add(panel);
		frame.setSize(500,600);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		
		JButton sendBtn = new JButton("Send");
		JLabel l = new JLabel("CLIENT");
		chat = new JTextArea("",20,30);
		console = new JTextArea("", 10,30);
		usernameTxt = new JTextField(6);
		
		//Sets Action for submit button 
	    sendBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	            Send();
	         }          
	      });
	    
	    panel.add(l);
	    panel.add(usernameTxt);
	    panel.add(chat);
	    panel.add(console);
	    panel.add(sendBtn);	
	    
	    frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String read() {
		return console.getText();
	}
	public void clear() {
		console.setText("");
	}
	public void print(String text) {
		chat.append(text);
	}
	public String getName() {
		return usernameTxt.getText();
	}
	
	
}
