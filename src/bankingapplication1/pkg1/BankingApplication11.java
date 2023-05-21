package bankingapplication1.pkg1;

import java.util.Random;
import java.util.Scanner;

public class BankingApplication11 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Bank bank = new Bank("");
        int option = 0, accountNumber;
        Account account = null;
        String accountName, accountType;
        double balance, amount;
        while (option != 6) {
            System.out.println("Main Menu");
            System.out.println("1. Display All Accounts");
            System.out.println("2. Open New Account");
            System.out.println("3. Close Existing Account");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Exit");
            System.out.println();

            System.out.print("Enter your choice: ");
            option = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (option) {
                case 1:
                    bank.listAccounts();
                    break;
                case 2:
                    System.out.print("Enter Account Name: ");
                    accountName = scan.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    balance = scan.nextDouble();
                    accountNumber = generateAccountNumber();
                    scan.nextLine();

                    System.out.print("Enter Account Type: (s --> Saving Account) or c --> Current Account): ");
                    accountType = scan.nextLine();
                    if (accountType.toLowerCase().equals("s")) {
                        account = new SavingAccount(accountNumber, accountName, balance);
                    }else if (accountType.toLowerCase().equals("c")) {
                        account = new CurrentAccount(accountNumber, accountName, balance);
                    }
                    bank.openAccount(account);
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scan.nextInt();
                    account = bank.getAccount(accountNumber);
                    bank.closeAccount(account);
                    System.out.println("Account is Deleted");
                    System.out.println();
                    break;
                case 4:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scan.nextInt();
                    System.out.print("Enter Amount: ");
                    amount = scan.nextDouble();
                    account = bank.getAccount(accountNumber);
                    bank.depositMoney(account, amount);
                    break;
                case 5:
                    System.out.print("Enter Account Number: ");
                    accountNumber = scan.nextInt();
                    System.out.print("Enter Amount: ");
                    amount = scan.nextDouble();
                    account = bank.getAccount(accountNumber);
                    bank.withdrawMoney(account, amount);

                    break;

            }
            System.out.println();
        }
    }

    // Random Account Number
    public static int generateAccountNumber() {
        Random random = new Random();
        int accNumber = 100_000 + random.nextInt(900_000);
        return accNumber;
    }

}
