package classes.controller;

//import classes.model.Customer;
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

@WebServlet("/EditProductServlet")
public class EditProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        DAO db = (DAO)session.getAttribute("db");

        String id_string = req.getParameter("id");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String stock_string = req.getParameter("stock");
        String price_string = req.getParameter("price");
        String supplier = req.getParameter("supplier");


        String errorMessage = null;

        if (id_string == null || id_string.isEmpty() ||
                name == null || name.isEmpty() ||
                description == null || description.isEmpty() ||
                stock_string == null || stock_string.isEmpty() ||
                price_string == null || price_string.isEmpty() ||
                supplier == null || supplier.isEmpty()) {
            errorMessage = "All fields are required.";
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("editProduct.jsp").forward(req, resp);
            return;
        }
//        int id = Integer.parseInt(id_string);
//        Integer stock = Integer.parseInt(stock_string);
//        Float price = Float.valueOf(price_string);



//        Product new_product = new Product(id, name, description, stock, price, supplier);
//        Customer existingCustomer = (Customer)session.getAttribute("loggedInUser");
//        Customer newCustomer = new Customer(email, password, genre);

        try {
            int id = Integer.parseInt(id_string);
            Integer stock = Integer.parseInt(stock_string);
            Float price = Float.valueOf(price_string);

            Product new_product = new Product(id, name, description, stock, price, supplier);
            Product old_product = db.Products().get(id);
            db.Products().update(old_product, new_product);
//            session.setAttribute("loggedInUser", newCustomer);
        } catch (SQLException e) {
            errorMessage = "An error occurred. Please verify inputs.";
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("editProduct.jsp").forward(req, resp);
            e.printStackTrace();
        }
        resp.sendRedirect("index.jsp");
    }
}