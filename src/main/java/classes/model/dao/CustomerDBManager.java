package classes.model.dao;

import classes.model.Customer;
//import classes.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDBManager extends DBManager<Customer> {
    public int getUserCount() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMERS");
        resultSet.next();
        return resultSet.getInt(1);
    }

    public CustomerDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    //CREATE
    public Customer add(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMERS (Email, Password, FName, LName, Age, Address, Registered, PhoneNumber, IsActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, customer.getEmail());
        preparedStatement.setString(2, customer.getPassword());
        preparedStatement.setString(3, customer.getFName());
        preparedStatement.setString(4, customer.getLName());
        preparedStatement.setInt(5, customer.getAge());
        preparedStatement.setString(6, customer.getAddress());
        preparedStatement.setBoolean(7, customer.getRegistered());
        preparedStatement.setString(8, customer.getPhoneNumber());
        preparedStatement.setBoolean(9, customer.getActive());
        preparedStatement.executeUpdate();

        return customer;
    }



    public Customer get(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE CustomerId = ?");
        preparedStatement.setInt(1, customer.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getBoolean(8), false);
        }else {
            return null;
        }
    }

    public Customer get(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE email = ? AND password = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return new Customer(resultSet.getInt("CustomerId"),resultSet.getString("Email"), resultSet.getString("Password"), resultSet.getString("FName"), resultSet.getString("LName"), resultSet.getInt("Age"), resultSet.getString("Address"), resultSet.getBoolean("Registered"), resultSet.getString("PhoneNumber"), resultSet.getBoolean("IsActive"));
        } else{
            return null;
        }
    }

    public List<Customer> get(String firstName, String lastName, String phoneNumber) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM CUSTOMERS WHERE 1=1");

        if (firstName != null && !firstName.isEmpty()) {
            query.append(" AND FName LIKE ?");
        }
        if (lastName != null && !lastName.isEmpty()) {
            query.append(" AND LName LIKE ?");
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            query.append(" AND PhoneNumber LIKE ?");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query.toString());

        int index = 1;
        if (firstName != null && !firstName.isEmpty()) {
            preparedStatement.setString(index++, "%" + firstName.trim() + "%");
        }
        if (lastName != null && !lastName.isEmpty()) {
            preparedStatement.setString(index++, "%" + lastName.trim() + "%");
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            preparedStatement.setString(index++, "%" + phoneNumber.trim() + "%");
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getInt("CustomerId"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FName"),
                    resultSet.getString("LName"),
                    resultSet.getInt("Age"),
                    resultSet.getString("Address"),
                    resultSet.getBoolean("Registered"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getBoolean("IsActive")
            );
            customers.add(customer);
        }

        return customers;
    }

    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM CUSTOMERS";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getInt("CustomerId"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FName"),
                    resultSet.getString("LName"),
                    resultSet.getInt("Age"),
                    resultSet.getString("Address"),
                    resultSet.getBoolean("Registered"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getBoolean("IsActive")
            );
            customers.add(customer);
        }
        return customers;
    }

    public Customer getById(int userId) throws SQLException {
        String query = "SELECT * FROM CUSTOMERS WHERE CustomerId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Customer(
                    resultSet.getInt("CustomerId"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FName"),
                    resultSet.getString("LName"),
                    resultSet.getInt("Age"),
                    resultSet.getString("Address"),
                    resultSet.getBoolean("Registered"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getBoolean("IsActive")
            );
        }
        System.out.println("Can't find customers ");
        return null;
    }

    //UPDATE
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

    public void update(Customer customer) throws SQLException {
        String query = "UPDATE CUSTOMERS SET Email = ?, Password = ?, FName = ?, LName = ?, Age = ?, Address = ?, Registered = ?, PhoneNumber = ?, IsActive = ? WHERE CustomerId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, customer.getEmail());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getFName());
            ps.setString(4, customer.getLName());
            ps.setInt(5,customer.getAge());
            ps.setString(6, customer.getAddress());
            ps.setBoolean(7,customer.getRegistered());
            ps.setString(8, customer.getPhoneNumber());
            ps.setBoolean(9, customer.getActive());
            ps.setInt(10, customer.getId());
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //DELETE
    public void delete(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE CustomerId = ?");
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.executeUpdate();
    }

    public boolean existsEmail(String email) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM CUSTOMERS WHERE Email = ?");
        preparedStatement.setString(1,email);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        PreparedStatement preparedStatement_ = connection.prepareStatement("SELECT COUNT(*) FROM STAFF WHERE Email = ?");
        preparedStatement_.setString(1,email);
        ResultSet resultSet_ = preparedStatement_.executeQuery();
        return resultSet.getInt(1) > 0 || resultSet_.getInt(1) > 0;
    }

}