package codeu.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirect = request.getParameter("post_logout_redirect");
        request.getSession().setAttribute("user", null);

        if (redirect != null) {
            response.sendRedirect(redirect);
        } else {
            response.sendRedirect("/index.jsp");
        }
    }
}
