package classes.model.dao;

import classes.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ProductDBManager extends DBManager<Product> {
    public ProductDBManager(Connection connection) throws SQLException {
        super(connection);
    }

    public Product add(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTS (ProductId, Name, Description, Stock, Price, Supplier) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, product.getId());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setString(3, product.getDescription());
        preparedStatement.setInt(4, product.getStock());
        preparedStatement.setFloat(5, product.getPrice());
        preparedStatement.setString(6, product.getSupplier());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("SELECT MAX(ProductId) FROM PRODUCTS");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int productId = resultSet.getInt(1);
        product.setId(productId);
        return product;
    }

    public Product get(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE ProductId = ?");
        preparedStatement.setInt(1, product.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getFloat(5), resultSet.getString(6));
    }

    public Product get(Integer product_id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE ProductId = ?");
        preparedStatement.setInt(1, product_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getFloat(5), resultSet.getString(6));
    }


    public void update(Product oldProduct, Product newProduct) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PRODUCTS SET Name = ?, Description = ?, Stock = ?, Price = ?, Supplier = ? WHERE ProductId = ?");
        preparedStatement.setString(1, newProduct.getName());
        preparedStatement.setString(2, newProduct.getDescription());
        preparedStatement.setInt(3, newProduct.getStock());
        preparedStatement.setFloat(4, newProduct.getPrice());
        preparedStatement.setString(5, newProduct.getSupplier());
        preparedStatement.setInt(6, oldProduct.getId());
        preparedStatement.executeUpdate();
    }

    public void delete(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCTS WHERE ProductId = ?");
        preparedStatement.setInt(1, product.getId());
        preparedStatement.executeUpdate();
    }

    public LinkedList<Product> allProducts() throws SQLException {
        LinkedList<Product> products = new LinkedList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCTS");
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            products.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getFloat(5), resultSet.getString(6)));
        }
        return products;
    }
}