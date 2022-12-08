package dataaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.Item;
import models.User;


public class ItemDB {

    public ArrayList<Item> getAllUserItems(String userEmail) {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
         try {
             // Execute a select on items where owner equals userEmail
            List<Item> itemListType;
            itemListType = em.createNamedQuery("Item.findByOwner", Item.class)
                    .setParameter("owner", userEmail).getResultList();
            
            ArrayList<Item> userItems = new ArrayList(itemListType);
            // Return list specific user's items
            return userItems;
            
        } finally {
            em.close();
        }
    }

    public void insertNewItem(Item item) {
         // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Transaction
            trans.begin();

            // Insert the user into the user table
            em.persist(item);

            // User's have multiple items so this new item should be added to the respective user list
            User user = item.getOwner();
            user.getItemList().add(item);
            // update the user table
            em.merge(user);
            
             // Category's have multiple items so this new item should be added to the respective category list
            Category category = item.getCategory();
            category.getItemList().add(item);
            // update the category table
            em.merge(category);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }

    public void updateItem(Item item) {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Transaction
            trans.begin();

            // update the user in the item table
            em.merge(item);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }

    public void deleteItem(Item item) {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Transaction
            trans.begin();

            // Remove the user into the user table
            em.remove(em.merge(item));

            // User's have multiple items so this new item should be added to the respective user list
            User user = item.getOwner();
            user.getItemList().remove(item);
            // update the user table
            em.merge(user);
            
             // Category's have multiple items so this new item should be added to the respective category list
            Category category = item.getCategory();
            category.getItemList().remove(item);
            // update the category table
            em.merge(category);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(ItemDB.class.getName()).log(Level.SEVERE, null, ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }
    
}
