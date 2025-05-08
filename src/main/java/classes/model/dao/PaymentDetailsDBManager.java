package classes.model.dao;

import classes.model.PaymentDetail;

import java.sql.*;

public class PaymentDetailsDBManager extends DBManager<PaymentDetail> {

    public PaymentDetailsDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected PaymentDetail add(PaymentDetail paymentDetail) throws SQLException {
        String sql = "INSERT INTO PAYMENT_DETAILS (CustomerId, CardNumber, CardHolderName, ExpiryDate, CVV, OrderDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, paymentDetail.getCustomerId());
            preparedStatement.setString(2, paymentDetail.getCardNumber());
            preparedStatement.setString(3, paymentDetail.getCardHolderName());
            preparedStatement.setString(4, paymentDetail.getExpiryDate());
            preparedStatement.setString(5, paymentDetail.getCvv());
            preparedStatement.setString(6, paymentDetail.getOrderDate());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating payment detail failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paymentDetail.setPaymentId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating payment detail failed, no ID obtained.");
                }
            }
            return paymentDetail;
        }
    }

    @Override
    protected PaymentDetail get(PaymentDetail paymentDetail) throws SQLException {
        // This method might need a more specific signature, e.g., getById(int paymentId)
        // For now, implementing based on the abstract class signature.
        // Assuming paymentDetail object might have an ID set for lookup, or this is a placeholder.
        if (paymentDetail.getPaymentId() == 0) {
            throw new SQLException("PaymentId must be set to get a PaymentDetail.");
        }
        String sql = "SELECT * FROM PAYMENT_DETAILS WHERE PaymentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, paymentDetail.getPaymentId());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new PaymentDetail(
                            rs.getInt("PaymentId"),
                            rs.getInt("CustomerId"),
                            rs.getString("CardNumber"),
                            rs.getString("CardHolderName"),
                            rs.getString("ExpiryDate"),
                            rs.getString("CVV"),
                            rs.getString("OrderDate")
                    );
                }
            }
        }
        return null; // Or throw an exception if not found
    }

    @Override
    protected void update(PaymentDetail oldObject, PaymentDetail newObject) throws SQLException {
        // Implementation for updating payment details can be added here if needed.
        throw new UnsupportedOperationException("Update operation is not yet implemented for PaymentDetails.");
    }

    @Override
    protected void delete(PaymentDetail paymentDetail) throws SQLException {
        // Implementation for deleting payment details can be added here if needed.
        throw new UnsupportedOperationException("Delete operation is not yet implemented for PaymentDetails.");
    }
}