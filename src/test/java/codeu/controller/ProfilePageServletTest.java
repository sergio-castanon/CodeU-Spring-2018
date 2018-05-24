package codeu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * Testing class for the ProfilePageServlet
 *
 */
public class ProfilePageServletTest {
	
	private ProfilePageServlet profileServlet;
	private HttpServletRequest mockRequest;
	private HttpSession mockSession;
	private HttpServletResponse mockResponse;
	private RequestDispatcher mockRequestDispatcher;
	
	@Before
	public void setup() {
		profileServlet = new ProfilePageServlet();
		
		mockRequest = Mockito.mock(HttpServletRequest.class);
		mockSession = Mockito.mock(HttpSession.class);
		Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
		
		mockResponse = Mockito.mock(HttpServletResponse.class);
		mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
		Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
			.thenReturn(mockRequestDispatcher);
		
	}
	
	@Test
	public void testDoGet_UserCheckingOtherUsersProfile() throws ServletException, IOException {
		Mockito.when(mockSession.getAttribute("user")).thenReturn("notSameUser");
		Mockito.when(mockRequest.getRequestURI()).thenReturn("/profile/user");
		
		profileServlet.doGet(mockRequest, mockResponse);
		
		Mockito.verify(mockRequest).setAttribute("userMatch", false);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	}
	
	@Test
	public void testDoGet_UserCheckingTheirOwnProfile() throws ServletException, IOException {
		Mockito.when(mockSession.getAttribute("user")).thenReturn("sameUser");
		Mockito.when(mockRequest.getRequestURI()).thenReturn("/profile/sameUser");
		
		profileServlet.doGet(mockRequest, mockResponse);
		
		Mockito.verify(mockRequest).setAttribute("userMatch", true);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	}
	
	@Test
	public void testDoGet_UserNotLoggedInCheckingProfile() throws ServletException, IOException {
		Mockito.when(mockSession.getAttribute("user")).thenReturn(null);
		Mockito.when(mockRequest.getRequestURI()).thenReturn("/profile/someUser");
		
		profileServlet.doGet(mockRequest, mockResponse);
		
		Mockito.verify(mockRequest).setAttribute("userMatch", false);
		Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
	}
	
}