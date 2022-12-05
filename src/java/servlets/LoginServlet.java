package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.UserService;


public class LoginServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String logout = request.getParameter("logout");
        
        HttpSession session = request.getSession();
         session.invalidate();
        
        if (logout != null) {
            // Logout user
            request.setAttribute("logout", "true");
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       HttpSession session = request.getSession();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        boolean invalid;

        if (email.equals("") || password.equals("")) {
           invalid = true;
           request.setAttribute("invalid", "Invalid login details");
           
        } else {
            // If the login details are correct, retrieve the corresponding user email
            String user =  UserService.login(email, password);
            
            invalid = (user == null);
            if (invalid) {
                request.setAttribute("invalid", "Invalid login details");
            } else {
                session.setAttribute("email", user);
            }
        }
       
        if (invalid == true) {
            // Send to login page if login attempt failed
            request.setAttribute("email", email);
            request.setAttribute("password", password); 
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            
         } else {
            response.sendRedirect("home");
        }
    }        
}