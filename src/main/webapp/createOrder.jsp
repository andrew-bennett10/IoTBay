<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Place Order</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body class="main-page">
<nav class="navigation">
    <ul>
        <li class="specialNav">IoTBay Digital Services</li>
        <li><a href="main.jsp">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="products.jsp">Products</a></li>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="createOrder.jsp">Create Order</a></li>
        <li><a href="logout.jsp">Logout</a></li>
    </ul>
</nav>

<main>
    <div class="payment-container">
        <h2>Place Order</h2>
        <form action="OrderServlet" method="post">
            <div>
                <label for="productId">Choose a product:</label>
                <select name="productId" id="productId" required>
                    <option value="1">Sensor - $12.50</option>
                    <option value="2">Actuator - $9.45</option>
                    <option value="3">Gateway - $4.00</option>
                </select>
            </div>

            <div>
                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" min="1" value="1" required>
            </div>

            <div>
                <input type="submit" value="Place Order" class="button">
            </div>
        </form>
    </div>
</main>
</body>
</html>
