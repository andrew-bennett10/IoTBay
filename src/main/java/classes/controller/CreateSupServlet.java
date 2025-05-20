package classes.controller;

import classes.model.Supplier;
import classes.model.dao.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CreateSupServlet")
public class CreateSupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DAO db = (DAO) session.getAttribute("db");
        String ContactName = req.getParameter("ConName");
        String Company = req.getParameter("company");
        String Email = req.getParameter("Email");
        String SupplierStatus = req.getParameter("SuppStat");
        String errorMessage = null;


        if (errorMessage == null && !Email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage = "Invalid email format.";
        }

        if (errorMessage == null) {
            try {
                Supplier newSup = new Supplier(ContactName, Company, Email, SupplierStatus);
                db.Suppliers().add(newSup);
            } catch (SQLException e) {
                errorMessage = "Error creating user: " + e.getMessage();
                e.printStackTrace();
            }
        }

        resp.sendRedirect("main.jsp");
        }

    }




