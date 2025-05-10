package classes.controller;

import classes.model.AccessLog;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import classes.model.dao.DAO;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DAO db = ((DAO)req.getSession().getAttribute("db"));
        session.removeAttribute("loggedInUser");
        AccessLog currentAccessLog = (AccessLog) session.getAttribute("currentAccessLog");

        currentAccessLog.setLogout(new Date());
        try {
            System.out.println(currentAccessLog.getLogout());
            db.AccessLog().update(((AccessLog) session.getAttribute("currentAccessLog")), currentAccessLog);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            db.AccessLog().endSession();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        session.invalidate();

        resp.sendRedirect("index.jsp");
    }
}