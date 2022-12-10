package services;

import dataaccess.UserDB;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Item;
import models.Role;
import models.User;


public class UserService {

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
            User user = getUser(email);
            
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
        final int REGULAR_USER = 2;
        
        try {
            // Check if the e-mail is already being used
            User currentUser = getUser(email);
            
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

    /**
     * Get user's first name
     * 
     * @param email user's email
     * @return user first name
     */
    public static String getFirstName(String email) {
        try {
            return getUser(email).getFirstName();
            
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * Get user's last name
     * 
     * @param email user's email
     * @return user last name
     */
    public static String getLastName(String email) {
        try {
            return getUser(email).getLastName();
            
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    /**
     * Get user
     * 
     * @param userEmail user's email
     * @return full user object
     */
   public static User getUser(String userEmail) {
        try {
            return new UserDB().findUser(userEmail);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   /**
    * Change a user's full name
    * 
    * @param firstName
    * @param lastName
    * @param email
    * @return true if the name was changed false if it was not
    */
    public static boolean changeName(String firstName, String lastName, String email) {
            User user = getUser(email);
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            
          try { 
            new UserDB().updateUser(user);
            
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Check the validity of a password
     * 
     * @param password
     * @param email
     * @return 
     */
    public static boolean checkPassword(String password, String email) {
        User user = getUser(email);
        
        return user.getPassword().equals(password);
    }

    /**
     * Change a users password
     * 
     * @param newPassword
     * @param email
     * @return 
     */
    public static boolean changePassword(String newPassword, String email) {
        User user = getUser(email);
        
        user.setPassword(newPassword);
        
        try {
            new UserDB().updateUser(user);
            return true;
            
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Change a user's email address
     * 
     * @param newEmail
     * @param oldEmail
     * @return true if change succeeded, false if it failed
     */
    public static boolean changeEmail(String newEmail, String oldEmail) {
        // Check if email is available
        if (getUser(newEmail) != null) {
            return false;
        } else {
            // Get the current user
            User user = getUser(oldEmail);
            user.setEmail(newEmail);
            
            // To update a user's email the items table also needs to be changed
            ArrayList<Item> userItems = ItemService.getAllUserItems(oldEmail);
            
            for (int x = 0; x < userItems.size(); x++) {
               Item item = userItems.get(x);
               item.setOwner(user);
               userItems.set(x, item);
            }
            
            
            try {
                // Change the current user's email address
                new UserDB().updateUser(user);
                ItemService.updateUserItems(userItems);
                
                 // Remove the old user from the database
                new UserDB().deleteUser(getUser(oldEmail));
                
                return true;
                
            } catch (Exception ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }

    /**
     * Check the active status of a user's account
     * 
     * @param email
     * @return true if user is active, false if the account has been deactivated
     */
    public static boolean checkActive(String email) {
        User user = getUser(email);
        return user.getActive();
    }

    /**
     * Deactivate a user's account
     * 
     * @param email 
     */
    public static void deactivateAccount(String email) {
       User user = getUser(email);
       user.setActive(false);
        try {
            new UserDB().updateUser(user);
        
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get the user's role id
     * 
     * @param email
     * @return user role id integer
     */
    public static int getUserRoleId(String email) {
        User user = getUser(email);
        return user.getRole().getRoleId();
    }

    /**
     * Get an ArrayList of all the users in the database
     * 
     * @return A list of all the users, or null if something went wrong
     */
    public static ArrayList<User> getAllUsers() {
       try {
           return new UserDB().getAllUsers();
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }

    public static boolean addAccount(String email, String firstName, String lastName, String password, 
            boolean acitve, int roleId) {
        Role role = RoleService.getRole(roleId);
        User user = new User(email, acitve, firstName, lastName, password, role);
        
        try {
            new UserDB().insertNewUser(user);
            return true;
            
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
