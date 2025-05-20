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
            if (customer != null) { // Customer account found
                session.setAttribute("userType", "customer");
                session.setAttribute("loggedInUser", customer);
                session.setAttribute("name", customer.getName());
                Date currentDate = new Date();
                AccessLog accessLog = new AccessLog(customer.getId(), currentDate, null, false);
                session.setAttribute("currentAccessLog", accessLog);
                db.AccessLog().add(accessLog);
                session.setAttribute("email",customer.getEmail());
                session.setAttribute("password",customer.getPassword());
                session.setAttribute("fName",customer.getFName());
                session.setAttribute("lName",customer.getLName());
                session.setAttribute("age",customer.getAge());
                session.setAttribute("address",customer.getAddress());
                session.setAttribute("registered",customer.getRegistered());
                session.setAttribute("phoneNumber",customer.getPhoneNumber());
                session.setAttribute("isActive",customer.getActive());
                resp.sendRedirect("welcome.jsp");
            } else {
                System.out.println("Running staff section");
                StaffDBManager staffDBManager = db.Staff();
                Staff staff = staffDBManager.get(email, password);

                if (staff != null) { // Staff account found
                    System.out.println("account found");
                    session.setAttribute("userType", "staff");
                    session.setAttribute("loggedInUser", staff);
                    session.setAttribute("name", staff.getName());
                    Date currentDate = new Date();
                    AccessLog accessLog = new AccessLog(staff.getId(), currentDate, null, true);
                    session.setAttribute("currentAccessLog", accessLog);
                    db.AccessLog().add(accessLog);

                    session.setAttribute("email",staff.getEmail());
                    session.setAttribute("password",staff.getPassword());
                    session.setAttribute("fName",staff.getFName());
                    session.setAttribute("lName",staff.getLName());
                    session.setAttribute("role",staff.getRole());
                    session.setAttribute("phoneNumber",staff.getPhoneNumber());
                    resp.sendRedirect("welcome.jsp");
                }
                else {
                    System.out.println("Login attempt failed - user not found");
                    // Invalid login
                    req.setAttribute("errorMessage", "Invalid email or password");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}