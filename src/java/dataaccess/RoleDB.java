package dataaccess;

import javax.persistence.EntityManager;
import models.Role;

public class RoleDB {

       public Role getRole(int roleID) throws Exception{
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            // Find the role based on it's role id
            Role role = em.find(Role.class, roleID);
            
            // Return the role
            return role;
            
        } finally {
           em.close();
        }
    }
    
    public String getRoleName(int roleID) throws Exception{
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            // Find the role based on it's role id
            Role role = em.find(Role.class, roleID);
            
            // Return the role name
            return role.getRoleName();
            
        } finally {
           em.close();
        }
    }
    
}