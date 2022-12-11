package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import services.CategoryService;
import services.ItemService;
import services.UserService;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get userEmail
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");

        // Retireve all of the users inventory
        ArrayList<Item> userItems = ItemService.getAllUserItems(userEmail);
        // Set userItems session variable
        session.setAttribute("userItems", userItems);

        // Retrieve all categories
        ArrayList<Category> categories = CategoryService.getAllCategories();
        // Set categories session variable
        session.setAttribute("categories", categories);

        // Check if the user selected "Edit inventory"
        if (request.getParameter("editInventory") != null) {
            // Set editInventory to true to send the user to the edit inventory form
            request.setAttribute("editInventory", true);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get userEmail
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");

        // Get ArrayList of user items
        ArrayList<Item> userItems = (ArrayList<Item>) session.getAttribute("userItems");

        // Make sure that action is not null
        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "no action";

        switch (action) {
            case ("Add Item"):

                // Create a blank item for user to edit
                // Keep the data from other items
                for (int x = 0; x < userItems.size(); x++) {
                    // Item Id
                    int itemId = userItems.get(x).getItemId();

                    // Item values
                    String itemName = request.getParameter(itemId + "Name");
                    Double itemPrice = Double.parseDouble(request.getParameter(itemId + "Price"));
                    int categoryId = Integer.parseInt(request.getParameter(itemId + "Category"));
                    Category category = CategoryService.getCategory(categoryId);

                    // Update the exisiting inventory
                    Item currentItem = userItems.get(x);

                    // Set new or old values
                    currentItem.setItemName(itemName);
                    currentItem.setPrice(itemPrice);
                    currentItem.setCategory(category);

                    // Set item
                    userItems.set(x, currentItem);
                }

                // Initalize a new blank item
                // Set the id to a negative number to indicate that it is a new item
                Item blankItem = new Item((userItems.size() - 1) * -1, UserService.getUser(userEmail));
                userItems.add(blankItem);

                // Set editInventory to true to keep the user on the edit inventory form
                request.setAttribute("editInventory", true);
                break;

            case ("Save Changes"):
                // ArrayList to hold items that will be deleted
                ArrayList<Item> itemsToDelete = new ArrayList<>();

                //  Remove items selected for deletion and/or initalize new items
                for (int x = 0; x < userItems.size(); x++) {
                    // Item ID
                    int itemId = userItems.get(x).getItemId();

                    // Item values
                    String itemName = request.getParameter(itemId + "Name");
                    Double itemPrice = Double.parseDouble(request.getParameter(itemId + "Price"));
                    int categoryId = Integer.parseInt(request.getParameter(itemId + "Category"));
                    Category category = CategoryService.getCategory(categoryId);

                    if (itemId < 0) {
                        // Initalize new item
                        // All new items id will always be a negative number
                        Item newItem = userItems.get(x);

                        // Set ID to null so that a proper ID can be created when the item is inserted into the tabel
                        newItem.setItemId(null);
                        // Set new values
                        newItem.setItemName(itemName);
                        newItem.setPrice(itemPrice);
                        newItem.setCategory(category);

                        // Set new item
                        userItems.set(x, newItem);
                    } else {
                        // Update the exisiting inventory
                        Item currentItem = userItems.get(x);

                        // Set new values
                        currentItem.setItemName(itemName);
                        currentItem.setPrice(itemPrice);
                        currentItem.setCategory(category);

                        // Set item
                        userItems.set(x, currentItem);
                    }

                    // Delete item
                    String delete = request.getParameter(itemId + "Delete");
                    if (delete != null) {
                        itemsToDelete.add(userItems.get(x));
                        userItems.remove(x);
                        x--;
                    }
                }

                // Update user's inventory
                ItemService.updateUserItems(userItems);
                ItemService.deleteUserItems(itemsToDelete);
                // Retrieve new item list
                userItems = ItemService.getAllUserItems(userEmail);

                // Update userItems session variable
                session.setAttribute("userItems", userItems);
                break;

            default:
                System.out.println("default");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }

}
