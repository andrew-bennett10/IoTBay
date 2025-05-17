package classes.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    ArrayList<DBManager<?>> tables;

    public DAO() throws SQLException {
        tables = new ArrayList<>();
        Connection connection = new DBConnector().getConnection(); // This might create a new connection each time DAO is instantiated. Consider managing connection lifecycle.
        try {
            tables.add(new CustomerDBManager(connection));    // Index 0
            tables.add(new ProductDBManager(connection));     // Index 1
            tables.add(new StaffDBManager(connection));       // Index 2
            tables.add(new AccessLogDBManager(connection));   // Index 3
            tables.add(new PaymentDetailDBManager(connection)); // Index 4
        } catch (SQLException ex) {
            System.out.println("Error initializing DBManagers: " + ex.getMessage());
            ex.printStackTrace(); // More detailed error logging
            throw ex; // Re-throw to indicate DAO initialization failure
        }
    }

    public CustomerDBManager Customers() {
        return (CustomerDBManager) tables.get(0);
    }

    public StaffDBManager Staff() {
        return (StaffDBManager) tables.get(2);
    }

    public ProductDBManager Products() {
        return (ProductDBManager) tables.get(1);
    }

    public AccessLogDBManager AccessLog() {
        return (AccessLogDBManager) tables.get(3);
    }

    public PaymentDetailDBManager PaymentDetails() {
        return (PaymentDetailDBManager) tables.get(4); // New accessor
    }
}