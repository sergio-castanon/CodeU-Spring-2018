package codeu.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Servlet class responsible for the admin page. */
public class AdminServlet extends HttpServlet {

    private static final String ADMIN_MESSAGE = "admin_message";

    /**
     * This function fires when a user navigates to the admin page. It displays a message on the page, which differs
     * depending on if you are logged in, and whether or not you are an admin.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("user");
        System.out.println("user: " + user);

        /* Note: the page should always have "Welcome to the admin page!" because I later edited the index.jsp page to
        only show the admin link if you are currently logged in as an admin. I (Justin) will refactor this code for
        the MVP.
        */
        if (user != null) {
            if (isAdmin(user)) {
                request.getSession().setAttribute(ADMIN_MESSAGE, "Hello " + user + "! Welcome to the admin page!");
            } else {
                request.getSession().setAttribute(ADMIN_MESSAGE, "You are not an admin!");
            }
        } else {
            response.sendRedirect("/login");
        }

        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        return;
    }

    /**
     * Determines whether or not the given username is an admin.
     * @param user representing the user currently logged into the site (null if not logged in)
     * @return boolean
     */
    public static boolean isAdmin(String user) {
        String[] admins = {"Cynthia", "Justin", "Sergio"};
        for (String admin : admins) {
            if (user.equals(admin))
                return true;
        }
        return false;
    }

}
