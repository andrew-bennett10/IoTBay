package classes.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    ArrayList<DBManager<?>> tables;

    public DAO() throws SQLException {
        tables = new ArrayList<>();
        Connection connection = new DBConnector().getConnection();
        try {
            tables.add(new CustomerDBManager(connection));
            tables.add(new ProductDBManager(connection));
            tables.add(new StaffDBManager(connection));
            tables.add(new AccessLogDBManager(connection));
            tables.add(new PaymentDetailDBManager(connection));
        } catch (SQLException ex) {
            System.out.println("Error initializing DBManagers: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
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
        return (PaymentDetailDBManager) tables.get(4);
    }
}