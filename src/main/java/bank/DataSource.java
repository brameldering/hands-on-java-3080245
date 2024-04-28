package bank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

  private static DataSource instance;
  private Connection connection;

  private DataSource() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("Connected to database");
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }

  public static DataSource getInstance() {
    if (instance == null) {
      instance = new DataSource();
    }
    return instance;
  }

  public Connection getConnection() {
    return connection;
  }

  public Customer getCustomer(String username) {
    String sql = "select * from customers where username = ?";
    Customer customer = null;

    try (PreparedStatement statement = getConnection().prepareStatement(sql)) {

      statement.setString(1, username);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          customer = new Customer(resultSet.getInt("id"), 
          resultSet.getString("name"), 
          resultSet.getString("username"), 
          resultSet.getString("password"), 
          resultSet.getInt("account_id"));
        }
      }                 
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customer;
  }

public Account getAccount(int id) {
    String sql = "select * from accounts where id = ?";
    Account account = null;

    try (PreparedStatement statement = getConnection().prepareStatement(sql)) {

      statement.setInt(1, id);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          account = new Account(resultSet.getInt("id"), 
          resultSet.getString("type"), 
          resultSet.getDouble("balance"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return account;
  }

  public void updateAccountBalance(int accountId, double amount) {
    String sql = "update accounts set balance = ? where id = ?";
    try (PreparedStatement statement = getConnection().prepareStatement(sql)) {

      statement.setDouble(1, amount);
      statement.setInt(2, accountId);

      statement.executeUpdate();      
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}
