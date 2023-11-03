import socialmedia.*;
import socialmedia.AccountIDNotRecognisedException;
import socialmedia.BadSocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.SocialMediaPlatform;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		MiniSocialMediaPlatform platform = new SocialMedia();

		assert (platform.getMostEndorsedPost() == 0) : "Initial SocialMediaPlatform most endorsed post should be null.";
        assert (platform.getMostEndorsedAccount() == 0) : "Initial SocialMediaPlatform most endorsed account should be null.";

		// define the handle for the user
        String my_handle = "my_handle";
		
		try {
			Integer id = platform.createAccount("my_handle");
            // create a post
            Integer post1 = platform.createPost(my_handle, "Hello World!");
            // create a comment
            Integer comment1 = platform.commentPost(my_handle, post1, "This is a comment!");
            // create an endorsement
            Integer endorsement1 = platform.endorsePost(my_handle, post1);
            // get most endorsed post
            assert (platform.getMostEndorsedPost() == post1) : "Most endorsed post does not match.";
            // get most endorsed account
            assert (platform.getMostEndorsedAccount() == id) : "Most endorsed account does not match.";
            // get most popular account
		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		} 

	}

}

