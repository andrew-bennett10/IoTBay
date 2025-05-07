package classes.model.dao;

import classes.model.AccessLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AccessLogDBManager extends DBManager<AccessLog> {
//    public int getUserCount() throws SQLException {
//        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMERS");
//        resultSet.next();
//        return resultSet.getInt(1);
//    }

    public AccessLogDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    //CREATE
    public AccessLog add(AccessLog accessLog) throws SQLException {
        if (accessLog.getIsStaff()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STAFF_ACCESS_LOGS (StaffId, LoginDate, LogoutDate) VALUES (?, ?, ?)");
            preparedStatement.setString(1, String.valueOf(accessLog.getUserId()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String loginFormatted = sdf.format(accessLog.getLogin());
            String logoutFormatted = sdf.format(accessLog.getLogout());

            preparedStatement.setString(2, loginFormatted);
            preparedStatement.setString(3, logoutFormatted);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT MAX(StaffAccLogId) FROM STAFF_ACCESS_LOGS");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int accessLogId = resultSet.getInt(1);
//            int userId = resultSet.getInt(1);

            accessLog.setAccessLogId(accessLogId);
            return accessLog;
        }
//        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMER_ACCESS_LOGS (CustomerId, LoginDate, LogoutDate) VALUES (?, ?, ?)");
        else{



            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMER_ACCESS_LOGS (Cus, LoginDate, LogoutDate) VALUES (?, ?, ?)");
            preparedStatement.setString(1, accessLog.getUser().);
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getFName());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT MAX(StaffAccLogId) FROM STAFF_ACCESS_LOGS");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int accessLogId = resultSet.getInt(1);
//            int userId = resultSet.getInt(1);

            accessLog.setAccessLogId(accessLogId);
            return accessLog;
        }


//        return AccessLogDBManager;
    }


//        preparedStatement.setString(1, customer.getEmail());
//        preparedStatement.setString(2, customer.getPassword());
//        preparedStatement.setString(3, customer.getFName());
//        preparedStatement.setString(4, customer.getLName());
//        preparedStatement.setInt(5, customer.getAge());
//        preparedStatement.setString(6, customer.getAddress());
//        preparedStatement.setBoolean(7, customer.getRegistered());
//        preparedStatement.executeUpdate();
//
//        preparedStatement = connection.prepareStatement("SELECT MAX(CustomerId) FROM CUSTOMERS");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        int userId = resultSet.getInt(1);
//        customer.setId(userId);
//        return customer;
//    }

    public AccessLog get(AccessLog accessLog) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACCESS_LOGS WHERE UserID = ?");
        preparedStatement.setInt(1, customer.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getBoolean(8), false);

        return accessLog;
    }

//    public AccessLog get(Customer customer) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE CustomerId = ?");
//        preparedStatement.setInt(1, customer.getId());
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getBoolean(8), false);
//    }
//
//    public AccessLog get(String email, String password) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE email = ? AND password = ?");
//        preparedStatement.setString(1, email);
//        preparedStatement.setString(2, password);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        return new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getBoolean(8), false);
//    }
//
//    //UPDATE
    public void update(AccessLog oldAccessLog, AccessLog newAccessLog) throws SQLException {
        PreparedStatement preparedStatement = null;
//        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CUSTOMERS SET Email = ?, Password = ?, FName = ?, LName = ?, Age = ?, Address = ?, Registered = ? WHERE CustomerId = ?");
//        preparedStatement.setString(1, newCustomer.getEmail());
//        preparedStatement.setString(2, newCustomer.getPassword());
//        preparedStatement.setString(3, newCustomer.getFName());
//        preparedStatement.setString(4, newCustomer.getLName());
//        preparedStatement.setInt(5, newCustomer.getAge());
//        preparedStatement.setString(6, newCustomer.getAddress());
//        preparedStatement.setBoolean(7, newCustomer.getRegistered());
//        preparedStatement.setInt(8, oldCustomer.getId());
//        preparedStatement.executeUpdate();
    }
//
//    //DELETE
    public void delete(AccessLog accessLog) throws SQLException {
        PreparedStatement preparedStatement = null;
//        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE CustomerId = ?");
//        preparedStatement.setInt(1, customer.getId());
//        preparedStatement.executeUpdate();
    }

}