package classes.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


import java.util.HashMap;
import java.util.Map;


@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));


        Map<Integer, String> productNames = new HashMap<>();
        Map<Integer, Double> productPrices = new HashMap<>();

        productNames.put(1, "Sensor");
        productPrices.put(1, 12.50);

        productNames.put(2, "Actuator");
        productPrices.put(2, 9.45);

        productNames.put(3, "Gateway");
        productPrices.put(3, 4.00);


        String productName = productNames.get(productId);
        double unitPrice = productPrices.get(productId);


        double totalPrice = unitPrice * quantity;


        HttpSession session = request.getSession();
        session.setAttribute("productName", productName);
        session.setAttribute("unitPrice", unitPrice);
        session.setAttribute("quantity", quantity);
        session.setAttribute("totalPrice", totalPrice);
        RequestDispatcher dispatcher = request.getRequestDispatcher("payment.jsp");
        dispatcher.forward(request, response);




    }
}


