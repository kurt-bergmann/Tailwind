package services;

import dataaccess.RoleDB;
import models.Role;

public class RoleService {

    // Find the role based on it ID
    public static Role getRole(int roleID) {
        Role role;
        
        try {
           role = new RoleDB().getRole(roleID);
           
       } catch (Exception ex) {
           // Set the role name to null if the database could not be reached
           System.out.println(ex);
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
           System.out.println(ex);
           roleName = "null";
       }
              
       return roleName;
    }


}