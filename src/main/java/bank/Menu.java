package bank;

import java.util.Scanner;

import bank.exceptions.AmountException;

public class Menu {
  private Scanner scanner;

  public static void main(String[] args) {
    
    clearConsole();
    System.out.println("Welcome");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if(customer != null) {
      Account account = DataSource.getInstance().getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your password");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
    return customer;
  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;

    while (selection !=4 && customer.isAuthenticated()) {
      // clearConsole();
      System.out.println("=====================================");
      System.out.println("Please select one of the following:");
      System.out.println("1: Deposit");
      System.out.println("2: Withdraw");
      System.out.println("3: Check balance");
      System.out.println("4: Exit");
      System.out.println("=====================================");

      selection = scanner.nextInt(); 
      Double amount = (double) 0;

      switch (selection) {
        case 1: 
          System.out.println("How much do you want to deposit");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
          }
          break;
        case 2: 
          System.out.println("How much do you want to withdraw");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            System.out.println(e.getMessage());
          }
          break;
        case 3: 
          System.out.println("Your balance is: " + account.getBalance());
          break;
        case 4: 
          System.out.println("Exiting...");
          Authenticator.Logout(customer);
          break;
        default:
          System.out.println("Invalid option");
          break;
      }
      System.out.println();
    }
  }

  private final static void clearConsole() {  
    System.out.print("\033[H\033[2J"); 
    System.out.flush(); 
  }  
}
