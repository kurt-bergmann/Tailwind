package services;

import dataaccess.ItemDB;
import java.util.ArrayList;
import models.Category;
import models.Item;
import models.User;


public class ItemService {

    public static ArrayList<Item> getAllUserItems(String userEmail) {
       return new ItemDB().getAllUserItems(userEmail);
    }

    public static void updateUserItems(ArrayList<Item> userItems) {
        // Update items that were already in the users inventory and insert the new items
        // Old items will have a ID, new items will have a null ID
        for (Item item : userItems) {
            // Get item ID
            Integer id = item.getItemId();
    
            if (id == null) {
                // New item
                new ItemDB().insertNewItem(item);
                
            } else {
                // Old item
                new ItemDB().updateItem(item);
            }
        }
    }

    public static void deleteUserItems(ArrayList<Item> itemsToDelete) {
        for (Item item : itemsToDelete) {
            new ItemDB().deleteItem(item);
        }
    }
       
}
