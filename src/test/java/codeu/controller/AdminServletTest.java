package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentStorageAgent;
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

public class AdminServletTest {

    private AdminServlet adminServlet;
    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;
    private PersistentStorageAgent mockPersistentStorageAgent;
    private ConversationStore fakeConversationStore;
    private MessageStore fakeMessageStore;
    private UserStore fakeUserStore;

    private String adminUser = "Justin";

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

        fakeConversationStore = ConversationStore.getTestInstance(mockPersistentStorageAgent);
        ArrayList<Conversation> testConversations = new ArrayList<>();
        for (int i = 0; i < 123; i++) {
            testConversations.add(null);
        }
        fakeConversationStore.setConversations(testConversations);

        fakeMessageStore = MessageStore.getTestInstance(mockPersistentStorageAgent);
        ArrayList<Message> testMessages = new ArrayList<>();
        for (int i = 0; i < 1234; i++) {
            testMessages.add(null);
        }
        fakeMessageStore.setMessages(testMessages);

        fakeUserStore = UserStore.getTestInstance(mockPersistentStorageAgent);
        ArrayList<User> testUsers = new ArrayList<>();
        for (int i = 0; i < 12345; i++) {
            testUsers.add(null);
        }
        fakeUserStore.setUsers(testUsers);

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

        Mockito.when(mockSession.getAttribute("user")).thenReturn(adminUser);

        adminServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("adminMessage", "Hello " + adminUser + "! Welcome to the admin page!");

        Assert.assertEquals(123, fakeConversationStore.getNumConversations());
        Assert.assertEquals(1234, fakeMessageStore.getNumMessages());
        Assert.assertEquals(12345, fakeUserStore.getNumUsers());

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
        Mockito.when(mockSession.getAttribute("user")).thenReturn(adminUser);

        Assert.assertEquals(1234, fakeMessageStore.getNumMessages());
        Assert.assertEquals(12345, fakeUserStore.getNumUsers());
        Mockito.when(mockRequest.getParameter("deleteUsersButton")).thenReturn("notNull");

        adminServlet.doPost(mockRequest, mockResponse);

        fakeMessageStore.deleteAllMessages();
        fakeUserStore.deleteAllUsers();
        Mockito.verify(mockResponse).sendRedirect("/logout");
        Assert.assertEquals(0, fakeMessageStore.getNumMessages());
        Assert.assertEquals(0, fakeUserStore.getNumUsers());
    }

    @Test
    public void testDoPost_DeleteMessages() throws ServletException, IOException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn(adminUser);

        Assert.assertEquals(1234, fakeMessageStore.getNumMessages());
        Mockito.when(mockRequest.getParameter("deleteMessagesButton")).thenReturn("notNull");

        adminServlet.doPost(mockRequest, mockResponse);

        fakeMessageStore.deleteAllMessages();
        Assert.assertEquals(0, fakeMessageStore.getNumMessages());

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost_DeleteConversations() throws ServletException, IOException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn(adminUser);

        Assert.assertEquals(1234, fakeMessageStore.getNumMessages());
        Assert.assertEquals(123, fakeConversationStore.getNumConversations());
        Mockito.when(mockRequest.getParameter("deleteConversationsButton")).thenReturn("notNull");

        adminServlet.doPost(mockRequest, mockResponse);

        fakeMessageStore.deleteAllMessages();
        fakeConversationStore.deleteAllConversations();
        Assert.assertEquals(0, fakeMessageStore.getNumMessages());
        Assert.assertEquals(0, fakeConversationStore.getNumConversations());

        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

}
