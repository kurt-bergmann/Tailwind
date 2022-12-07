
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    
    <body>
        
        <nav>
            <ul>
                <li><a href="home">Home</a></li>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </nav>
        
        <h1>HOME nVentory</h1>
        
        <h3>
            <c:if test="${newUser}">
                Welcome new user!
            </c:if>
            <c:if test="${currentUser}">
                Welcome back!
            </c:if>
        </h3>
        
        
        
        
    </body>
</html>
