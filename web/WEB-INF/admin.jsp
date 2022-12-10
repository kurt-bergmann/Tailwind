<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory Administrator</title>
    </head>
    <body>

        <nav>
            <ul>
                <li><a href="admin">Manage Users</a></li>
                <li><a href="account">Account</a></li>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </nav>

        <h1>Manage Users</h1>

        <%-- Inform the user if the database is empty --%>
        <c:if test="${users.size() == 0}">
            <h3>No users found. Please add a user</h3>
        </c:if>

        <%-- Display all users in a table if the database has users --%>
        <c:if test="${users.size() >= 1}">    
            <table>
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Active</th>
                    <th>Role</th>
                    <th colspan="1"></th>
                </tr>

                <%-- Display a row for each user in the database  --%>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <%-- User data --%>
                        <td>${user.getEmail()}</td>
                        <td>${user.getFirstName()}</td>
                        <td>${user.getLastName()}</td>
                        <td>${user.getActive()}</td>
                        <td>${user.getRole().getRoleName()}</td>

                        <%-- Edit Link --%>
                        <td>
                            <button>
                                <a style="text-decoration: none; color: black" 
                                   href="
                                   <c:url value='admin'>
                                       <c:param name='edit' value='${user.getEmail()}' />
                                   </c:url>
                                   ">
                                    Edit
                                </a>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>  

        <%-- Display header depending on selected functionality --%>
        <h2> ${editUser ? "Edit User" : "Add User"} </h2>

        <%-- Add or edit user form --%>
        <form action="admin" method="post">

            <%-- E-mail input  --%>
            Email: <input type="text" name="email" 
                          value="${editUser ? userToEdit.getEmail() : inputEmail}" required>

            <br>

            <%-- Name input --%>
            <%-- If in edit mode name inputs are filled in with the selected user--%>
            First name: <input type="text" name="firstName" 
                               value="${editUser ? userToEdit.getFirstName() : inputFirstName}" required>

            <br>

            Last name: <input type="text" name="lastName" 
                              value="${editUser ? userToEdit.getLastName() : inputLastName}" required>

            <br>

            Password: <input type="password" name="password" required>

            <br>

            <%-- Active input for edit user --%>
            Active: 
            <select name="active">
                <option value="true" ${userToEdit.getActive() ? "selected": ""}>True</option>
                <option value="false" ${!userToEdit.getActive() ? "selected": ""}>False</option>
            </select>

            <br>

            <%-- If in edit mode the role is set to the role of the selected user --%>
            Role: 
            <select name="role">
                <%-- Populate the selections based on the data from the role table --%>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.getRoleId()}"
                            ${(userToEdit.getRole().getRoleId() == role.getRoleId())  ? "selected" : ""}>
                        ${role.getRoleName()}
                    </option>
                </c:forEach>
            </select>

            <br>

            <%-- Update and delete user. Or cancel changes --%>
            <c:if test="${editUser}">
                <input type="submit" value="Save changes" name="action">
                <button>
                    <a style="text-decoration: none; color: black" href="admin?action=cancel">
                        Cancel
                    </a>
                </button>

                <br><br>

                <input type="submit" name="action" value="Delete account">
                <input type="hidden" value="${userToEdit.getEmail()}" name="email">
            </c:if>

            <%-- Add user --%>
            <c:if test="${editUser == null}">
                <input type="submit" value="Add account" name="action">
            </c:if>

            <br>

            <%-- Error message  --%>
            ${emailAlreadyExists ? "E-mail already in use" : ""}

        </form>

    </body>
</html>
