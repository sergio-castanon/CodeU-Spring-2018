package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import com.google.appengine.repackaged.org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminServletTest {

    private AdminServlet adminServlet;
    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;
    private PersistentStorageAgent mockPersistentStorageAgent;
    private ConversationStore mockConversationStore;
    private MessageStore mockMessageStore;
    private UserStore mockUserStore;

    @Before
    public void setup() {
        adminServlet = new AdminServlet();

        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
                .thenReturn(mockRequestDispatcher);

        mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);

        mockConversationStore = ConversationStore.getTestInstance(mockPersistentStorageAgent);
        ArrayList<Conversation> testConversations = new ArrayList<>();
        for (int i = 0; i < 123; i++) {
            testConversations.add(null);
        }
        mockConversationStore.setConversations(testConversations);

        mockMessageStore = MessageStore.getTestInstance(mockPersistentStorageAgent);
        ArrayList<Message> testMessages = new ArrayList<>();
        for (int i = 0; i < 1234; i++) {
            testMessages.add(null);
        }
        mockMessageStore.setMessages(testMessages);

        mockUserStore = UserStore.getTestInstance(mockPersistentStorageAgent);
        ArrayList<User> testUsers = new ArrayList<>();
        for (int i = 0; i < 12345; i++) {
            testUsers.add(null);
        }
        mockUserStore.setUsers(testUsers);

    }

    @Test
    public void testDoGet_UserNotLoggedIn() throws IOException, ServletException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

        adminServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendRedirect("/login?error_message=notloggedin&post_login_redirect=/admin");
    }

    @Test
    public void testDoGet_UserNotAdmin() throws IOException, ServletException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn("Foobar");

        adminServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("adminMessage", "You are not an admin!");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoGet_UserIsAdmin() throws IOException, ServletException {
        String admin = "Justin";
        Mockito.when(mockSession.getAttribute("user")).thenReturn(admin);

        adminServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("adminMessage", "Hello " + admin + "! Welcome to the admin page!");

        Assert.assertEquals(123, mockConversationStore.getNumConversations());
        Assert.assertEquals(1234, mockMessageStore.getNumMessages());
        Assert.assertEquals(12345, mockUserStore.getNumUsers());

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        adminServlet.doPost(mockRequest, mockResponse);
        Assert.assertNull(mockRequest.getParameter("deleteUsersButton"));
        Assert.assertNull(mockRequest.getParameter("deleteMessagesButton"));
        Assert.assertNull(mockRequest.getParameter("deleteConversationsButton"));

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost_DeleteUsers() throws ServletException, IOException {
        Assert.assertEquals(12345, mockUserStore.getNumUsers());
        Mockito.when(mockRequest.getParameter("deleteUsersButton")).thenReturn("notNull");

        adminServlet.doPost(mockRequest, mockResponse);

        mockUserStore.deleteAllUsers();
        Mockito.verify(mockResponse).sendRedirect("/logout");
        Assert.assertEquals(0, mockUserStore.getNumUsers());
    }

    @Test
    public void testDoPost_DeleteMessages() throws ServletException, IOException {
        Assert.assertEquals(1234, mockMessageStore.getNumMessages());
        Mockito.when(mockRequest.getParameter("deleteMessagesButton")).thenReturn("notNull");

        adminServlet.doPost(mockRequest, mockResponse);

        mockMessageStore.deleteAllMessages();
        Assert.assertEquals(0, mockMessageStore.getNumMessages());

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost_DeleteConversations() throws ServletException, IOException {
        Assert.assertEquals(1234, mockMessageStore.getNumMessages());
        Assert.assertEquals(123, mockConversationStore.getNumConversations());
        Mockito.when(mockRequest.getParameter("deleteConversationsButton")).thenReturn("notNull");

        adminServlet.doPost(mockRequest, mockResponse);

        mockMessageStore.deleteAllMessages();
        mockConversationStore.deleteAllConversations();
        Assert.assertEquals(0, mockMessageStore.getNumMessages());
        Assert.assertEquals(0, mockConversationStore.getNumConversations());

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

}
