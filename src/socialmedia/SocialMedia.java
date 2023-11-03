package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Comparator;

/**
 * SocialMedia is a minimally compiling, but non-functioning implementor
 * of the MiniSocialMediaPlatform interface.
 * 
 * @author Chit Hin Ernest Cheong
 * @version 1.0
 */

public class SocialMedia implements MiniSocialMediaPlatform {

	// attributes which hold the accounts in the social media
	private ArrayList<Account> accounts = new ArrayList<>();

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// check if the handle is illegal or duplicate
		if (handle.isEmpty() || handle.length() > 30 || handle.indexOf(' ') >= 0) {
			throw new InvalidHandleException();
		}
		for (Account account : accounts) {
			if (account.getHandle().equals(handle)) {
				throw new IllegalHandleException();
			}
		}
		// create a new account and add it to the list of accounts
		Account newAccount = new Account(handle);
		accounts.add(newAccount);
		return newAccount.getAccountId();
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		// use the size() method to get the size of the accounts list and loop over it using a standard for loop
		for (int i = 0; i < accounts.size(); i++) {
			Account account = accounts.get(i);
			if (account.getAccountId() == id) {
				// remove account from the list of accounts
				accounts.remove(i);
				return;
			}
		}
		throw new AccountIDNotRecognisedException();

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
	    //check if the new handle is valid
		if (newHandle.isEmpty() || newHandle.length() > 30 || newHandle.indexOf(' ') >= 0) {
			throw new InvalidHandleException();
		}
		// Check if the old handle is recognised
		boolean accountFound = false;
		for (Account account : accounts) {
			if (account.getHandle().equals(oldHandle)) {
				accountFound = true;
				break;
			}
			if (account.getHandle().equals(newHandle)) {
				throw new IllegalHandleException();
			}
		}
		if (!accountFound) {
			throw new HandleNotRecognisedException();
		}
		// search the account and change the handle
		for (Account account : accounts) {
			if (account.getHandle().equals(oldHandle)) {
				account.setHandle(newHandle);
				return;
			}
		}

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// Declare a null account variable to store the matched account
		Account account = null;
		// Iterate through the accounts to find the one that matches the handle
		for (Account a : accounts) {
			if (a.getHandle().equals(handle)) {
				// Store the matched account in the account variable
				account = a;
				break;
			}
		}
		// If the account variable is still null, that means no account matched the handle, so throw an exception
		if (account == null) {
			throw new HandleNotRecognisedException();
		}
		return account.getSummary();
		
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// Declare a null account variable to store the matched account
		Account account = null;
		for (Account a : accounts) {
			if (a.getHandle().equals(handle)) {
				account = a;
				break;
			}
		}
		// If the account variable is still null, that means no account matched the handle, so throw an exception
		if (account == null) {
			throw new HandleNotRecognisedException();
		}

        // Check if the message is empty or has more than 100 characters
        if (message == null || message.isEmpty() || message.length() > 100) {
            throw new InvalidPostException();
        }

        // Create a new post for the account
        Post post = new Post(message);
        account.addPost(post);

        // Return the sequential ID of the created post
        return post.getPostId();
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// Declare a null account variable to store the matched account
        Account account = null;
        for (Account a : accounts) {
           if (a.getHandle().equals(handle)) {
            account = a;
            break;
		   }
	    }
		// If the account variable is still null, that means no account matched the handle, so throw an exception
		if (account == null) {
			throw new HandleNotRecognisedException();
		}

		// Check if the post exists and is not already an endorsement
		Post post = null;
		for (Post p : account.getPosts()) {
			if (p.getPostId() == id) {
				post = p;
				break;
			}
		}
		if (post == null) {
			throw new PostIDNotRecognisedException();
		} else if (post.isEndorsement()) {
			throw new NotActionablePostException();
		}

        // Create the endorsement post
        String message = "EP@" + account.getHandle() + ": " + post.getMessage();
        Post endorsementPost = new Post(message);
		endorsementPost.setIsEndorsement(true);

        // Add the endorsement post to the endorsements array in the post class
		post.addEndorsement(endorsementPost);
        
		// Add the endorsement post to the account
        account.addPost(endorsementPost);

        // Return the ID of the endorsement post
        return endorsementPost.getPostId();
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// Declare a null account variable to store the matched account
		Account account = null;
        for (Account a : accounts) {
           if (a.getHandle().equals(handle)) {
            account = a;
            break;
		   }
	    }
		// If the account variable is still null, that means no account matched the handle, so throw an exception
		if (account == null) {
			throw new HandleNotRecognisedException();
		}
		// Check if the post exists, vaild and is not already an endorsement
		Post post = null;
		for (Post p : account.getPosts()) {
			if (p.getPostId() == id) {
				post = p;
				break;
			}
		}
        if (post == null) {
            throw new PostIDNotRecognisedException();
        } else if (post.isEndorsement()) {
            throw new NotActionablePostException();
        } else if (message == null || message.isEmpty() || message.length() > 100) {
			throw new InvalidPostException();
		}
		// Creates the comment post
		Post comment = new Post(message);
		comment.setIsCommentPost(true);
		comment.setOriginalPostId(id);

		// Add the comment post to the account
        account.addPost(comment);

		// Add the comment post to the endorsements array in the post class
		post.addComment(comment);

