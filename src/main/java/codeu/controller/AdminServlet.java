package codeu.controller;

import codeu.helper.AdminHelper;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Servlet class responsible for the admin page. */
public class AdminServlet extends HttpServlet {

    private static final String ADMIN_MESSAGE = "adminMessage";

    /**
     * This function fires when a user navigates to the admin page. It displays a message on the page, which differs
     * depending on if you are logged in, and whether or not you are an admin.
     * The page contains information pertaining to administration if you are an admin.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("user");

        if (user != null) {
            if (AdminHelper.isAdmin(user)) {
                request.getSession().setAttribute(ADMIN_MESSAGE, "Hello " + user + "! Welcome to the admin page!");
            } else {
                request.getSession().setAttribute(ADMIN_MESSAGE, "You are not an admin!");
                request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
                return;
            }
        } else {
            response.sendRedirect("/login?error_message=notloggedin&post_login_redirect=/admin");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("deleteUsersButton") != null) {
            UserStore.getInstance().deleteAllUsers();
            response.sendRedirect("/logout");
            return;
        } else if (request.getParameter("deleteMessagesButton") != null) {
            MessageStore.getInstance().deleteAllMessages();
        } else if (request.getParameter("deleteConversationsButton") != null) {
            MessageStore.getInstance().deleteAllMessages();
            ConversationStore.getInstance().deleteAllConversations();
        }

        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        return;
    }
}


