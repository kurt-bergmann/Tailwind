package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.UserService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Logout user
        String logout = request.getParameter("logout");

        if (logout != null) {
            // Logout user
            HttpSession session = request.getSession();
            session.invalidate();
            request.setAttribute("logout", "true");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        boolean invalid = true;

        // Default role id set to regular user
        int userRoleId = 2;

        switch (action) {
            case ("User Login"):
                // Set register to false, then return to login page with login form
                request.setAttribute("register", false);
                break;

            case ("Register New User"):
                // Set register to true, then return to login page with register form
                request.setAttribute("register", true);
                break;

            case ("Login"):
                // If the login details are correct, retrieve the corresponding user email
                String userEmail = UserService.login(email, password);

                // If null is returned then the password was incorrect, or no user could be found
                invalid = (userEmail == null);
                if (invalid) {
                    // Inform the user if their login failed
                    request.setAttribute("invalid", "Invalid login details");
                } else {
                    // Check if the user is an admin
                    userRoleId = UserService.getUserRoleId(email);
                }

                // Check if user account is active
                boolean userIsActive = UserService.checkActive(email);
                if (!userIsActive) {
                    // A user can't log in to their account if it's inactive
                    invalid = true;
                    request.setAttribute("invalid", "This account has been deactivated<br>"
                            + "Please contact a system administrator to reactivate your account");
                }
                break;

            case ("Register"):
                String newUserEmail = UserService.register(firstName, lastName, email, password);

                // If null is return then a user was not created
                invalid = (newUserEmail == null);
                if (invalid) {
                    // Inform the user if their registration attempt failed
                    request.setAttribute("invalid", "The e-mail you are using is already belongs to a user");

                    // Keep the user on the register form
                    request.setAttribute("register", true);
                }
                break;

            default:
                System.out.println("Default");
        }

        if (invalid == true) {
            // Keep the user inputs
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("password", password);

            // Send to login page if registration or login attempt failed
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

        } else if (userRoleId == 2) {
            // Set the session variable for email and name
            session.setAttribute("userEmail", email);
            session.setAttribute("userFirstName", UserService.getFirstName(email));
            session.setAttribute("userLastName", UserService.getLastName(email));

            // Send regular user to their home page
            response.sendRedirect("home");

        } else if (userRoleId == 1) {
            // Set the session variable for email and name
            session.setAttribute("userEmail", email);
            session.setAttribute("adminUser", true);
            session.setAttribute("userFirstName", UserService.getFirstName(email));
            session.setAttribute("userLastName", UserService.getLastName(email));

            // Send the system admin to the admin page
            response.sendRedirect("admin");
        }
    }
}
