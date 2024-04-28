package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

import bank.exceptions.AmountException;

public class Account {
  private int id;
  private String type;
  private double balance;

  private static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public Account (int id, String type, double balance) {
    setId(id);
    setType(type);
    setBalance(balance);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return round(this.balance, 2);
  }

  public void setBalance(double balance) {
    this.balance = round(balance, 2);;
  }

  public void deposit (double amount) throws AmountException {
    if(amount < 1) {
      throw new AmountException("The minimum deposit is 1.00");
    } else {
      double newBalance = round(this.balance + amount, 2);
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
      System.out.println("New Balance: " + newBalance);
    }
  }

  public void withdraw (double amount) throws AmountException {
    if(amount < 1) {
      throw new AmountException("The minimum withdraw amount is 1.00");
    } else if (amount > getBalance()) {
      throw new AmountException("Cannot withdraw more then current balance which is " + getBalance());
    } else {
      double newBalance = round(this.balance - amount, 2);
      setBalance(newBalance);
      DataSource.updateAccountBalance(id, newBalance);
      System.out.println("New Balance: " + newBalance);
    }
  }
} 
