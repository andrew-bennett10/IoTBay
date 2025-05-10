package classes.controller;

import classes.model.AccessLog;
import classes.model.Customer;
import classes.model.Staff;
import classes.model.User;
import classes.model.dao.CustomerDBManager;
import classes.model.dao.DAO;

import classes.model.dao.StaffDBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DAO db = ((DAO)req.getSession().getAttribute("db"));
        HttpSession session = req.getSession();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            CustomerDBManager customerDBManager = db.Customers();
            Customer customer = customerDBManager.get(email, password);
            if (customer.getEmail() != null) { // Customer account found
                session.setAttribute("userType", "customer");
                session.setAttribute("loggedInUser", customer);
                session.setAttribute("name", customer.getName());
                Date currentDate = new Date();
                AccessLog accessLog = new AccessLog(customer.getId(), currentDate, null, false);
                session.setAttribute("currentAccessLog", accessLog);
                db.AccessLog().add(accessLog);
            } else {
                StaffDBManager staffDBManager = db.Staff();
                Staff staff = staffDBManager.get(email, password);

                if (staff.getEmail() != null) { // Staff account found
                    session.setAttribute("userType", "staff");
                    session.setAttribute("loggedInUser", staff);
                    Date currentDate = new Date();
                    AccessLog accessLog = new AccessLog(staff.getId(), currentDate, null, true);
                    session.setAttribute("currentAccessLog", accessLog);
                    db.AccessLog().add(accessLog);

                }
                else {
                    // Invalid login
                    req.setAttribute("errorMessage", "Invalid email or password");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String userType = (String) session.getAttribute("userType");
        User user = (User) session.getAttribute("loggedInUser");

        if ("customer".equals(userType)) {
            Customer customer = (Customer) user;
            String name = customer.getFName()+" "+customer.getLName();
            session.setAttribute("name", name);
        } else if ("staff".equals(userType)) {
            Staff staff = (Staff) user;
            String name = staff.getFName()+" "+staff.getLName();
            session.setAttribute("name", name);
        } else {
            req.setAttribute("errorMessage", "Error finding user.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        resp.sendRedirect("welcome.jsp");
    }
}