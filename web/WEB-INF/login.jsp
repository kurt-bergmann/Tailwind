<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory Login</title>
    </head>
    <body>

        <%-- Display header depending on selected functionality --%>
        <h1> ${register ? "Register" : "Login"} </h2>

        <c:if test="${invalid != null}">
            ${invalid} 
            <br>
        </c:if>
        <c:if test="${logout != null}">
            Logout successful 
            <br>
        </c:if>   

        <br>

        <c:choose>
            <c:when test="${register}">
                <%-- Register new user --%>
                <form method="post" action="login">
                    First Name: <input type="text" name="firstName" value="${firstName}" required>
                    <br>
                    Last Name: <input type="text" name="lastName" value="${lastName}" required>
                    <br>
                    E-mail: <input type="text" name="email" value="${email}" required>
                    <br>
                    Password: <input type="password" name="password" value="${password}" required>
                    <br>
                    <input type="submit" name="action" value="Register">
                </form>

                <br><br>

                <form method="post" action="login">
                    <input type="submit" name="action" value="User Login">
                </form>    
            </c:when>

            <c:otherwise>
                <%-- User login --%>
                <form method="post" action="login">
                    E-mail: <input type="text" name="email" value="${email}" required>
                    <br>
                    Password: <input type="password" name="password" value="${password}" required>
                    <br>
                    <input type="submit" name="action" value="Login">
                </form>

                <br><br>

                <form method="post" action="login">
                    <input type="submit" name="action" value="Register New User">
                </form>    
            </c:otherwise>
        </c:choose>

</body>
</html>
