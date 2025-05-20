package classes.model;

import java.util.Date;

public class Order {
    private int orderId;
    private int customerId;
    private int productId;
    private double totalPrice;
    private Date orderDate;

    // for creating a new order
    public Order(int customerId, int productId, double totalPrice, Date orderDate) {
        this.customerId = customerId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    // Retrieving from DB
    public Order(int orderId, int customerId, int productId, double totalPrice, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public Order() {

    }



    public int getCustomerId() {
        return customerId;
    }



    public int getProductId() {
        return productId;
    }



    public double getTotalPrice() {
        return totalPrice;
    }



    public Date getOrderDate() {
        return orderDate;
    }

}
