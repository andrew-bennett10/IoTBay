package classes.controller;

import classes.model.PaymentDetail;
import classes.model.dao.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; // Import the annotation
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID; // Import UUID for generating random orderId
import java.util.regex.Pattern;

@WebServlet("/ProcessPaymentServlet") // Add this annotation
public class ProcessPaymentServlet extends HttpServlet {

    private DAO dao; // For database operations

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

        String customerEmail = (String) session.getAttribute("email");
        String orderId = (String) session.getAttribute("orderId");
        Double cartTotal = (Double) session.getAttribute("cartTotal");

        // Handle null orderId by generating a random one
        if (orderId == null) {
            orderId = UUID.randomUUID().toString();
            // Optionally, you might want to store this newly generated orderId back in the session
            // if other parts of your application might need it before the session is cleared.
            // session.setAttribute("orderId", orderId);
        }

        // Handle null cartTotal by setting it to $1.00
        if (cartTotal == null) {
            cartTotal = 1.0;
        }

        if (customerEmail == null) { // Simplified check, as orderId and cartTotal are now handled
            session.setAttribute("paymentError", "Session expired or required information is missing. Please try again.");
            response.sendRedirect("payment.jsp");
            return;
        }

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
            PaymentDetail paymentDetail = new PaymentDetail(orderId, customerEmail, cardName, cardNumber, expiryDate, cvc, cartTotal);
            dao.PaymentDetails().add(paymentDetail);

            session.setAttribute("paymentSuccess", "Payment processed successfully! Your Order ID is " + orderId);
            session.removeAttribute("cartTotal");
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