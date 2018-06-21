package codeu.helper;

/**
 * Helper class that includes a method for the ProfilePageServlet 
 * class.
 *
 */
public class ProfileHelper {
	/**
	 * 
	 * @param user 
	 * 			-- the name of the current user viewing the profile
	 * 				(null if not logged in)
	 * @param profileName
	 * 			-- the name of the profile owner
	 * @return whether the two usernames match
	 */
	public static boolean isSameUser(String user, String profileName) {
		if (user != null && profileName.equals(user)) {
			return true;
		}
		
		return false;
	}
	
}
