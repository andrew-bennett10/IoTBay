package classes.controller;

import classes.model.Staff;
import classes.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import classes.model.Customer;
import classes.model.dao.DAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DAO db = (DAO)session.getAttribute("db");
        String userType = (String) session.getAttribute("userType");
        User user = (User) session.getAttribute("loggedInUser");

        try {
            if ("customer".equals(userType)) {
                Customer customer = (Customer) user;
                db.Customers().delete(customer);
            } else if ("staff".equals(userType)) {
                Staff staff = (Staff) user;
                db.Staff().delete(staff);
            } else {
                System.out.println("This is broken in DeleteAccountServlet");
            }
        } catch (SQLException e) {
            System.out.format("Failed to remove user from the database");
        }
        session.removeAttribute("loggedInUser");
        session.removeAttribute("userType");

        req.setAttribute("errorMessage", "Your account has been successfully deleted.");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}