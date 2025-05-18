package classes.model.dao;

import classes.model.Customer;
import classes.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDBManager extends DBManager<Staff> {
    public int getUserCount() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM STAFF");
        resultSet.next();
        return resultSet.getInt(1);
    }

    public StaffDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    //CREATE
    public Staff add(Staff staff) throws SQLException {
        String sql = "INSERT INTO STAFF (Email, Password, FName, LName, Role, PhoneNumber, IsActive) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, staff.getEmail());
        preparedStatement.setString(2, staff.getPassword());
        preparedStatement.setString(3, staff.getFName());
        preparedStatement.setString(4, staff.getLName());
        preparedStatement.setString(5, staff.getRole());
        preparedStatement.setString(6, staff.getPhoneNumber());
        preparedStatement.setBoolean(7, staff.getActive());
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
        if(resultSet.next()){
            return new Staff(
                    resultSet.getInt("StaffId"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FName"),
                    resultSet.getString("LName"),
                    resultSet.getString("Role"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getBoolean("IsActive"));
        }else {
            return null;
        }
    }

    public List<Staff> get(String firstName, String lastName, String phoneNumber) throws SQLException {
        List<Staff> staffMembers = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM STAFF WHERE 1=1");

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
            Staff staff = new Staff(
                    resultSet.getInt("StaffId"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FName"),
                    resultSet.getString("LName"),
                    resultSet.getString("Role"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getBoolean("IsActive")
            );
            staffMembers.add(staff);
        }
        return staffMembers;
    }

    public List<Staff> getAll() throws SQLException {
        List<Staff> staffMembers = new ArrayList<>();
        String query = "SELECT * FROM STAFF";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Staff staff = new Staff(
                    resultSet.getInt("StaffId"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FName"),
                    resultSet.getString("LName"),
                    resultSet.getString("Role"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getBoolean("IsActive")
            );
            staffMembers.add(staff);
        }
        return staffMembers;
    }

    public Staff getById(int userId) throws SQLException {
        String query = "SELECT * FROM STAFF WHERE StaffId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Staff(
                    resultSet.getInt("StaffId"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FName"),
                    resultSet.getString("LName"),
                    resultSet.getString("Role"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getBoolean("IsActive")
            );
        }
        System.out.println("Can't find staffs");
        return null;
    }

    //UPDATE
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

    public void update(Staff staff) throws SQLException {
        String query = "UPDATE STAFF SET Email = ?, Password = ?, FName = ?, LName = ?, Role = ?, PhoneNumber = ?, IsActive = ? WHERE StaffId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, staff.getEmail());
            ps.setString(2, staff.getPassword());
            ps.setString(3, staff.getFName());
            ps.setString(4, staff.getLName());
            ps.setString(5,staff.getRole());
            ps.setString(6, staff.getPhoneNumber());
            ps.setBoolean(7, staff.getActive());
            ps.setInt(8, staff.getId());
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //DELETE
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
    public boolean existsEmail(String email) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM STAFF WHERE Email = ?");
        preparedStatement.setString(1,email);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) > 0;
    }

    public boolean isAdmin(String email) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT Role FROM STAFF WHERE Email = ?");
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() && "Admin".equalsIgnoreCase(resultSet.getString("Role"));
    }
}