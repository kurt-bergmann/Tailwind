package services;

import dataaccess.CategoryDB;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Category;

public class CategoryService {

    public static ArrayList<Category> getAllCategories() {
        return new CategoryDB().getAllCategories();
    }

    public static Category getCategory(int categoryId) {
        Category category;
        
        try {
           category = new CategoryDB().getCategory(categoryId);
           
       } catch (Exception ex) {
           // Set the role name to null if the database could not be reached
           Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
           category = null;
       }
              
       return category;
    }

    public static boolean updateCategories(ArrayList<Category> categories) {
        // Get current category list size
        int currentCategoryListSize = getAllCategories().size();
        
        int counter = 1;
        
        for (Category category : categories) {
           
            if (counter <= currentCategoryListSize) {
                 // Update old categories
                new CategoryDB().updateCategory(category);
                
            } else {
                // Insert new categories
                new CategoryDB().insertCategory(category);
            }
            // Increase counter
            counter ++;
        }
        
        // If the new category list size is the same as the parameter then categories were updated
        return categories.size() == getAllCategories().size();
    }
    
}
