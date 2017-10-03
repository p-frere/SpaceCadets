import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class App {
	
	public String getInitials(String email){
            String initials = "";
            int atLocation = email.indexOf("@");    //Get the username from everything before @
            String username = email.substring(0, atLocation);

            //Loops through the chars in username and add them to the initials until you find a number
            for(char c : username.toCharArray()){
                    if(Character.isLetter(c)){
                            initials += c;
                    } else {
                            break;
                    }
            }

            return initials;	
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
			
			BufferedReader br = new BufferedReader(isr);
			
                        //Some emails wouldnt return the title line so i added this
                        //to loop through the whole source code and set 'line' to
                        //the line which contains "<title>"
                        
                        boolean loop = true;
                        while(loop){
                            line = br.readLine();
                            if(line.contains("<title>")){
                                loop = false;
                                break;
                            }
                        }
                        
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
		
                System.out.print("Please enter an ECS staff member's email:\t");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String input = null;
                try{
                    input = br.readLine();
                } catch (IOException e){
                    System.out.println("Input error...");
                    System.exit(0);
                }
            
		App app = new App();
		String initials = app.getInitials(input);
		String link = app.generateURL(initials);
		String line = app.getHTML(link);
		String name = app.getName(line);
		
		System.out.println("This e-mail belongs to: "+name);

	}

}

//By Phoebe Frere and Alex Shaw


