package classes.controller;

import classes.model.Customer;
import classes.model.User;
import classes.model.dao.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeletePaymentDetailServlet")
public class DeletePaymentDetailServlet extends HttpServlet {
    private DAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dao = new DAO();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize DAO for DeletePaymentDetailServlet", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Retrieve the loggedInUser object and userType from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String userType = (String) session.getAttribute("userType");
        String customerEmail = null;

        if (loggedInUser != null && "customer".equals(userType) && loggedInUser instanceof Customer) {
            customerEmail = ((Customer) loggedInUser).getEmail();
        }

        if (customerEmail == null) {
            session.setAttribute("generalError", "User not authenticated. Please log in.");
            response.sendRedirect("login.jsp");
            return;
        }

        // Get the card number to delete from the request
        String cardNumberToDelete = request.getParameter("cardNumberToDelete");

        if (cardNumberToDelete == null || cardNumberToDelete.trim().isEmpty()) {
            session.setAttribute("generalError", "Invalid request: Card number missing.");
            response.sendRedirect("PaymentHistoryServlet");
            return;
        }

        try {
            // Call the DAO method to delete the payment details
            int rowsAffected = dao.PaymentDetails().deletePaymentDetailsByCardNumberAndEmail(cardNumberToDelete, customerEmail);
            if (rowsAffected > 0) {
                session.setAttribute("generalMessage", "Payment method deleted successfully.");
            } else {
                session.setAttribute("generalError", "Could not find the specified payment method to delete or it was already removed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("generalError", "Error deleting payment method: " + e.getMessage());
        }
        response.sendRedirect("PaymentHistoryServlet");
    }
}