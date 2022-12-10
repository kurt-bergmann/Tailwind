
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory Account</title>

        <script>
            function confirmDeactivate() {
                var warning = ("Are you sure you want to deactivate your account?");

                if (confirm(warning)) {
                    document.getElementById("deactivateAccount").setAttribute("value", "true");
                }
            }
        </script>

    </head>
    <body>

        <nav>
            <ul>
                <c:if test="${adminUser}">
                    <li><a href="admin">Manage Users</a></li>
                    <li><a href="admin">Manage Categories</a></li>
                </c:if>
                <li><a href="home">HOME nVentory</a></li>
                <li><a href="account">Account</a></li>
                <li><a href="login?logout">Logout</a></li>
            </ul>
        </nav>

        <h1>Account Settings</h1>

        <p>
            ${userFirstName} edit your account details below
        </p>

        <br>

        <form method="post" action="account" id="accountForm">
            <%-- Edit user name --%>
            <h3>Name</h3>
            <p> ${nameResponse}</p>
            First Name: <input type="text" name="firstName">
            <br>
            Last Name: <input type="text" name="lastName">
            <br>
            <input type="submit" name="action" value="Change Name"> <input type="reset" value="Reset">

            <br>

            <%-- Edit user password --%>
            <h3>Password</h3>
            <p>${passwordResponse}</p>
            Old Password: <input type="password" name="oldPassword">
            <br>
            New Password: <input type="password" name="newPassword">
            <br>
            Confirm Password: <input type="password" name="confirmNewPassword">
            <br>
            <input type="submit" name="action" value="Change Password"> <input type="reset" value="Cancel">   

            <br>

            <%-- Edit user email --%>
            <h3>E-mail</h3>
            <p>${emailResponse}</p>
            New E-mail: <input type="text" name="newEmail">
            <br>
            Confirm E-mail: <input type="text" name="confirmNewEmail">
            <br>
            <input type="submit" name="action" value="Change Email">

            <br>

            <%-- Only show deactivate account for regular users --%>
            <c:if test="${adminUser == null}">
                <h3>Deactivate Account</h3>
                <p>
                    You will not be able to log in once your account has been deactivated
                    <br>
                    To reactivate your account you will need to email a system adminstrator
                </p>

                <input type="submit" name="action" value="Deactivate Account" onclick="confirmDeactivate()">
                <input id="deactivateAccount" name="confirmDeactivateAccount" type="hidden">
            </c:if>

        </form> 

    </body>
</html>
