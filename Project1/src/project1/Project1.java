
package project1;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Project1 {

    private static int userid;
    private static int choice;
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/afdemp_java_1";
 
    static final String USER = "newuser";
    static final String PASS = "newpassword";
    
    private static BankAccounts acc = new BankAccounts();
    private static FileAccess writer = new FileAccess();
    private static Login login = new Login();        
    private static MainMenu menu = new MainMenu();
    private static DBaccess db = new DBaccess();
    
    public static void main(String[] args) throws InterruptedException, IOException, SQLException {
        
        Locale.setDefault(new Locale("el", "GR"));
        
        Connection conn = null;
        
        try {    
            Class.forName(JDBC_DRIVER);
        }catch(Exception e) {e.printStackTrace();}
        
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(SQLException e) {
            System.out.println("Failed to create connection with database");
            System.exit(0);
        } 
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        
        String filepath0 = "/Statement_admin_"+timeStamp+".txt";
        File file0 = new File(System.getProperty("user.dir") + filepath0);
        
        String filepath1 = "/Statement_user1_"+timeStamp+".txt";
        File file1 = new File(System.getProperty("user.dir") + filepath1);

        String filepath2 = "/Statement_user2_"+timeStamp+".txt";
        File file2 = new File(System.getProperty("user.dir") + filepath2);
        
        clearScreen();
        
        login.start(conn);
        userid = login.getID();
        
        while(true) {
            
            clearScreen();
            menu.start(userid);

            choice = menu.getChoice();
            
            String line = "";
            
            switch (choice) {
                
                case 1: {
                    acc.showOwnAccount(userid, conn);
                    break;
                }

                case 2:{
                    if (userid==0) acc.showUserAccounts(conn);
                    else acc.userDepositToCoop(userid,writer,conn);
                    break;
                }

                case 3: {
                    if (userid==0) acc.adminDeposit(0, writer, conn);
                    else acc.userDepositToUser(userid,writer,conn);
                    break;
                }
                
                case 4: {
                    if (userid==0) acc.adminWithdraw(0, writer,conn);
                    else if (userid==1) {
                        conn.close();
                        writer.saveandexit(file1);
                    }
                    else {
                        conn.close();
                        writer.saveandexit(file2);
                    }
                    break;
                }

                case 5: {
                    conn.close();
                    if (userid==0) {
                        writer.saveandexit(file0);
                    }
                    else {
                        writer.exit();
                    }
                    break;
                    
                }
                case 6: {
                    conn.close();
                    writer.exit();
                    break;
                }
            }

            System.out.println("Press any key then ENTER to return to the main menu");
            Scanner scanner = new Scanner(System.in);
            String anykey = scanner.next();
        }
    }
      
    
    
    public static void clearScreen(){
    //Code imported from https://stackoverflow.com/questions/2979383/java-clear-the-console
    try {
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }  
    
}
