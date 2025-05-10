package classes.model.dao;

import classes.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDBManager extends DBManager<Staff> {
    public int getUserCount() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM STAFF");
        resultSet.next();
        return resultSet.getInt(1);
    }

    public StaffDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    public Staff add(Staff staff) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STAFF (Email, Password, FName, LName, Role) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, staff.getEmail());
        preparedStatement.setString(2, staff.getPassword());
        preparedStatement.setString(3, staff.getFName());
        preparedStatement.setString(4, staff.getLName());
        preparedStatement.setString(5, staff.getRole());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("SELECT MAX(StaffId) FROM STAFF");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int userId = resultSet.getInt(1);
        staff.setId(userId);
        return staff;
    }

    public Staff get(Staff staff) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STAFF WHERE StaffId = ?");
        preparedStatement.setInt(1, staff.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Staff(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
    }

    public Staff get(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM STAFF WHERE email = ? AND password = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Staff(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
    }

    public void update(Staff oldStaff, Staff newStaff) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STAFF SET Email = ?, Password = ?, FName = ?, LName = ?, Role = ? WHERE StaffId = ?");
        preparedStatement.setString(1, newStaff.getEmail());
        preparedStatement.setString(2, newStaff.getPassword());
        preparedStatement.setString(3, newStaff.getFName());
        preparedStatement.setString(4, newStaff.getLName());
        preparedStatement.setString(5, newStaff.getRole());
        preparedStatement.setInt(6, oldStaff.getId());
        preparedStatement.executeUpdate();

    }

    public void delete(Staff staff) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM STAFF WHERE StaffId = ?");
        preparedStatement.setInt(1, staff.getId());
        preparedStatement.executeUpdate();
    }

    public Boolean checkEmailUsed(String email) throws SQLException {
        String sql = "SELECT 1 FROM STAFF WHERE email = ? LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }
}