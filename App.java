import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class App {
	
	public String getInitials(String email){
		int atLocation = email.indexOf("@");    //Get the username from everything before @
		String username = email.substring(0, atLocation);
			
		return username;	
	}

	public String generateURL(String initials){
		String link = "http://www.ecs.soton.ac.uk/people/" + initials;		
		return link; 
	}
	
	
	public String getHTML(String adress)
	{
		String line = null;
		
		try {
			URL url = new URL(adress);
			
			InputStreamReader isr = new InputStreamReader(url.openStream());
			
			//cycles through 8 lines
			BufferedReader br = new BufferedReader(isr);
			for (int i = 0; i < 8; i++)
			{
				br.readLine();
			}
			//reads 9th line
			line = br.readLine();
			
			br.close();
			isr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Something is wrong with the URL");
		}	
		return line;
	}
	//extracts name from string of code
	public String getName(String line) {
		int end = line.indexOf("|");
		int start = line.indexOf(">");
		String name = line.substring(start+1,end);
		return name;
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		sc.close();
		
		App app = new App();
		String initials = app.getInitials(input);
		String link = app.generateURL(initials);
		String line = app.getHTML(link);
		String name = app.getName(line);
		
		System.out.println("This e-mail belongs to "+name);

	}

}

//By Phoebe Frere and Alex Shaw


