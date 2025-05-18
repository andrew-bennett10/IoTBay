package classes.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/UserTypeSelectServlet")
public class UserTypeSelectServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userType = req.getParameter("userType");
        if("customer".equalsIgnoreCase(userType)){
            resp.sendRedirect("customerCreation.jsp");
        } else if ("staff".equalsIgnoreCase(userType)) {
            resp.sendRedirect("staffCreation.jsp");
        } else {
            req.setAttribute("errorMessage", "Please select a valid user type");
            req.getRequestDispatcher("createUser.jsp").forward(req,resp);
        }
    }
}
