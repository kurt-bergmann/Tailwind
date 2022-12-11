package dataaccess;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;


public class CategoryDB {
    
    public ArrayList<Category> getAllCategories() {
        // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            // Find all of the categories in the database
            List<Category> categoriesList = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            ArrayList<Category> categories = new ArrayList(categoriesList);
            // Return list of categories
            return categories;
            
        } finally {
            em.close();
        }
    }

    public Category getCategory(int categoryId) {
         // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            // Find the role based on it's role id
            Category category = em.find(Category.class, categoryId);
            
            // Return the role
            return category;
            
        } finally {
           em.close();
        }
    }

    public void insertCategory(Category category) {
       // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Transaction
            trans.begin();

            // Insert the category into the user table
            em.persist(category);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(CategoryDB.class.getName()).log(Level.SEVERE, null, ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }

    public void updateCategory(Category category) {
       // Instantiate EntityManager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        // Instantitate EntityTransaction so DML can be executed
        EntityTransaction trans = em.getTransaction();

        try {
            // Transaction
            trans.begin();

            // update the category in the user table
            em.merge(category);

            trans.commit();

        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            // Rollback if there is an error
            trans.rollback();

        } finally {
            em.close();
        }
    }
}
