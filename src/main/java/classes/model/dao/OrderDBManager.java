package classes.model.dao;

import classes.model.Order;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class OrderDBManager extends DBManager<Order> {
    private final OrderDAO orderDAO;

    public OrderDBManager(Connection conn) throws SQLException {
        super(conn);
        this.orderDAO = new OrderDAO(conn);
    }

    @Override
    public Order add(Order order) throws SQLException {
        orderDAO.createOrder(order);
        return order;
    }


    @Override
    public Order get(Order order) throws SQLException {
        return null;
    }

    @Override
    protected void update(Order oldObject, Order newObject) throws SQLException {

    }

    @Override
    protected void delete(Order object) throws SQLException {

    }

    public void create(Order order) throws SQLException {
        orderDAO.createOrder(order);
    }

    public List<Order> getAll() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public void delete(int orderId) throws SQLException {
        orderDAO.deleteOrder(orderId);
    }
}
