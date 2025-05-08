package classes.model.dao;

import classes.model.PaymentDetail; // Import PaymentDetail

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    ArrayList<DBManager<?>> tables;

    public DAO() throws SQLException {
        tables = new ArrayList<>();
        Connection connection = new DBConnector().getConnection();
        try {
            tables.add(new CustomerDBManager(connection)); // Index 0
            tables.add(new ProductDBManager(connection));  // Index 1
            tables.add(new StaffDBManager(connection));    // Index 2
            tables.add(new AccessLogDBManager(connection)); // Index 3
            tables.add(new PaymentDetailsDBManager(connection)); // Index 4
        } catch (SQLException ex) {
            System.out.println("Error initializing DBManagers");
            // It's good practice to re-throw the exception or handle it more robustly
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

    public PaymentDetailsDBManager PaymentDetails() {
        return (PaymentDetailsDBManager) tables.get(4);
    }

    // New method to add payment details
    public PaymentDetail addPaymentDetail(PaymentDetail paymentDetail) throws SQLException {
        return this.PaymentDetails().add(paymentDetail);
    }
}