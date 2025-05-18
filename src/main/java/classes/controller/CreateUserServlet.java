package classes.controller;

import classes.model.Customer;
import classes.model.Staff;
import classes.model.dao.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DAO db = (DAO) session.getAttribute("db");

        String userType = req.getParameter("userType");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fName = req.getParameter("fName");
        String lName = req.getParameter("lName");
        String ageStr = req.getParameter("age");
        String address = req.getParameter("address");
        String phoneNumber = req.getParameter("phoneNumber");
        String role = req.getParameter("role"); // Only for staff
        String errorMessage = null;


        if (errorMessage == null && !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage = "Invalid email format.";
        }

        if (errorMessage == null) {
            try {
                boolean emailExists = db.Customers().existsEmail(email) || db.Staff().existsEmail(email);
                if (emailExists) {
                    errorMessage = "Email already exists.";
                    System.out.println(errorMessage);
                }
            } catch (SQLException e) {
                errorMessage = "Error checking email availability.";
                System.out.println(errorMessage);
                e.printStackTrace();
            }
        }

        int age = 0;
        if(errorMessage == null && "customer".equalsIgnoreCase(userType)) {
            try {
                age = Integer.parseInt(ageStr);
                if(age < 0){
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                errorMessage = "Age must be a positive number.";
                System.out.println(errorMessage);
                e.printStackTrace();
            }
        }

        if (errorMessage == null) {
            try {
                if ("customer".equalsIgnoreCase(userType)) {
                    Customer newCustomer = new Customer(email, password, fName, lName, age, address,true, phoneNumber, true);
                    newCustomer.setActive(true);
                    db.Customers().add(newCustomer);
                } else if ("staff".equalsIgnoreCase(userType)) {
                    Staff newStaff = new Staff(email, password, fName, lName, role, phoneNumber, true);
                    newStaff.setActive(true);
                    db.Staff().add(newStaff);
                } else {
                    errorMessage = "Invalid user type";
                    System.out.println(errorMessage);
                }
            } catch (SQLException e) {
                errorMessage = "Error creating user: " + e.getMessage();
                e.printStackTrace();
            }
        }

        if (errorMessage != null) {
            System.out.println(errorMessage);
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("createUser.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("main.jsp");
        }

    }

}




