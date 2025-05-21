package classes.model.dao;

import classes.model.AccessLog;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


public class AccessLogDBManager extends DBManager<AccessLog> {

    public AccessLogDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    public AccessLog add(AccessLog accessLog) throws SQLException {
        if (accessLog.getIsStaff()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STAFF_ACCESS_LOGS (StaffId, LoginDate, LogoutDate) VALUES (?, ?, ?)");
            preparedStatement.setString(1, String.valueOf(accessLog.getUserId()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String loginFormatted = sdf.format(accessLog.getLogin());
            preparedStatement.setString(2, loginFormatted);
            if (accessLog.getLogout() != null){
                String logoutFormatted = sdf.format(accessLog.getLogout());
                preparedStatement.setString(3, logoutFormatted);
            }

            preparedStatement.executeUpdate();
            preparedStatement.close();

            PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT MAX(StaffAccLogId) FROM STAFF_ACCESS_LOGS");
            ResultSet resultSet = preparedStatement3.executeQuery();
            resultSet.next();
            int accessLogId = resultSet.getInt(1);

            accessLog.setAccessLogId(accessLogId);
            preparedStatement3.close();
            resultSet.close();
            return accessLog;
        }
        else{
            PreparedStatement preparedStatement4 = connection.prepareStatement("INSERT INTO CUSTOMER_ACCESS_LOGS (CustomerId, LoginDate, LogoutDate) VALUES (?, ?, ?)");
            preparedStatement4.setString(1, String.valueOf(accessLog.getUserId()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String loginFormatted = sdf.format(accessLog.getLogin());
            preparedStatement4.setString(2, loginFormatted);
            if (accessLog.getLogout() != null){

                String logoutFormatted = sdf.format(accessLog.getLogout());
                preparedStatement4.setString(3, logoutFormatted);
            }else {
                preparedStatement4.setNull(3, java.sql.Types.VARCHAR);
            }

            preparedStatement4.executeUpdate();
            preparedStatement4.close();

            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT MAX(CustomerAccLogId) FROM CUSTOMER_ACCESS_LOGS");
            ResultSet resultSet = preparedStatement2.executeQuery();
            resultSet.next();
            int accessLogId = resultSet.getInt(1);

            accessLog.setAccessLogId(accessLogId);
            resultSet.close();
            preparedStatement2.close();
            return accessLog;
        }
    }

    public AccessLog get(AccessLog accessLog) throws SQLException {
        System.out.println("3");
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
            preparedStatement.close();
            resultSet.close();

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

            }catch (Exception e) {
                e.printStackTrace();
            }
            preparedStatement.close();
            resultSet.close();
        }
        return null;
    }

    public ArrayList<AccessLog> getItems(Integer userId, String userType, String logSearch) throws SQLException {
        PreparedStatement preparedStatement = null;
        if (userType.equals("staff")) {
            preparedStatement = connection.prepareStatement("SELECT * FROM STAFF_ACCESS_LOGS WHERE StaffId = ? AND LoginDate LIKE ? ORDER BY LoginDate DESC LIMIT 10");
        } else {
            preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMER_ACCESS_LOGS WHERE CustomerId = ? AND LoginDate LIKE ? ORDER BY LoginDate DESC LIMIT 10");
        }

        preparedStatement.setInt(1, userId);

        if (logSearch == null) {
            logSearch = "";
        }

        preparedStatement.setString(2, "%"+logSearch+"%");

        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<AccessLog> accessLogs = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            Date loginDate = resultSet.getTimestamp("LoginDate");
            Date logoutDate = resultSet.getTimestamp("LogoutDate");

            AccessLog log = new AccessLog(id, loginDate, logoutDate, false);
            accessLogs.add(log);
        }
        preparedStatement.close();
        resultSet.close();
        return accessLogs;
    }

    public void endSession() throws SQLException {
        connection.close();
    }

    public void update(AccessLog oldAccessLog, AccessLog newAccessLog) throws SQLException {
        if (newAccessLog.getIsStaff()){

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STAFF_ACCESS_LOGS SET LogoutDate = ? WHERE StaffId = ? AND StaffAccLogId = ?");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String logoutFormatted = sdf.format(newAccessLog.getLogout());
            preparedStatement.setString(1, logoutFormatted);
            preparedStatement.setString(2, String.valueOf(newAccessLog.getUserId()));
            preparedStatement.setString(3, String.valueOf(newAccessLog.getAccessLogId()));

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        else{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CUSTOMER_ACCESS_LOGS SET LogoutDate = ? WHERE CustomerId = ? AND CustomerAccLogId = ?");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String logoutFormatted = sdf.format(newAccessLog.getLogout());
            preparedStatement.setString(1, logoutFormatted);
            preparedStatement.setString(2, String.valueOf(newAccessLog.getUserId()));
            preparedStatement.setString(3, String.valueOf(newAccessLog.getAccessLogId()));

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

    }

    public void delete(AccessLog accessLog) throws SQLException {
        // Does not need to be implemented - to satisfy abstract class.
    }

}