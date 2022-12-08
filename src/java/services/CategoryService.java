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
    
}
