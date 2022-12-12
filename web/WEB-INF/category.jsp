<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:import url="style.jsp" />
        <title>HOME nVentory Administrator Categories</title>
    </head>
    <body>

        <nav>
            <ul>
                <li><a href="admin">Manage Accounts</a></li>
                <li><a class="active" href="category">Manage Categories</a></li>
                <li><a href="home">HOME nVentory</a></li>
                <li><a href="account">Account</a></li>
                <li style="float: right"><a href="login?logout">Logout</a></li>
            </ul>
        </nav>
        <main>

            <p class="userName">
                Current user: ${userFirstName}
            </p>   

            <h1>Manage Categories</h1>

            <form action="category" method="post">
                <table>
                    <%-- Table headers --%>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                    </tr>

                    <%-- View Categories --%>
                    <c:if test="${editCategories == null}">
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>${category.getCategoryId()}</td>
                                <td>${category.getCategoryName()}</td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    <%-- Edit categories --%>
                    <c:if test="${editCategories}">
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>${category.getCategoryId()}</td>
                                <td>
                                    <input type="text" name="${category.getCategoryId()}CategoryName" 
                                           value="${category.getCategoryName()}" required>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                </table>

                <%-- Select edit categories --%>
                <c:if test="${editCategories == null}">
                    <br>

                    <input type="submit" name="action" value="Edit Categories">
                </c:if>

                <%-- Edit categories --%>
                <c:if test="${editCategories}">
                    <%-- Add category --%>
                    <input type="submit" value="Add Category" name="action">

                    <br><br>

                    <%-- Save changes, or cancel --%>
                    <input type="submit" value="Save Changes" name="action">
                    <button>
                        <a href="category" style="text-decoration: none; color: black">Cancel</a>
                    </button>
                </c:if>

            </form>

            <br>
            ${categoriesChanged ? "Changes to categories were saved" : ""}
            <main>
                </body>
                </html>
