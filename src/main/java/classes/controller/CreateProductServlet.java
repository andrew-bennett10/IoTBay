package classes.controller;

import classes.model.Product;
import classes.model.dao.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CreateProductServlet")
public class CreateProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAO db = ((DAO)req.getSession().getAttribute("db"));
        HttpSession session = req.getSession();

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Integer stock = Integer.valueOf(req.getParameter("stock"));
        Float price = Float.valueOf(req.getParameter("price"));
        String supplier = req.getParameter("supplier");

        Product product = new Product(name, description, stock, price, supplier);

        try {
            db.Products().add(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}