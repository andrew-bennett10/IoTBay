package classes.model.dao;

import classes.model.PaymentDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDetailDBManager extends DBManager<PaymentDetail> {

    public PaymentDetailDBManager(Connection connection) throws SQLException {
        super(connection);
        // Create the PAYMENT_DETAILS table if it doesn't exist
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS PAYMENT_DETAILS (" +
                    "PaymentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "OrderID TEXT NOT NULL, " +
                    "CustomerEmail TEXT NOT NULL, " +
                    "CardName TEXT NOT NULL, " +
                    "CardNumber TEXT NOT NULL, " +
                    "ExpiryDate TEXT NOT NULL, " +
                    "CVC TEXT NOT NULL, " +
                    "PaymentDate DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "Amount REAL NOT NULL)";
            stmt.execute(sql);
        }
    }

    // Adds a new payment detail to the database
    public PaymentDetail add(PaymentDetail paymentDetail) throws SQLException {
        String sql = "INSERT INTO PAYMENT_DETAILS (OrderID, CustomerEmail, CardName, CardNumber, ExpiryDate, CVC, Amount, PaymentDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, paymentDetail.getOrderId());
            pstmt.setString(2, paymentDetail.getCustomerEmail());
            pstmt.setString(3, paymentDetail.getCardName());
            pstmt.setString(4, paymentDetail.getCardNumber());
            pstmt.setString(5, paymentDetail.getExpiryDate());
            pstmt.setString(6, paymentDetail.getCvc());
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

    // Retrieves all payment details for a specific customer email
    public List<PaymentDetail> getPaymentsByEmail(String customerEmail) throws SQLException {
        List<PaymentDetail> payments = new ArrayList<>();
        String sql = "SELECT * FROM PAYMENT_DETAILS WHERE CustomerEmail = ? ORDER BY PaymentDate DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customerEmail);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PaymentDetail payment = new PaymentDetail(
                        rs.getString("OrderID"),
                        rs.getString("CustomerEmail"),
                        rs.getString("CardName"),
                        rs.getString("CardNumber"),
                        rs.getString("ExpiryDate"),
                        rs.getString("CVC"),
                        rs.getDouble("Amount")
                );
                payment.setPaymentId(rs.getInt("PaymentID"));
                payment.setPaymentDate(rs.getTimestamp("PaymentDate"));
                payments.add(payment);
            }
        }
        return payments;
    }

    // Deletes payment details by card number and customer email
    public int deletePaymentDetailsByCardNumberAndEmail(String cardNumber, String customerEmail) throws SQLException {
        String sql = "DELETE FROM PAYMENT_DETAILS WHERE CardNumber = ? AND CustomerEmail = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, customerEmail);
            return pstmt.executeUpdate();
        }
    }


    @Override
    public PaymentDetail get(PaymentDetail item) throws SQLException {
        throw new UnsupportedOperationException("Get method not fully implemented.");
    }

    @Override
    public void update(PaymentDetail oldItem, PaymentDetail newItem) throws SQLException {
        throw new UnsupportedOperationException("Update method not implemented.");
    }

    @Override
    public void delete(PaymentDetail item) throws SQLException {

        String sql = "DELETE FROM PAYMENT_DETAILS WHERE PaymentID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, item.getPaymentId());
            pstmt.executeUpdate();
        }
    }
}