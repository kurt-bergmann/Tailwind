
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:import url="style.jsp" />
        <title>HOME nVentory</title>     
    </head>

    <body>

        <nav>
            <ul>
                <c:if test="${adminUser}">
                    <li><a href="admin">Manage Accounts</a></li>
                    <li><a href="category">Manage Categories</a></li>
                    </c:if>
                <li><a class="active" href="home">HOME nVentory</a></li>
                <li><a href="account">Account</a></li>
                <li style="float: right"> <a href="login?logout">Logout</a></li>
            </ul>
        </nav>

        <main>
            <p class="userName">
                Current user: ${userFirstName} ${userLastName}
            </p>   


            <h1>HOME nVentory</h1>

            <p>
                Welcome ${userFirstName}
            </p>

            <br>

            <h2>Inventory</h2>

            <c:choose>

                <%-- Edit the user's inventory --%>
                <c:when test="${editInventory}">
                    <form method="post" action="home">
                        <table>

                            <%-- Only display headers if there is at least one item in the user's inventory --%>
                            <c:if test="${userItems.size() >= 1}">
                                <tr>
                                    <th>Item Name</th>
                                    <th>Price</th>
                                    <th>Category</th>
                                    <th>Delete</th>
                                </tr>
                            </c:if>

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
                                        <input type="checkbox" name="${item.getItemId()}Delete" value="delete" class="delete">
                                    </td>
                                </tr>
                            </c:forEach>
                        </table> 

                        <%-- Add item --%>
                        <input type="submit" value="Add Item" name="action">

                        <br><br>

                        <%-- Save changes, or cancel --%>
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
                    <p>
                        You currently have no items in your inventory
                    </p>
                    <button>
                        <a href="home?editInventory" style="text-decoration: none; color: black">
                            Create Inventory
                        </a>
                    </button>
                </c:otherwise>

            </c:choose>

        </main>
    </body>
</html>
