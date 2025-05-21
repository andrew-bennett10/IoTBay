package classes.controller;

import classes.model.Customer;
import classes.model.PaymentDetail;
import classes.model.Staff;
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
import java.util.UUID;
import java.util.regex.Pattern;

@WebServlet("/ProcessPaymentServlet")
public class ProcessPaymentServlet extends HttpServlet {

    private DAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dao = new DAO();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize DAO for ProcessPaymentServlet", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve the logged-in user and their type from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String userType = (String) session.getAttribute("userType");
        String customerEmail = null;

        if (loggedInUser != null) {
            if ("customer".equals(userType) && loggedInUser instanceof Customer) {
                customerEmail = ((Customer) loggedInUser).getEmail();
            } else if ("staff".equals(userType) && loggedInUser instanceof Staff) {
                customerEmail = ((Staff) loggedInUser).getEmail();
            }
        }

        String orderId = (String) session.getAttribute("orderId");
        Double totalPrice = (Double) session.getAttribute("totalPrice"); // Changed from cartTotal

        // Currently orderID is randomly generated - may change this
        if (orderId == null) {
            orderId = UUID.randomUUID().toString();
        }

        // If totalPrice is null set to 1.0
        // Will have to see how product pricing works - may change this
        if (totalPrice == null) { // Changed from cartTotal
            totalPrice = 1.0; // Changed from cartTotal
        }

        // If the user is not logged in or the email is null, redirect to payment.jsp with an error message
        if (customerEmail == null) {
            session.setAttribute("paymentError", "Session expired or user information is missing. Please log in and try again.");
            response.sendRedirect("payment.jsp");
            return;
        }

        // Validate payment details
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvc = request.getParameter("cvc");

        StringBuilder errors = new StringBuilder();
        if (cardName == null || cardName.trim().isEmpty()) {
            errors.append("Name on card is required.<br>");
        }
        if (cardNumber == null || !Pattern.matches("^\\d{13,19}$", cardNumber)) {
            errors.append("Invalid card number format (must be 13-19 digits).<br>");
        }
        if (expiryDate == null || !Pattern.matches("^(0[1-9]|1[0-2])\\/\\d{2}$", expiryDate)) {
            errors.append("Invalid expiry date format (must be MM/YY).<br>");
        }
        if (cvc == null || !Pattern.matches("^\\d{3,4}$", cvc)) {
            errors.append("Invalid CVC format (must be 3 or 4 digits).<br>");
        }

        if (errors.length() > 0) {
            session.setAttribute("paymentError", errors.toString());
            response.sendRedirect("payment.jsp");
            return;
        }

        try {
            PaymentDetail paymentDetail = new PaymentDetail(orderId, customerEmail, cardName, cardNumber, expiryDate, cvc, totalPrice); // Changed from cartTotal
            dao.PaymentDetails().add(paymentDetail);

            session.setAttribute("paymentSuccess", "Payment processed successfully! Your Order ID is " + orderId);
            session.removeAttribute("totalPrice"); // Changed from cartTotal
            session.removeAttribute("orderId");

            response.sendRedirect("main.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("paymentError", "Payment processing failed due to a database error. Please try again later.");
            response.sendRedirect("payment.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("paymentError", "An unexpected error occurred during payment. Please try again.");
            response.sendRedirect("payment.jsp");
        }
    }
}