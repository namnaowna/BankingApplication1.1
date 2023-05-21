package bankingapplication1.pkg1;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {

    private String name;

    public Bank(String name) {
        this.name = name;
    }

    public void listAccounts() { //Query database
        Connection con = BankConnection.connect();
        Statement statement;
        try {
            statement = con.createStatement();
            String sql = "select * from account2";
            ResultSet results = statement.executeQuery(sql);
            while (results.next()) {
                System.out.println(results.getString(1) + " " + results.getString(2) + " " + results.getString(3)
                + " " + results.getString(4));
            }
            System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void openAccount(Account account) { // Insert database
        Connection con = BankConnection.connect();
        String sql = "insert into account2(accNumber,accName,accBalance,accType) values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.setString(4, account.getAccountType());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void closeAccount(Account account) { // Delete database
        Connection con = BankConnection.connect();
        String sql = "delete from account2 where accNumber = ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void depositMoney(Account account, double amount) { // Update database
        account.deposit(amount);
        Connection con = BankConnection.connect();
        String sql = "update account2 set accBalance = ? where accNumber = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void withdrawMoney(Account account, double amount) { // Update database
        account.withdraw(amount);
        Connection con = BankConnection.connect();
        String sql = "update account2 set accBalance = ? where accNumber = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
            System.out.println("Balance: " + account.getBalance());
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Account getAccount(int accountNumber) { //Query database
        Connection con = BankConnection.connect();
        Account account = null;
        String accountName = "";
        double balance = 0;
        String sql = "select * from account2 where accNumber = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);
            ResultSet results = preparedStatement.executeQuery();
                
                results.next();
                accountName = results.getString(2);
                balance = results.getDouble(3);
                String accType = results.getString(4);
                
            if (accType.equals("SavingAccount")) {
              account = new SavingAccount(accountNumber, accountName, balance);  
            }else if(accType.equals("CurrentAccount")) {
                account = new CurrentAccount(accountNumber, accountName, balance);
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }

        return account;

    }

}
