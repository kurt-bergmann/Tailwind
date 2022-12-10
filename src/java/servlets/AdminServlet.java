package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Retireve all users
        ArrayList<User> users = UserService.getAllUsers();
        session.setAttribute("users", users);

        // Retrieve all roles
        ArrayList<Role> roles = RoleService.getAllRoles();
        session.setAttribute("roles", roles);

        // Check if admin selected a user to edit
        String userToEditEmail = request.getParameter("edit");
        if (userToEditEmail != null) {
            request.setAttribute("editUser", true);

            // Get user requested for edititng
            User userToEdit = UserService.getUser(userToEditEmail);
            request.setAttribute("userToEdit", userToEdit);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         HttpSession session = request.getSession();

        // Retrieve parameter values
        String email = request.getParameter("email");
        String newEmail = request.getParameter("newEmail");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        boolean acitve = request.getParameter("active").equals("true");
        int role = Integer.parseInt(request.getParameter("role"));

        // Retrieve action command
        String action = request.getParameter("action");

        switch (action) {
            case ("Add account"):
                boolean addAccount = UserService.addAccount(newEmail, firstName, lastName, password, acitve, role);
                
                // If add account is false a user was not added to the database
                // Most likely the email is already being used
                if (!addAccount) {
                    // Inform the user of the error
                    request.setAttribute("emailAlreadyExists", true);
                    
                    // Keep user inputs
                    request.setAttribute("inputEmail", newEmail);
                    request.setAttribute("inputFirstName", firstName);
                    request.setAttribute("inputLastName", lastName);
                } else {
                    // reset user table
                    ArrayList<User> users = UserService.getAllUsers();
                    session.setAttribute("users", users);
                }
                break;

            case ("Save changes"):
                boolean editAccount = UserService.updateAccount(email, newEmail, firstName, lastName, password, 
                        acitve, role);
                
                // If editAccount is false the user was not edited
                // Most likely the email is already being used
                if (!editAccount) {
                    // Inform the user of the error
                    request.setAttribute("emailAlreadyExists", true);
                    
                    // Keep user inputs
                    request.setAttribute("inputEmail", email);
                    request.setAttribute("inputFirstName", firstName);
                    request.setAttribute("inputLastName", lastName);
                } else {
                    // reset user table
                    ArrayList<User> users = UserService.getAllUsers();
                    session.setAttribute("users", users);
                }
                break;

            case ("Delete account"):
                break;

            default:
                System.out.println("default");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
