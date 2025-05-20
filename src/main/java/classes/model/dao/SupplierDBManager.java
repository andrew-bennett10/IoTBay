package classes.model.dao;

import classes.model.Customer;
import classes.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDBManager extends DBManager<Supplier> {

    public SupplierDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    public Supplier add(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO SUPPLIERS (ContactName, Company, Email, SuppStatus) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, supplier.getContactName());
        ps.setString(2, supplier.getCompany());
        ps.setString(3, supplier.getEmail());
        ps.setString(4, supplier.getSuppStatus());
        ps.executeUpdate();
        ps = connection.prepareStatement("SELECT MAX(SupplierId) FROM SUPPLIERS");
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();
        int userId = resultSet.getInt(1);
        supplier.setId(userId);
        return supplier;
    }

    @Override
    protected Supplier get(Supplier object) throws SQLException {
        return null;
    }

    @Override
    protected void update(Supplier oldObject, Supplier newObject) throws SQLException {

    }

    @Override
    protected void delete(Supplier object) throws SQLException {

    }

    public Supplier getById(int supplierId) throws SQLException {
        String sql = "SELECT * FROM SUPPLIERS WHERE SupplierId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, supplierId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapResultSetToSupplier(rs);
        }
        return null;
    }

    public Supplier getByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM SUPPLIERS WHERE Email = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapResultSetToSupplier(rs);
        }
        return null;
    }

    public List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM SUPPLIERS";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Supplier> suppliers = new ArrayList<>();
        while (rs.next()) {
            suppliers.add(mapResultSetToSupplier(rs));
        }
        return suppliers;
    }

    public int getSupplierCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM SUPPLIERS";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

    public void update(Supplier supplier) throws SQLException {
        String sql = "UPDATE SUPPLIERS SET ContactName = ?, Company = ?, Email = ?, SuppStatus = ? WHERE SupplierId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, supplier.getContactName());
        ps.setString(2, supplier.getCompany());
        ps.setString(3, supplier.getEmail());
        ps.setString(4, supplier.getSuppStatus());
        ps.setInt(5, supplier.getId());
        ps.executeUpdate();
    }

    public void delete(int supplierId) throws SQLException {
        String sql = "DELETE FROM SUPPLIERS WHERE SupplierId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, supplierId);
        ps.executeUpdate();
    }

    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM SUPPLIERS WHERE Email = ? LIMIT 1";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        return new Supplier(
                rs.getString("ContactName"),
                rs.getString("Company"),
                rs.getString("Email"),
                rs.getString("SuppStatus")
        );
    }
}