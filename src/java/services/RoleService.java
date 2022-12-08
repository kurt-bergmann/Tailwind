package services;

import dataaccess.RoleDB;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;

public class RoleService {

    // Find the role based on it ID
    public static Role getRole(int roleID) {
        Role role;
        
        try {
           role = new RoleDB().getRole(roleID);
           
       } catch (Exception ex) {
           // Set the role name to null if the database could not be reached
           Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
           role = null;
       }
              
       return role;
    }

    // Find the a role name based on its ID
    public static String findRoleName(int roleID) {
       String roleName;
       
       try {
           roleName = new RoleDB().getRoleName(roleID);
           
       } catch (Exception ex) {
           // Set the role name to null if the database could not be reached
           Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
           roleName = "null";
       }
              
       return roleName;
    }
    
    // Retrieve all data from about roles from the database
    public static ArrayList<Role> getAllRoles() {
        ArrayList<Role> roles;
        
        try{
            roles = new RoleDB().getAllRoles();
            
        } catch (Exception ex) {
             // Return null if the database could not be reached
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            roles = null;
        }
        
      return roles;   
    }

}