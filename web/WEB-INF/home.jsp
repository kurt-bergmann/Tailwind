
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
            Welcome ${userFirstName}
        </h3>

        <br>

        <h2>Inventory</h2>

        <c:choose>

            <%-- Edit the user's inventory --%>
            <c:when test="${editInventory}">
                <form method="post" action="home">
                    <table name="inventory">
                        <tr>
                            <th>Item Name</th>
                            <th>Price</th>
                            <th>Category</th>
                            <th>Delete</th>
                        </tr>

                        <%-- Display a row for each item that belongs to the current user--%>
                        <c:forEach var="item" items="${userItems}">
                            <tr>
                                <%-- Item name --%>
                                <td>
                                    <input type="text" name="${item.getItemId()}Name" value="${item.getItemName()}" required>
                                </td>

                                <%-- Item Price --%>
                                <td>
                                    <input type="number" name="${item.getItemId()}Price" value="${item.getPrice()}" min="0"
                                           step="0.01">
                                </td>

                                <%-- Item categories --%>
                                <td>
                                    <select name="${item.getItemId()}Category">
                                        <c:forEach var="category" items="${categories}">
                                            <option  value="${category.getCategoryId()}"  
                                                     <%-- Set the default selected category to the items current category --%>
                                                     ${(item.getCategory().getCategoryId() == category.getCategoryId()) ?
                                                       "selected" : ""}>  

                                                ${category.getCategoryName()}
                                            </option>
                                        </c:forEach>                                                      
                                    </select>
                                </td>

                                <%-- Delete item --%>
                                <td>
                                    <input type="checkbox" name="${item.getItemId()}Delete" value="delete">
                                </td>
                            </tr>
                        </c:forEach>
                    </table> 

                    <input type="submit" value="Add Item" name="action">
                    <br>
                    <input type="submit" value="Save Changes" name="action">
                    <button>
                        <a href="home" style="text-decoration: none; color: black">Cancel</a>
                    </button>
                </form>
            </c:when>

            <%-- Just view inventory --%>
            <c:when test="${userItems.size() > 0}">
                <table>
                    <tr>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Category</th>
                    </tr>

                    <%-- Display a row for each item that belongs to the current user--%>
                    <c:forEach var="item" items="${userItems}">
                        <tr>
                            <td>
                                ${item.getItemName()}
                            </td>

                            <td>
                                <fmt:formatNumber minFractionDigits="2" value="${item.getPrice()} "/>
                            </td>

                            <td>
                                ${item.getCategory().getCategoryName()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <button>
                    <a href="home?editInventory" style="text-decoration: none; color: black">
                        Edit Inventory
                    </a>
                </button>
            </c:when>

            <c:otherwise>
                <button>
                    <a href="home?editInventory" style="text-decoration: none; color: black">
                        Create Inventory
                    </a>
                </button>
            </c:otherwise>

        </c:choose>

    </body>
</html>
