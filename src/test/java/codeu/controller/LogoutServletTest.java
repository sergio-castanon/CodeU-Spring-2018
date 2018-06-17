package codeu.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServletTest {

    private LogoutServlet logoutServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private HttpSession mockSession;

    @Before
    public void setup() {
        logoutServlet = new LogoutServlet();

        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockSession = Mockito.mock(HttpSession.class);

        Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    }

    @Test
    public void testDoGet() throws IOException {
        // By default, redirects to index page if there isn't a post logout redirect page set
        Mockito.when(mockRequest.getParameter("post_logout_redirect")).thenReturn(null);

        logoutServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("user", null);
        Mockito.verify(mockResponse).sendRedirect("/index.jsp");
    }

    @Test
    public void testDoGetOtherPageRedirect() throws IOException {
        Mockito.when(mockRequest.getParameter("post_logout_redirect")).thenReturn("/conversations");

        logoutServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("user", null);
        Mockito.verify(mockResponse).sendRedirect("/conversations");
    }

}
