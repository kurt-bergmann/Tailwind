package servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import services.CategoryService;

public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // Retrieve all categories
        ArrayList<Category> categories = CategoryService.getAllCategories();
        // Set categories session variable
        session.setAttribute("categories", categories);

        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // Retrieve categories
        ArrayList<Category> categories = (ArrayList) session.getAttribute("categories");

        String action = request.getParameter("action");

        switch (action) {
            case ("Edit Categories"):
                // Send user to edit categories form
                request.setAttribute("editCategories", true);
                break;

            case ("Add Category"):
                // Create a blank item for user to edit
                // Keep the data from other items
                for (int x = 0; x < categories.size(); x++) {
                    int categoryId = categories.get(x).getCategoryId();

                    // New or old category name
                    String categoryName = request.getParameter(categoryId + "CategoryName");

                    // Set new or old category name
                    Category category = categories.get(x);
                    category.setCategoryName(categoryName);
                    categories.set(x, category);
                }

                // Set the id to the size of the categories list plus 1
                Category blankCategory = new Category(categories.size() + 1);
                categories.add(blankCategory);

                // Set editCategory to true to keep the user on the edit category form
                request.setAttribute("editCategories", true);
                break;

            case ("Save Changes"):
                for (int x = 0; x < categories.size(); x++) {
                    int categoryId = categories.get(x).getCategoryId();

                    // New or old category name
                    String categoryName = request.getParameter(categoryId + "CategoryName");

                    // Set new or old category name
                    Category category = categories.get(x);
                    category.setCategoryName(categoryName);
                    categories.set(x, category);
                }

                boolean categoriesChanged = CategoryService.updateCategories(categories);

                if (categoriesChanged) {
                    // Inform user of success
                    request.setAttribute("categoriesChanged", true);
                }
                break;
        }

        session.setAttribute("categories", categories);
        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
    }
}
