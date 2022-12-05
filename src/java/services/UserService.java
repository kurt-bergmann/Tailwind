package services;

import dataaccess.UserDB;
import models.User;


public class UserService {

    public static String login(String email, String password) {
        try {
            // See if there is a user in the DB with a matching username
            User user = new UserDB().findUser(email);
            
            // If there is a user in the DB with a matching username, check the validity of the
            // inputted password
            if (user != null) {
                boolean passwordMatch = user.getPassword().equals(password);
                
                // If the passwords match return the user's email
                if (passwordMatch) {
                    return user.getEmail();
                }
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
}