		// Return the ID of the post
		return comment.getPostId();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// Initialize a boolean flag to track if the post with the given ID was found
		boolean found = false;
		// Loop through all accounts and posts of each account
		for (Account account: accounts){
			for (Post post: account.getPosts()) {
			    if (id == post.getPostId()){
					found = true;
					// If the post is an endorsement, remove it from all accounts that endorsed it
					if (post.isEndorsement()){
                        for (Post endorsements : post.getEndorsements()) {
							endorsements.setIsEndorsement(false);
							// Remove the endorsement post from its account
							account.removePost(endorsements);
						}
					}
					// If the post is a comment, set its message to a generic message indicating the original content was removed
					if (post.isComment()){
						for (Post reply : account.getPosts()) {
                            if (reply.getOriginalPostId() == id) {
                                reply.setMessage("The original content was removed from the system and is no longer available.");
                            }
                        }
					}
					// Remove the post from the account it belongs to
				    account.removePost(post);
				    break;
				}
			}
		}
		// If no post with the given ID was found, throw an exception
		if (!found){
			throw new PostIDNotRecognisedException();
		}

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// Initialize a boolean flag to track if the post with the given ID was found
        boolean found = false;
        // Loop through all accounts
        for (Account account: accounts){
			// Loop through all posts of each account
			for (Post post: account.getPosts()) {
				// If the post ID matches the given ID, mark it as found
			    if (id == post.getPostId()){
                    found = true;
                    // Get the number of endorsements and comments for this post
                    int endorsementCount = post.getEndorsements().size();
                    int commentCount = post.getComments().size();
                    // Return a formatted string with post information
                    return String.format("ID: %d\nAccount: %s\nNo. endorsements: %d | No. comments: %d\n%s",
                    post.getPostId(), account.getHandle(), endorsementCount, commentCount, post.getMessage());
                }    
            }
        }
        // If no post with the given ID was found, throw an exception
        if (!found){
			throw new PostIDNotRecognisedException();
		}
		// This line will never be reached, since the method either returns a string or throws an exception
		return null;
    }

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
        // Declare a null post variable to store the matched post
        Post postFound = null;
        // Loop through all accounts
        for (Account account: accounts){
			// Loop through all posts of each account
			for (Post post: account.getPosts()) {
                if (post.getPostId() == id) {
                // Assign the matched post to the postFound variable
                    postFound = post;
                    break;
                }
            }
	    }
        // If no post with the given ID is found, throw a PostIDNotRecognisedException
        if (postFound == null) {
            throw new PostIDNotRecognisedException();
        } 
        // If the matched post is an endorsement or not a comment, throw a NotActionablePostException
        else if (postFound.isEndorsement() || !postFound.isComment()) {
            throw new NotActionablePostException();
        }
        // If the matched post is a comment, proceed to get its children details
        else {
            // Create a StringBuilder to store the post details and its children's details
            StringBuilder sb = new StringBuilder();
            // Append the details of the root level post to the StringBuilder
            sb.append(showIndividualPost(id)).append("\n");
            // Get the comments of the post and add them to an ArrayList of replies 
            ArrayList<Post> replies = new ArrayList<>();
            replies.addAll(postFound.getComments());
            // Sort the replies by their post IDs and append the details of each reply to the StringBuilder
            replies.sort(new Comparator<Post>() {
				public int compare(Post o1, Post o2) {
					return Integer.compare(o1.getPostId(), o2.getPostId());
				}
			});
            for (Post reply : replies) {
                sb.append("|  >").append(showIndividualPost(reply.getPostId())).append("\n");
            }
            return sb;
		}
	}

	@Override
	public int getMostEndorsedPost() {
		// Loop through the account and their posts to find the most endorsed post
		int mostEndorsements = 0;
		int mostEndorsedPostId = 0;
        for (Account account: accounts){
			for (Post post: account.getPosts()) {
				// Get the number of endorsements for the post
                int endorsementCount = post.getEndorsements().size();

				// Check if the current post has more endorsements than the current most endorsed post
                if (endorsementCount > mostEndorsements) {
                    mostEndorsements = endorsementCount;
                    mostEndorsedPostId = post.getPostId();
                }
            }
        }
		return mostEndorsedPostId;
	}

	@Override
	public int getMostEndorsedAccount() {
		int mostEndorsements = 0;
		int mostEndorsedAccountId = 0;
		// Loop throught the account and their post
        for (Account account : accounts) {
            int endorsementCount = 0;
            for (Post post : account.getPosts()) {
				// Get the number of endorsements for each post of the account
                endorsementCount += post.getEndorsements().size();
            }
            // Check if the current account has more endorsements than the current most endorsed account
            if (endorsementCount > mostEndorsements) {
                mostEndorsements = endorsementCount;
                mostEndorsedAccountId = account.getAccountId();
            }
        }
        
        return mostEndorsedAccountId;
	}

	@Override
	public void erasePlatform() {
		// Reset counters
		Post.setCurrentId(0);
		Account.setCurrentId(0);
		
		// Loop through all accounts and remove posts
		for (Account account : accounts) {
			account.getPosts().clear();
		}
		
		// Clear accounts list
		accounts.clear();

		assert(accounts.size() == 0);


	}

	@Override
	public void savePlatform(String filename) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			// Save the list of accounts
			out.writeObject(accounts);
	
			// Save the current ID counts
			out.writeInt(Account.getCurrentId());
			out.writeInt(Post.getCurrentId());
			out.close();
		}

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			// Load the list of accounts
			accounts = (ArrayList<Account>) in.readObject();
	
			// Load the current ID counts
			int accountIdCounter = in.readInt();
			int postIdCounter = in.readInt();
			Account.setCurrentId(accountIdCounter);
			Post.setCurrentId(postIdCounter);
		}

	}

}
