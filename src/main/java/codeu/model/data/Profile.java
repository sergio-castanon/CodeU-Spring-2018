package codeu.model.data;

import java.util.UUID;

/**
 * Class representing a profile which consists of an About Me section. Users can only edit their
 * own profile's About Me section. 
 *
 */
public class Profile {
	private final UUID id;
	private final String owner;
	private String text;
	
	/**
	 * Constructs a new Profile
	 * 
	 * @param id the ID of the profile
	 * @param owner the owner of the profile 
	 * @param text the About Me section text 
	 */
	public Profile(UUID id, String owner, String text) {
		this.id = id;
		this.owner = owner;
		this.text = text;
	}
	
	/**
	 * @return the ID of the profile
	 */
	public UUID getId() {
		return this.id;
	}
	
	/**
	 * @return the About Me section text 
	 */
	public String getProfileText() {
		return this.text;
	}
	
	/**
	 * Updates the About Me text for a user's profile
	 */
	public void setProfileText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the owner of the profile
	 */
	public String getProfileOwner() {
		return owner;
	}
	
}