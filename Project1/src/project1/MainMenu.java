
package project1;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class MainMenu {
    
    private int choice;
    
    public void start (int userid) throws InterruptedException {
        
        if (userid==0) {
                
                choice=0;
                Scanner scanner = new Scanner(System.in);
                String input = "";
                
                while (choice==0) {
                    
                    System.out.println("Welcome, admin. Please select an option from the list below");
                    System.out.println("___________________________________________________________");
                    System.out.println("");
                    System.out.println("1. View your bank account's balance");
                    System.out.println("2. View the members' bank account");
                    System.out.println("3. Deposit to a member's bank account");
                    System.out.println("4. Withdraw from a member's bank account");
                    System.out.println("5. Save today's transactions to file and exit");
                    System.out.println("6. Exit without saving");
                    System.out.println("");
                    
                    input = scanner.nextLine();
                    try {
                            if (Integer.parseInt(input)>0 && Integer.parseInt(input)<7) {
                                choice = Integer.parseInt(input);
                                break;
                            }else {
                                System.out.println("Incorrect input (expecting 1-6). Please try again in 3 seconds.");
                                TimeUnit.SECONDS.sleep(3);
                                Project1.clearScreen();
                            }
                            
                    } catch(java.lang.NumberFormatException ex) {
                            System.out.println("Incorrect input (expecting number). Please try again in 3 seconds.");
                            TimeUnit.SECONDS.sleep(3);
                            Project1.clearScreen();
                    }
                }
                
            
        }
        else {
                choice=0;
                Scanner scanner = new Scanner(System.in);
                String input = "";
                
                while (choice==0) {
                    
                    System.out.println("Welcome, user" +userid + ". Please select an option from the list below");
                    System.out.println("___________________________________________________________");
                    System.out.println("");
                    System.out.println("1. View your bank account's balance");
                    System.out.println("2. Deposit to the Cooperative's bank account");
                    System.out.println("3. Deposit to a member's bank account");
                    System.out.println("4. Save today's transactions to file and exit");
                    System.out.println("5. Exit without saving");
                    System.out.println("");
                
                    input = scanner.nextLine();
                    try {
                            if (Integer.parseInt(input)>0 && Integer.parseInt(input)<6) {
                                choice = Integer.parseInt(input);
                                break;
                            }else {
                                System.out.println("Incorrect input (expecting 1-5). Please try again in 3 seconds.");
                                TimeUnit.SECONDS.sleep(3);
                                Project1.clearScreen();
                            }
                            
                    } catch(java.lang.NumberFormatException ex) {
                            System.out.println("Incorrect input (expecting number). Please try again in 3 seconds.");
                            TimeUnit.SECONDS.sleep(3);
                            Project1.clearScreen();
                    }
                    
                        
                }
                
        }
    
    }
    
    public int getChoice() {
        return this.choice;
    }

}