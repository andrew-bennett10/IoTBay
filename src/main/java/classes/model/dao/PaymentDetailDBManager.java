package classes.model.dao;

import classes.model.PaymentDetail;

import java.sql.*;

public class PaymentDetailDBManager extends DBManager<PaymentDetail> {

    public PaymentDetailDBManager(Connection connection) throws SQLException {
        super(connection);
        // Ensure the PAYMENT_DETAILS table exists.
        // This is a basic way to create it if it doesn't exist.
        // In a production environment, use migration scripts.
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS PAYMENT_DETAILS (" +
                    "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "OrderID TEXT NOT NULL, " +
                    "CustomerEmail TEXT NOT NULL, " +
                    "CardName TEXT NOT NULL, " +
                    "CardNumber TEXT NOT NULL, " + // Consider security implications
                    "ExpiryDate TEXT NOT NULL, " +
                    "CVC TEXT NOT NULL, " +        // Consider security implications
                    "PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "Amount REAL NOT NULL)";
            stmt.execute(sql);
        }
    }

    public PaymentDetail add(PaymentDetail paymentDetail) throws SQLException {
        String sql = "INSERT INTO PAYMENT_DETAILS (OrderID, CustomerEmail, CardName, CardNumber, ExpiryDate, CVC, Amount, PaymentDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, paymentDetail.getOrderId());
            pstmt.setString(2, paymentDetail.getCustomerEmail());
            pstmt.setString(3, paymentDetail.getCardName());
            pstmt.setString(4, paymentDetail.getCardNumber()); // Sensitive data
            pstmt.setString(5, paymentDetail.getExpiryDate());
            pstmt.setString(6, paymentDetail.getCvc());         // Sensitive data
            pstmt.setDouble(7, paymentDetail.getAmount());
            pstmt.setTimestamp(8, paymentDetail.getPaymentDate());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paymentDetail.setPaymentId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating payment detail failed, no ID obtained.");
                }
            }
            return paymentDetail;
        }
    }

    // Implement get, update, delete methods as needed, similar to CustomerDBManager
    @Override
    public PaymentDetail get(PaymentDetail item) throws SQLException {
        // Example: Get by PaymentID
        // PreparedStatement ps = connection.prepareStatement("SELECT * FROM PAYMENT_DETAILS WHERE PaymentID = ?");
        // ps.setInt(1, item.getPaymentId());
        // ... execute and map to PaymentDetail object
        throw new UnsupportedOperationException("Get method not fully implemented.");
    }

    @Override
    public void update(PaymentDetail oldItem, PaymentDetail newItem) throws SQLException {
        throw new UnsupportedOperationException("Update method not implemented.");
    }

    @Override
    public void delete(PaymentDetail item) throws SQLException {
        throw new UnsupportedOperationException("Delete method not implemented.");
    }
}