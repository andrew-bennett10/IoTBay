package classes.controller;

import classes.model.Customer;
import classes.model.Staff;
import classes.model.dao.DAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userType = req.getParameter("userType");
        int userId = Integer.parseInt(req.getParameter("userId"));

        System.out.println("delete userType" + userType);
        System.out.println("delete userID:" + userId);

        DAO db = (DAO) req.getSession().getAttribute("db");

        try {
            if ("customer".equals(userType)) {
                Customer customer = db.Customers().getById(userId);
                if (customer != null) {
                    db.Customers().delete(customer);
                }
            } else if ("staff".equals(userType)) {
                Staff staff = db.Staff().getById(userId);
                if (staff != null) {
                    db.Staff().delete(staff);
                }
            }


            resp.sendRedirect("viewUsers.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("viewUsers.jsp");
        }
    }
}
