package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Destinations
        String homePage = "/WEB-INF/home.jsp";
        String accountPage = "/WEB-INF/account.jsp";
        // Where to go
        String requestDispatcher;

        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        
        switch (action) {
            case ("account"):
                requestDispatcher = accountPage;
             break;
             
            default:
                requestDispatcher = homePage;
        }
        
           getServletContext().getRequestDispatcher(requestDispatcher).forward(request, response);
          
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       HttpSession session = request.getSession();

       
    }

}