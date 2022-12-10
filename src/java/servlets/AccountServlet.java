package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.ItemService;
import services.UserService;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the current user's email so that all changes will be own the right user
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");

        String action = request.getParameter("action");

        switch (action) {
            case ("Change Name"):
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");

                if (firstName.equals("") || lastName.equals("")) {
                    request.setAttribute("nameResponse", "Both fields need to be filled in");
                } else {
                    if (UserService.changeName(firstName, lastName, userEmail)) {
                        request.setAttribute("nameResponse", "Your name has been changed");

                        // Reset name variables
                        session.setAttribute("userFirstName", firstName);
                        session.setAttribute("userLastName", lastName);
                    } else {
                        request.setAttribute("nameResponse", "Something wen't wrong");
                    }
                }
                break;

            case ("Change Password"):
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmNewPassword");

                // Validate data
                if (oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
                    request.setAttribute("passwordResponse", "All fields need to be filled in");

                } else if (!UserService.checkPassword(oldPassword, userEmail)) {
                    // Check that the old password is correct
                    request.setAttribute("passwordResponse", "Old password is incorrect");

                } else if (!newPassword.equals(confirmPassword)) {
                    // Check that the new password matches the confirm password entry
                    request.setAttribute("passwordResponse", "New password does not match confirmation");

                } else {
                    // Change the user's password
                    if (UserService.changePassword(newPassword, userEmail)) {
                        request.setAttribute("passwordResponse", "Your password has been changed");
                    } else {
                        request.setAttribute("passwordResponse", "Something wen't wrong");
                    }
                }
                break;

            case ("Change Email"):
                String newEmail = request.getParameter("newEmail");
                String confirmEmail = request.getParameter("confirmNewEmail");

                // Validate data
                if (newEmail.equals("") || confirmEmail.equals("")) {
                    request.setAttribute("emailResponse", "All fields must be filled in");
                    
                } else if (!newEmail.equals(confirmEmail)) {
                  request.setAttribute("emailResponse", "New e-mail does not match confirmation");
                  
                } else {
                    boolean emailChanged = UserService.changeEmail(newEmail, userEmail);

                    if (emailChanged) {
                        request.setAttribute("emailResponse", "Your email has been changed");
                        
                        // Update session variables
                        session.setAttribute("userEmail", newEmail);
                        session.setAttribute("userItems", ItemService.getAllUserItems(newEmail));
                        
                    } else {
                        request.setAttribute("emailResponse", "That email is already being used by another account");
                    }
                }
                break;

            case ("Deactivate Account"):
                // Confirm that the user wants to deactivate their account
                String deactivateConfirmation = request.getParameter("confirmDeactivateAccount");
                
                if (deactivateConfirmation.equals("true")) {
                    UserService.deactivateAccount(userEmail);
                    
                    // Invalidate session, and send the user to the login page
                    session.invalidate();
                    request.setAttribute("invalid", "Your account has been deactivated");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
                
                
                break;

            default:
                System.out.println("default");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);

    }

}
