package classes.controller;

import classes.model.Customer;
import classes.model.Staff;
import classes.model.dao.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAO db = (DAO) req.getSession().getAttribute("db");

        String userType = req.getParameter("userType");
        int userId = Integer.parseInt(req.getParameter("userId"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fName = req.getParameter("fName");
        String lName = req.getParameter("lName");
        String phoneNumber = req.getParameter("phoneNumber");
        boolean isActive = Boolean.parseBoolean(req.getParameter("isActive"));

        try {
            if ("customer".equals(userType)) {
                Customer customer = db.Customers().getById(userId);
                customer.setEmail(email);
                customer.setPassword(password);
                customer.setFName(fName);
                customer.setLName(lName);
                customer.setPhoneNumber(phoneNumber);
                customer.setActive(isActive);

                String address = req.getParameter("address");
                int age = Integer.parseInt(req.getParameter("age"));
                boolean registered = Boolean.parseBoolean(req.getParameter("registered"));

                customer.setAddress(address);
                customer.setAge(age);
                customer.setRegistered(registered);

                db.Customers().update(customer);
            } else if ("staff".equals(userType)) {
                Staff staff = db.Staff().getById(userId);
                staff.setEmail(email);
                staff.setPassword(password);
                staff.setFName(fName);
                staff.setLName(lName);
                staff.setPhoneNumber(phoneNumber);
                staff.setActive(isActive);

                String role = req.getParameter("role");
                staff.setRole(role);

                db.Staff().update(staff);
            }


            resp.sendRedirect("viewUsers.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("viewUsers.jsp");
        }
    }

}
