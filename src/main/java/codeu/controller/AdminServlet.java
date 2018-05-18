package codeu.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends HttpServlet {

    private static final String ADMIN_MESSAGE = "admin_message";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("user");
        System.out.println("user: " + user);

        if (user != null) {
            if (isAdmin(user)) {
                request.getSession().setAttribute(ADMIN_MESSAGE, "Hello " + user + "! Welcome to the admin page!");
            } else {
                request.getSession().setAttribute(ADMIN_MESSAGE, "You are not an admin!");
            }
        } else {
            request.getSession().setAttribute(ADMIN_MESSAGE, "You are not logged in!");
        }

        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        return;
    }

    public static boolean isAdmin(String user) {
        String[] admins = {"Cynthia", "Justin", "Sergio"};
        for (String admin : admins) {
            if (user.equals(admin))
                return true;
        }
        return false;
    }

}
