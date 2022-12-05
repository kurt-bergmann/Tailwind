<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>

        <c:if test="${invalid != null}">
            ${invalid} 
            <br>
         </c:if>
        <c:if test="${logout != null}">
            Logout successful 
            <br>
         </c:if>   
        
             <br>
             
        <form method="post" action="login">
            E-mail: <input type="text" name="email" value="${email}">
            <br>
            Password: <input type="password" name="password" value="${password}">
            <br>
            
            <input type="submit" value="Login">
            <input type="hidden" name="action" value="login">
        </form>

    </body>
</html>
