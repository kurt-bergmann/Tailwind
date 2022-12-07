package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// Based on Week 9 demo 
public class DBUtil {

   private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
        Persistence.createEntityManagerFactory("InventoryPU");
     
    
    public static EntityManagerFactory getEmFactory() {
        return ENTITY_MANAGER_FACTORY;
    }

}