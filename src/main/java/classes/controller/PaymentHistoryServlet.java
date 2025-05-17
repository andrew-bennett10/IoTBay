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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/PaymentHistoryServlet")
public class PaymentHistoryServlet extends HttpServlet {

    private DAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dao = new DAO();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize DAO for PaymentHistoryServlet", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String userType = (String) session.getAttribute("userType");
        String customerEmail = null;

        // Retrieve the loggedInUser object and userType from the session
        if (loggedInUser != null) {
            if ("customer".equals(userType) && loggedInUser instanceof Customer) {
                customerEmail = ((Customer) loggedInUser).getEmail();
            } else if ("staff".equals(userType) && loggedInUser instanceof Staff) {
                customerEmail = ((Staff) loggedInUser).getEmail();
            }
        }

        if (customerEmail == null) {
            session.setAttribute("generalError", "Please log in to view payment history.");
            response.sendRedirect("login.jsp");
            return;
        }

        String orderIdFilter = request.getParameter("orderIdFilter");
        String dateFilterStr = request.getParameter("dateFilter");

        try {
            // Retrieve payment history for the logged-in user
            List<PaymentDetail> paymentHistory = dao.PaymentDetails().getPaymentsByEmail(customerEmail);
            List<PaymentDetail> filteredHistory = paymentHistory;

            // Apply filters if provided
            if (orderIdFilter != null && !orderIdFilter.trim().isEmpty()) {
                String finalOrderIdFilter = orderIdFilter.trim().toLowerCase();
                filteredHistory = filteredHistory.stream()
                        .filter(p -> p.getOrderId() != null && p.getOrderId().toLowerCase().contains(finalOrderIdFilter))
                        .collect(Collectors.toList());
            }

            // Filter by date if provided
            if (dateFilterStr != null && !dateFilterStr.trim().isEmpty()) {
                try {
                    LocalDate filterDate = LocalDate.parse(dateFilterStr);
                    filteredHistory = filteredHistory.stream()
                            .filter(p -> p.getPaymentDate() != null && p.getPaymentDate().toLocalDateTime().toLocalDate().equals(filterDate))
                            .collect(Collectors.toList());
                } catch (DateTimeParseException e) {
                    session.setAttribute("filterError", "Invalid date format for filtering. Please use YYYY-MM-DD.");
                    e.printStackTrace();
                }
            }

            // Set the filtered payment history and filters in the request attributes
            request.setAttribute("paymentHistory", filteredHistory);
            request.setAttribute("orderIdFilter", orderIdFilter);
            request.setAttribute("dateFilter", dateFilterStr);
            request.getRequestDispatcher("paymentHistory.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("generalError", "Error retrieving payment history. Please try again later.");
            response.sendRedirect("main.jsp");
        }
    }
}