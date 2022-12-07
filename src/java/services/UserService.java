package services;

import dataaccess.UserDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;
import models.User;


public class UserService {
    
    private static final int SYSTEM_ADMIN = 1;
    private static final int REGULAR_USER = 2;

    /**
     * Login user
     * 
     * @param email
     * @param password
     * @return Existing user email or null if login details fail to find a matching user
     */
    public static String login(String email, String password) {
        try {
            // See if there is a user in the DB with a matching username
            User user = new UserDB().findUser(email);
            
            // If there is a user in the DB with a matching username, check the validity of the
            // inputted password
            // Also check if the user is active
            if (user != null && user.getActive()) {
                boolean passwordMatch = user.getPassword().equals(password);
                
                // If the passwords match return the user's email
                if (passwordMatch) {
                    return user.getEmail();
                }
            }
            
        } catch (Exception ex) {
             Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        
        return null;
    }

    /**
     * Register a new user to the DB, if the e-mail is available. By default set the account
     * to active. Also the new user will always be a regular user
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return The email of the new user
     */
    public static String register(String firstName, String lastName, String email, String password) {
        try {
            // Check if the e-mail is already being used
            User currentUser = new UserDB().findUser(email);
            
            // If the user is null then the email is free
            if (currentUser == null) {
                // add new user to the DB
                Role newUserRole = RoleService.getRole(REGULAR_USER);
                User newUser;
                newUser = new User(email, true, firstName, lastName, password, newUserRole);
                new UserDB().insertNewUser(newUser);
                
                return newUser.getEmail();
            }
           
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        
        return null;
    }
    
}
