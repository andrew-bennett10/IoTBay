package classes.controller;

import classes.model.AccessLog;
import classes.model.Customer;
import classes.model.dao.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAO db = ((DAO)req.getSession().getAttribute("db"));
        HttpSession session = req.getSession();

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fName = req.getParameter("fName");
        String lName = req.getParameter("lName");
        String age_string = req.getParameter("age");
        String address = req.getParameter("address");

        boolean agreed = req.getParameter("tos") != null;

        String errorMessage = null;

        if (email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                fName == null || fName.isEmpty() ||
                lName == null || lName.isEmpty() ||
                age_string == null || age_string.isEmpty() ||
                address == null || address.isEmpty()) {
            errorMessage = "All fields are required.";
        }

        else {
            try {
                int age = Integer.parseInt(age_string);
                if (age < 0) {
                    errorMessage = "Age must be a positive number.";
                }
            } catch (NumberFormatException e) {
                errorMessage = "Age must be a number.";
            }
        }
        if (!agreed) {
            errorMessage = "You must agree to the Terms of Service.";
        }

        try {
            Boolean emailAlreadyExists = db.Customers().checkEmailUsed(email);
            if (emailAlreadyExists) {
                errorMessage = "An account with this email already exists.";
            }
        }
        catch (SQLException e) {
            errorMessage = "An account with this email already exists.";
        }

        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        String name = fName+" "+lName;
        session.setAttribute("name", name);
        System.out.println("Running this part");
        Customer customer = new Customer(email, password, fName, lName, Integer.valueOf(age_string), address, true, false);
        session.setAttribute("loggedInUser", customer);
        session.setAttribute("userType", "customer");

        try {
            db.Customers().add(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Integer custID = null;
        try {
            custID = db.Customers().get(customer).getId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Date currentDate = new Date();
        AccessLog accessLog = new AccessLog(custID, currentDate, null, false);
        session.setAttribute("currentAccessLog", accessLog);
        System.out.println("Adding log");
        try {
            db.AccessLog().add(accessLog);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("welcome.jsp");
    }
}