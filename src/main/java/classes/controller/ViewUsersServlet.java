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

@WebServlet("/ViewUsersServlet")
public class ViewUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAO db = (DAO) req.getSession().getAttribute("db");

        String searchFName = req.getParameter("fName");
        String searchLName = req.getParameter("lName");
        String searchPhoneNumber = req.getParameter("phoneNumber");

        String viewAll = req.getParameter("viewAll");

        List<Customer> customers = new ArrayList<>();
        List<Staff> staffMembers = new ArrayList<>();


        if(searchFName != null || searchLName != null || searchPhoneNumber != null){
            try {
                customers = db.Customers().get(searchFName, searchLName, searchPhoneNumber);
                staffMembers = db.Staff().get(searchFName, searchLName, searchPhoneNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                customers = db.Customers().getAll();
                staffMembers = db.Staff().getAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        req.setAttribute("customers", customers);
        req.setAttribute("staffMembers", staffMembers);
        req.getRequestDispatcher("viewUsers.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
