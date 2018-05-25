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
    	String profileName = request.getRequestURI().substring("/profile/".length());
    	
    	User profileOwner = UserStore.getInstance().getUser(profileName);
    	
    	// user does not exist
    	if (profileOwner == null) {
    		response.sendRedirect("/");
    	}
    	
        request.setAttribute("author", profileName);
        
        if (user == null || (user != null && !profileName.equals(user))) {
        	request.setAttribute("userMatch", false);
        }
        else {
        	request.setAttribute("userMatch", true);
        }

        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
        return;
    }
    
    

}


