
package project1;

import java.sql.*;

public class DBaccess {
    
    public boolean checkPassword (String input, String password, Connection conn) throws SQLException, InterruptedException {
        
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Could not create the statement for some reason");
        }
        //Project1.handleSQLinjection(input);
        String sql = "select * from users where username ='"+input+"' and password = SHA1('"+password+"');";
        
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Could not create the result set for some reason");
            return false;
        }
        return rs.next();
        
    }
    
    public float getBalance (int i, Connection conn) throws SQLException {
        
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Could not create the statement for some reason");
        }
        
        String sql = "select * from accounts where user_id ="+i;
        
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Could not create the result set for some reason");
            return 0;
        }
        rs.next();
        return rs.getFloat("amount");
    }
    
    public int getID (int i, Connection conn) throws SQLException {
        
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Could not create the statement for some reason");
        }
        
        String sql = "select * from accounts where user_id ="+i+";";
        
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Could not create the result set for some reason");
            return 0;
        }
        rs.next();
        return rs.getInt("id");
    }
    
    public String getTransactionDate (int i, Connection conn) throws SQLException {
        
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Could not create the statement for some reason");
        }
        
        String sql = "select * from accounts where user_id ="+i;
        
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Could not create the result set for some reason");
            return "";
        }
        rs.next();
        return rs.getString("transaction_date");
    }
    
    public void addBalance (int i, float amount, String timeStamp, Connection conn) {
        
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Could not create the statement for some reason");
        }
        
        String sql = "UPDATE accounts SET amount=amount+"+amount+" WHERE id="+i+";";
        String sql2 = "UPDATE accounts SET transaction_date='"+timeStamp+"' WHERE id="+i+";";
        
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Could not create the result set for the balance change for some reason");
        }
        
        try {
            stmt.executeUpdate(sql2);
        } catch (SQLException ex) {
            System.out.println("Could not execute the statement for the timestamp update for some reason");
        }
    }
    
    public void removeBalance (int i, float amount, String timeStamp, Connection conn) {
        
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Could not create the statement for some reason");
        }
        
        String sql = "UPDATE accounts SET amount=amount-"+amount+" WHERE id="+i+";";
        String sql2 = "UPDATE accounts SET transaction_date='"+timeStamp+"' WHERE id="+i+";";
        
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Could not create the result set for the balance change for some reason");
        }
        
        try {
            stmt.executeUpdate(sql2);
        } catch (SQLException ex) {
            System.out.println("Could not execute the statement for the timestamp update for some reason");
        }
    }
    
    
}
