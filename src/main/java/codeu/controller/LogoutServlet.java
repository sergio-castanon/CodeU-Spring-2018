package codeu.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Servlet class responsible for the log out feature. */
public class LogoutServlet extends HttpServlet {
    /**
     * This function fires when a user clicks the logout header, which is only visible if they are currently logged in.
     * It logs out the user and redirects them back to the page they were on from where they clicked logout.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("user", null);

        String redirect = request.getParameter("post_logout_redirect");

        if (redirect != null) {
            response.sendRedirect(redirect);
        } else {
            response.sendRedirect("/index.jsp");
        }

        return;
    }
}
