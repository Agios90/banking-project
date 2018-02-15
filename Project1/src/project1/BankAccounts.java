
package project1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

public class BankAccounts {
    
    DBaccess db = new DBaccess();
    
    private int id;
    private String transaction_date;
    private float balance;
    
    @Override
    public String toString() { 
        
        if (this.id==0) return "Cooperative - Last transaction date: "+this.transaction_date+" - Balance: "+ NumberFormat.getInstance().format(this.balance);
        return "User"+this.id+" - Last transaction date: "+this.transaction_date+" - Balance: "+ NumberFormat.getInstance().format(this.balance);
        
    } 
    
    public String getAccount(int userid, Connection conn) throws SQLException {
        
        this.id=userid;
        this.transaction_date = db.getTransactionDate(userid+1, conn);
        this.balance = db.getBalance(userid+1, conn);
        String s = this.toString();
        return s;
        
    }
    
    public void showOwnAccount(int userid, Connection conn) throws SQLException {
        
        String s = getAccount(userid, conn);
        System.out.println(s);
        
    }
    
    public void showUserAccounts(Connection conn) throws SQLException {
        
        for (int i=1; i<3; i++) {
            showOwnAccount(i,conn);
        }
    }
    
    public void userDepositToUser(int userid, FileAccess writer, Connection conn) throws SQLException, InterruptedException {
        
        System.out.println("Please enter the amount you'd like to deposit:");
        float amount=0;
        int targetid = -1;
        Scanner scanner = new Scanner(System.in);
        
        try {
            amount = scanner.nextFloat();
        } catch(java.util.InputMismatchException ex) {
            System.out.println("Invalid input");
            return;
        }
        
        if (amount<0) {
            System.out.println("Amount must be positive.");
            return;
        }
        
        if (amount > db.getBalance(userid+1, conn)){
            System.out.println("The amount you have entered is higher than your balance.");
            return;
        }
        
        System.out.println("Showing a list of users you can deposit to:");
        if (userid==1){
            System.out.println("1. "+getAccount(2,conn));
        } else System.out.println("1. "+getAccount(1,conn));
        
        try {
            targetid= scanner.nextInt();
        } catch(java.util.InputMismatchException ex) {
            System.out.println("Invalid input");
            return;
        }
        
        if (!(targetid==1)) {
                System.out.println("User does not exist or you may not deposit to that user right now.");
                return;
        }
        if (userid==1){
            targetid=2;
        } else targetid=1;
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());  
        String timeStampForBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
        
        db.addBalance(targetid+1, amount, timeStamp, conn);
        db.removeBalance(userid+1, amount, timeStamp, conn);
        System.out.println("Deposited successfully.");
        
        String username = "User"+userid;
        if (userid==0) username="Admin";
        
        writer.addToBuffer(username+" deposited "+NumberFormat.getInstance().format(amount)+"€ to User"+targetid+" on "+timeStampForBuffer);
           
     }
    
    
    public void userDepositToCoop(int userid, FileAccess writer, Connection conn) throws SQLException {
        
        System.out.println("Please enter the amount you'd like to deposit to the Cooperative's account:");
        float amount=0;
        Scanner scanner = new Scanner(System.in);
        
        try {
            amount = scanner.nextFloat();
        } catch(java.util.InputMismatchException ex) {
            System.out.println("Invalid input");
            return;
        }
        
        if (amount<0) {
            System.out.println("Amount must be positive.");
            return;
        }
        
        if (amount > db.getBalance(userid+1, conn)){
            System.out.println("The amount you have entered is higher than your balance.");
            return;
        }
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String timeStampForBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
        
        db.addBalance(1, amount, timeStamp, conn);
        db.removeBalance(userid+1, amount, timeStamp, conn);
        
        String username = "User"+userid;
        if (userid==0) username="Admin";
        
        writer.addToBuffer(username +" deposited "+NumberFormat.getInstance().format(amount)+"€ to Admin on "+timeStampForBuffer);
        
    }
    
    public void adminDeposit(int targetid, FileAccess writer, Connection conn) throws SQLException, InterruptedException { 
        
        if (targetid==0) System.out.println("Showing the first user account to deposit to: ");
        else System.out.println("Showing the next user account to deposit to: ");
        targetid++;
        System.out.println(getAccount(targetid,conn));
                
        System.out.println("Please enter the amount you'd like to deposit:");
        float amount=0;
        Scanner scanner = new Scanner(System.in);
        
        try {
            amount = scanner.nextFloat();
        } catch(java.util.InputMismatchException ex) {
            System.out.println("Invalid input");
            return;
        }
        
        if (amount<0) {
            System.out.println("Amount must be positive.");
            return;
        }
        
        if (amount > db.getBalance(1, conn)){
            System.out.println("The amount you have entered is higher than the Cooperative's balance.");
            return;
        }
        
        System.out.println("Deposited successfully.");
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String timeStampForBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
        
        db.addBalance(targetid+1, amount, timeStamp, conn);
        db.removeBalance(1, amount, timeStamp, conn);
        
        writer.addToBuffer("Admin deposited "+NumberFormat.getInstance().format(amount)+"€ to User"+targetid+" on "+timeStampForBuffer);
        
        if (targetid==2) {System.out.println("Finished depositing to all users."); return; }
        else adminDeposit(targetid,writer,conn);
        
        
        
    }
    
    public void adminWithdraw(int targetid, FileAccess writer, Connection conn) throws SQLException, InterruptedException {
                
        if (targetid==0) System.out.println("Showing the first user account to withdraw from: ");
        else System.out.println("Showing the next user account to withdraw from: ");
        targetid++;
        System.out.println(getAccount(targetid,conn));
                
        System.out.println("Please enter the amount you'd like to withdraw:");
        float amount=0;
        Scanner scanner = new Scanner(System.in);
        
        try {
            amount = scanner.nextFloat();
        } catch(java.util.InputMismatchException ex) {
            System.out.println("Invalid input");
            return;
        }
        
        if (amount<0) {
            System.out.println("Amount must be positive.");
            return;
        }
        
        if (amount > db.getBalance(targetid+1, conn)){
            System.out.println("The amount you have entered is higher than user"+targetid+"'s balance.");
            return;
        }
        
        System.out.println("Withdrew successfully.");
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String timeStampForBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime());
        
        db.addBalance(1, amount, timeStamp, conn);
        db.removeBalance(targetid+1, amount, timeStamp, conn);
        
        writer.addToBuffer("Admin withdrew "+NumberFormat.getInstance().format(amount)+"€ from User"+targetid+" on "+timeStampForBuffer);
        
        if (targetid==2) {System.out.println("Finished withdrawing from all users."); return; }
        else adminWithdraw(targetid,writer,conn);
        
        }
        
    
    
   
}
