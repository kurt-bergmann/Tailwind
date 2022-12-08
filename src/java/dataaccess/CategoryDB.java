package dataaccess;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
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
}
