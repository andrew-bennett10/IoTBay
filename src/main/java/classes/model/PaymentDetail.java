package classes.model;

import java.io.Serializable;

public class PaymentDetail implements Serializable {
    private int paymentId;
    private int customerId;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private String orderDate;

    public PaymentDetail() {
    }

    public PaymentDetail(int customerId, String cardNumber, String cardHolderName, String expiryDate, String cvv, String orderDate) {
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.orderDate = orderDate;
    }

    public PaymentDetail(int paymentId, int customerId, String cardNumber, String cardHolderName, String expiryDate, String cvv, String orderDate) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.orderDate = orderDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}