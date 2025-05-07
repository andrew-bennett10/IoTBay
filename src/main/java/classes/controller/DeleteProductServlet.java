package classes.controller;

import classes.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import classes.model.dao.DAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer product_id = Integer.valueOf(req.getParameter("id"));
        DAO db = (DAO)session.getAttribute("db");
        String message = null;

        try {
            Product product = db.Products().get(product_id);

            try {

                db.Products().delete(product);
            } catch (SQLException e) {
                message = "Failed to remove %s from the database" + product.getName();
                System.out.format("Failed to remove %s from the database", product.getName());
            }
        } catch (SQLException e) {
            message = "Failed to find product name.";
            System.out.format("Failed to find product name.");
        }

        if (message != null) {
            req.setAttribute("errorMessage", message);
            req.getRequestDispatcher("editProduct.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("index.jsp");
        }
    }
}