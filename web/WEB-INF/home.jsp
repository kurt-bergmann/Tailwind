
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
                <li><a href="account">Account</a></li>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </nav>
        
        <h1>HOME nVentory</h1>
        
        <h3>
            <c:if test="${newUser}">
                Registration successful
                <br>
                Welcome ${userFirstName}!
            </c:if>
            <c:if test="${currentUser}">
                Welcome back ${userFirstName}!
            </c:if>
        </h3>
        
        <br>
        
        <h2>Inventory</h2>
        
        <table>
                    <tr>
                        <th>Item Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th colspan="2"></th>
                    </tr>

                    <%-- Display a row for each item that belongs to the current user--%>
                    <c:forEach var="item" items="${userItems}">
                        <tr>
                            <%-- Item data --%>
                            <td>${item.itemName}</td>
                            <td>${user.price}</td>
                            <td>${user.category}</td>

                            <%-- Edit and delete links --%>
                            <td><a href="home?edit=item.itemPK.itemId">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <a href="
                                   <c:url value='users'>
                                       <c:param name='delete' value='${user.email}' />
                                   </c:url>
                                   ">
                                    Delete
                                </a>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
        
    </body>
</html>
