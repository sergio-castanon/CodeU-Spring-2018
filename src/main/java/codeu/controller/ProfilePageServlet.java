package codeu.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

import java.io.IOException;

/** Servlet class responsible for a user's profile page. */
public class ProfilePageServlet extends HttpServlet {

    /**
     * This function fires when a user navigates to a profile page. It displays a user's About Me section
     * and depending on if the profile is the current user's, allows editing of the About Me section.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String user = (String) request.getSession().getAttribute("user");
    	String authorName = request.getRequestURI().substring("/profile/".length());
    	
    	User author = UserStore.getInstance().getUser(authorName);
    	
    	// user does not exist
    	if (author == null) {
    		response.sendRedirect("/");
    	}
    	
        request.setAttribute("author", authorName);
        
        if (user == null || (user != null && !authorName.equals(user))) {
        	request.setAttribute("userMatch", false);
        }
        else {
        	request.setAttribute("userMatch", true);
        }

        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
        return;
    }
    
    

}


