<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <style type="text/css">
        table{
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body>
    <div>
        <form action="welcome.jsp" method="get">
            <table>
                <tr>
                    <th colspan="2">Login Information</th>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><label>
                        <input type="text" name="email" required>
                    </label></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><label>
                        <input type="password" name="password" required>
                    </label></td>
                </tr>
                <tr>
                    <td><input type="submit" name="login" value="Login"></td>
                    <td><input type="reset" name="reset" value="Reset"></td>
                </tr>
            </table>
        </form>

    </div>
</body>
</html>
