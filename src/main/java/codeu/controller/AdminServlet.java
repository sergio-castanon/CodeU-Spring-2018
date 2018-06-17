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

    /** Store class that gives access to Conversations. */
    private ConversationStore conversationStore;

    /** Store class that gives access to Messages. */
    private MessageStore messageStore;

    /** Store class that gives access to Users. */
    private UserStore userStore;

    /** Set up state for admin page (displaying stats or allowing deletions) */
    @Override
    public void init() throws ServletException {
        super.init();
        setConversationStore(ConversationStore.getInstance());
        setMessageStore(MessageStore.getInstance());
        setUserStore(UserStore.getInstance());
    }

    /**
     * Sets the ConversationStore used by this servlet. This function provides a common setup method
     * for use by the test framework or the servlet's init() function.
     */
    void setConversationStore(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }

    /**
     * Sets the MessageStore used by this servlet. This function provides a common setup method for
     * use by the test framework or the servlet's init() function.
     */
    void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
     * This function fires when a user navigates to the admin page. It displays a message on the page, which differs
     * depending on if you are logged in, and whether or not you are an admin. It also has administrative buttons to
     * delete group of entities.
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
        String user = (String) request.getSession().getAttribute("user");

        if (AdminHelper.isAdmin(user)) {
            if (request.getParameter("deleteUsersButton") != null) {
                messageStore.deleteAllMessages();
                userStore.deleteAllUsers();
                response.sendRedirect("/logout");
                return;
            } else if (request.getParameter("deleteMessagesButton") != null) {
                messageStore.deleteAllMessages();
            } else if (request.getParameter("deleteConversationsButton") != null) {
                messageStore.deleteAllMessages();
                conversationStore.deleteAllConversations();
            }
        }

        request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
        return;
    }
}


