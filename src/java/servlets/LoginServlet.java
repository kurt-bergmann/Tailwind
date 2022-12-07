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

        HttpSession session = request.getSession();
        session.invalidate();

        if (logout != null) {
            // Logout user
            request.setAttribute("logout", "true");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean register;
        boolean invalid = true;
        switch (action) {
            case ("User Login"):
                // Set register to false, then return to login page with login form
                register = false;
                request.setAttribute("register", register);
                break;

            case ("Register New User"):
                // Set register to true, then return to login page with register form
                register = true;
                request.setAttribute("register", register);
                break;

            case ("Login"):
                // Exisiting user login
                if (email.equals("") || password.equals("")) {
                    request.setAttribute("invalid",  "All fields must be filled in");

                } else {
                    // If the login details are correct, retrieve the corresponding user email
                    String userEmail = UserService.login(email, password);

                    invalid = (userEmail == null);
                    if (invalid) {
                        // Inform the user if their login failed
                        request.setAttribute("invalid", "Invalid login details");
                    } else {
                        HttpSession session = request.getSession();
                        
                        // Set the session variable for email and name
                        session.setAttribute("userEmail", userEmail);
                        session.setAttribute("userFirstName", UserService.getFirstName(email));
                        session.setAttribute("userFirstName", UserService.getLastName(email));
                        
                        // Set welcome message for home page
                        session.setAttribute("currentUser", true);
                    }
                }
                break;

            case ("Register"):
                // Register new user
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                
                if (email.equals("") || password.equals("") || firstName.equals("") || lastName.equals("")) {
                    request.setAttribute("invalid", "All fields must be filled in");
                
                } else {
                    // Add new user
                    String newUserEmail = UserService.register(firstName, lastName, email, password);
                    
                    invalid = (newUserEmail == null);
                    if (invalid) {
                        // Inform the user if their registration attempt failed
                        request.setAttribute("invalid", "The e-mail you are using is already belongs to a user");
                        request.setAttribute("firstName", firstName);
                        request.setAttribute("lastName", lastName);
                    } else {
                        HttpSession session = request.getSession();
                        
                        // Set the session variable for email and name
                        session.setAttribute("userEmail", newUserEmail);
                        session.setAttribute("userFirstName", UserService.getFirstName(email));
                        session.setAttribute("userFirstName", UserService.getLastName(email));
                        
                        // Inform the user that their registration was successfull on the home page
                        session.setAttribute("newUser", true);
                    }
                }
                break;
                
            default:
                System.out.println("Default");
        }

        if (invalid == true) {
            // Send to login page if registration or login attempt failed
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

        } else {
            response.sendRedirect("home");
        }
    }
}