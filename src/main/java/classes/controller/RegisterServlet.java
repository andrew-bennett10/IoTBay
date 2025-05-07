package classes.controller;

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

        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        String name = fName+" "+lName;
//        String name = email.split("@")[0];
        session.setAttribute("name", name);

        Customer customer = new Customer(email, password, fName, lName, Integer.valueOf(age_string), address, true, false);
        session.setAttribute("loggedInUser", customer);
        try {
            db.Customers().add(customer);
            resp.sendRedirect("welcome.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        String imagePath = "image/" + req.getParameter("genre").toLowerCase() + ".jpg";
//        session.setAttribute("imagePath", imagePath);
//        resp.sendRedirect("welcome.jsp");
    }
}