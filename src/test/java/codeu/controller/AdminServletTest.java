package codeu.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminServletTest {

    private AdminServlet adminServlet;
    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

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

    }

    @Test
    public void testDoGet_UserNotLoggedIn() throws IOException, ServletException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

        adminServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendRedirect("/login");
    }

    @Test
    public void testDoGet_UserNotAdmin() throws IOException, ServletException {
        Mockito.when(mockSession.getAttribute("user")).thenReturn("Foobar");

        adminServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("admin_message", "You are not an admin!");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoGet_UserIsAdmin() throws IOException, ServletException {
        String admin = "Justin";
        Mockito.when(mockSession.getAttribute("user")).thenReturn(admin);

        adminServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("admin_message", "Hello " + admin + "! Welcome to the admin page!");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

}
