
package project1;


import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.console;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.SQLException;


public class Login {
    
    private int userid;
    
    public void start(Connection conn) throws InterruptedException, IOException, SQLException {
                
                DBaccess db = new DBaccess();
                Console console = System.console();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                userid=-1;
                int attempts =0;
                
                while (attempts<3) {
                    
                    System.out.println("Enter your username:");
                    String input = br.readLine();
                    attempts++;
                    int printvar = 3-attempts;
                    
                    if (input.equals("admin")) {
                        char passwordArray[] = console.readPassword("Enter your password: ");
                        String pass = new String(passwordArray);
                        if (db.checkPassword(input,pass,conn)) {
                            userid =0;
                            break;
                        }
                        else {
                            if (!(attempts==3)) System.out.println("Wrong password, please try again. You have "+printvar+" attempts remaining");
                        }
                    }
                    else if (input.equals( "user1")) {
                        char passwordArray[] = console.readPassword("Enter your password: ");
                        String pass = new String(passwordArray);
                        if (db.checkPassword(input,pass,conn)) {
                            userid =1;
                            break;
                        }
                        else {
                            if (!(attempts==3)) System.out.println("Wrong password, please try again. You have "+printvar+" attempts remaining");
                        }
                    }
                    else if (input.equals("user2")) {
                        char passwordArray[] = console.readPassword("Enter your password: ");
                        String pass = new String(passwordArray);
                        if (db.checkPassword(input,pass,conn)) {
                            userid =2;
                            break;
                        }
                        else {
                            if (!(attempts==3)) System.out.println("Wrong password, please try again. You have "+printvar+" attempts remaining");
                        }
                    }
                    else {
                            if (!(attempts==3)) System.out.println("Wrong username, please try again. You have "+printvar+" attempts remaining");
                    }
                }
                if (attempts==3 && userid==-1)  {
                    System.out.println("Sorry, you have exceeded the number of times you're allowed to enter your username.");
                    System.out.println("Exiting...");
                    TimeUnit.SECONDS.sleep(2);
                    System.exit(0);
                }
    }
    
    public int getID() {
        return this.userid;
    }
    
  
}
