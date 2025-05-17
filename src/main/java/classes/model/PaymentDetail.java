package classes.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PaymentDetail {
    private int paymentId;
    private String orderId;
    private String customerEmail;
    private String cardName;
    private String cardNumber; // For security, consider storing only last 4 digits or a token
    private String expiryDate; // MM/YY
    private String cvc;        // For security, CVC should generally not be stored post-authorization
    private Timestamp paymentDate;
    private double amount;

    // Constructor
    public PaymentDetail(String orderId, String customerEmail, String cardName, String cardNumber, String expiryDate, String cvc, double amount) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvc = cvc;
        this.amount = amount;
        this.paymentDate = Timestamp.valueOf(LocalDateTime.now()); // Set current time
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}