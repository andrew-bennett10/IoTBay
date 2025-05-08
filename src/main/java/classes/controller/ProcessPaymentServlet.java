package classes.controller;

import classes.model.Customer;
import classes.model.PaymentDetail;
import classes.model.dao.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet(name = "ProcessPaymentServlet", urlPatterns = {"/ProcessPaymentServlet"})
public class ProcessPaymentServlet extends HttpServlet {

    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^[0-9]{13,19}$");
    private static final Pattern EXPIRY_DATE_PATTERN = Pattern.compile("^(0[1-9]|1[0-2])\\/([0-9]{2})$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^[0-9]{3,4}$");

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        DAO dao = (DAO) session.getAttribute("dao");
        Customer customer = (Customer) session.getAttribute("customer");

        if (dao == null) {
            try {
                dao = new DAO();
                session.setAttribute("dao", dao);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "System error: Could not connect to the database. Please try again later.");
                request.getRequestDispatcher("purchase.jsp").forward(request, response);
                return;
            }
        }

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String cardHolderName = request.getParameter("cardHolderName");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");

        List<String> errors = new ArrayList<>();

        if (cardHolderName == null || cardHolderName.trim().isEmpty()) {
            errors.add("Card holder name is required.");
        }
        if (cardNumber == null || !CARD_NUMBER_PATTERN.matcher(cardNumber).matches()) {
            errors.add("Invalid card number. Must be 13-19 digits.");
        }
        if (expiryDate == null || !EXPIRY_DATE_PATTERN.matcher(expiryDate).matches()) {
            errors.add("Invalid expiry date. Format must be MM/YY.");
        } else {
            String[] parts = expiryDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]) + 2000;
            LocalDate today = LocalDate.now();
            LocalDate cardExpiry = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);

            if (cardExpiry.isBefore(today)) {
                errors.add("Card has expired.");
            }
        }
        if (cvv == null || !CVV_PATTERN.matcher(cvv).matches()) {
            errors.add("Invalid CVV. Must be 3 or 4 digits.");
        }

        Object cartTotalObj = session.getAttribute("cartTotal");
        if (cartTotalObj != null) {
            request.setAttribute("cartTotal", cartTotalObj);
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errorMessage", String.join(" ", errors));
            request.setAttribute("cardHolderName", cardHolderName);
            request.setAttribute("cardNumber", cardNumber);
            request.setAttribute("expiryDate", expiryDate);
            request.setAttribute("cvv", cvv);
            request.getRequestDispatcher("purchase.jsp").forward(request, response);
            return;
        }

        try {
            String orderDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            PaymentDetail paymentDetail = new PaymentDetail(
                    customer.getId(),
                    cardNumber,
                    cardHolderName,
                    expiryDate,
                    cvv,
                    orderDate
            );

            PaymentDetail savedPayment = dao.addPaymentDetail(paymentDetail);

            session.removeAttribute("cart");
            session.removeAttribute("cartTotal");

            request.setAttribute("orderId", String.valueOf(savedPayment.getPaymentId()));
            request.getRequestDispatcher("paymentConfirmation.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing payment: " + e.getMessage());
            request.setAttribute("cardHolderName", cardHolderName);
            request.setAttribute("cardNumber", cardNumber);
            request.setAttribute("expiryDate", expiryDate);
            request.setAttribute("cvv", cvv);
            request.getRequestDispatcher("purchase.jsp").forward(request, response);
        }
    }
}