package classes.controller;

import classes.model.Customer;
import classes.model.Staff;
import classes.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import classes.model.dao.DAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EditAccountServlet")
public class EditAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        DAO db = (DAO)session.getAttribute("db");
        String userType = (String) session.getAttribute("userType");
        User user = (User) session.getAttribute("user");
        String message = null;

        try {
            if ("customer".equals(userType)) {
                Customer customer = (Customer) user;

                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String fName = req.getParameter("fName");
                String lName = req.getParameter("lName");
                Integer age = Integer.valueOf(req.getParameter("age"));
                String address = req.getParameter("address");

                Customer newCustomer = new Customer(customer.getId(), email, password, fName, lName, age, address, true, false);
                db.Customers().update(customer, newCustomer);
                session.setAttribute("loggedInUser", newCustomer);


            } else if ("staff".equals(userType)) {
                Staff staff = (Staff) user;

                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String fName = req.getParameter("fName");
                String lName = req.getParameter("lName");
                String role = req.getParameter("role");

                Staff newStaff = new Staff(staff.getId(), email, password, fName, lName, role);
                db.Staff().update(staff, newStaff);
                session.setAttribute("loggedInUser", newStaff);


            }
        } catch (SQLException e) {
            message = "Failed to remove user from the database";
//            System.out.format("Failed to remove user from the database");
//            e.printStackTrace();
        }
//        resp.sendRedirect("index.jsp");

        if (message != null) {
            req.setAttribute("errorMessage", message);
            req.getRequestDispatcher("editUser.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("index.jsp");
        }

    }
}