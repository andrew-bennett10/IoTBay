package classes.model.dao;

import classes.model.AccessLog;
import classes.model.Customer;
import classes.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AccessLogDBManager extends DBManager<AccessLog> {
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



            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMER_ACCESS_LOGS (CustomerId, LoginDate, LogoutDate) VALUES (?, ?, ?)");
            preparedStatement.setString(1, String.valueOf(accessLog.getUserId()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String loginFormatted = sdf.format(accessLog.getLogin());
            String logoutFormatted = sdf.format(accessLog.getLogout());

            preparedStatement.setString(2, loginFormatted);
            preparedStatement.setString(3, logoutFormatted);
            preparedStatement.executeUpdate();


            preparedStatement = connection.prepareStatement("SELECT MAX(CustomerAccLogId) FROM CUSTOMER_ACCESS_LOGS");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int accessLogId = resultSet.getInt(1);

            accessLog.setAccessLogId(accessLogId);
            return accessLog;
        }

    }



    public AccessLog get(AccessLog accessLog) throws SQLException {
        if (accessLog.getIsStaff()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STAFF_ACCESS_LOGS WHERE StaffId = ?");
            preparedStatement.setInt(1, accessLog.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date loginDate = sdf.parse(resultSet.getString(2));
                Date logoutDate = sdf.parse(resultSet.getString(3));
                return new AccessLog(resultSet.getInt(1), loginDate, logoutDate, true);

            }catch (Exception e) {
                e.printStackTrace();
            }





        } else {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMER_ACCESS_LOGS WHERE CustomerId = ?");
            preparedStatement.setInt(1, accessLog.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date loginDate = sdf.parse(resultSet.getString(2));
                Date logoutDate = sdf.parse(resultSet.getString(3));
                return new AccessLog(resultSet.getInt(1), loginDate, logoutDate, false);

//                return accessLog;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void update(AccessLog oldAccessLog, AccessLog newAccessLog) throws SQLException {
        PreparedStatement preparedStatement = null;
        // Does not need to be implemented - to satisfy abstract class.

    }

    public void delete(AccessLog accessLog) throws SQLException {
        PreparedStatement preparedStatement = null;
        // Does not need to be implemented - to satisfy abstract class.

    }

}