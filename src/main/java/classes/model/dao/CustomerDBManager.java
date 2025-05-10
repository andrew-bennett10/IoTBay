package classes.model.dao;

import classes.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDBManager extends DBManager<Customer> {
    public int getUserCount() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMERS");
        resultSet.next();
        return resultSet.getInt(1);
    }

    public CustomerDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    public Customer add(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMERS (Email, Password, FName, LName, Age, Address, Registered) VALUES (?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, customer.getEmail());
        preparedStatement.setString(2, customer.getPassword());
        preparedStatement.setString(3, customer.getFName());
        preparedStatement.setString(4, customer.getLName());
        preparedStatement.setInt(5, customer.getAge());
        preparedStatement.setString(6, customer.getAddress());
        preparedStatement.setBoolean(7, customer.getRegistered());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("SELECT MAX(CustomerId) FROM CUSTOMERS");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int userId = resultSet.getInt(1);
        customer.setId(userId);
        return customer;
    }

    public Customer get(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE CustomerId = ?");
        preparedStatement.setInt(1, customer.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getBoolean(8), false);
    }

    public Customer get(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE email = ? AND password = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getBoolean(8), false);
    }

    public void update(Customer oldCustomer, Customer newCustomer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CUSTOMERS SET Email = ?, Password = ?, FName = ?, LName = ?, Age = ?, Address = ?, Registered = ? WHERE CustomerId = ?");
        preparedStatement.setString(1, newCustomer.getEmail());
        preparedStatement.setString(2, newCustomer.getPassword());
        preparedStatement.setString(3, newCustomer.getFName());
        preparedStatement.setString(4, newCustomer.getLName());
        preparedStatement.setInt(5, newCustomer.getAge());
        preparedStatement.setString(6, newCustomer.getAddress());
        preparedStatement.setBoolean(7, newCustomer.getRegistered());
        preparedStatement.setInt(8, oldCustomer.getId());
        preparedStatement.executeUpdate();
    }

    public void delete(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE CustomerId = ?");
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.executeUpdate();
    }

    public Boolean checkEmailUsed(String email) throws SQLException {
        String sql = "SELECT 1 FROM CUSTOMERS WHERE email = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }
}