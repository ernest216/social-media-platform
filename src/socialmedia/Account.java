package socialmedia;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * This class represents an account on a social media platform, contains the posts in the account
 */
public class Account implements Serializable {

    // private instance variables
    private String handle;
    private String description;
    private int accountId;
    private static int accountIdCounter = 0;
    // Arraylist contains all posts
    private ArrayList<Post> posts = new ArrayList<>();

    /**
     * Constructor of a new Account object with the given handle and description.
     *
     * @param handle      the handle for the account
     * @param description the description for the account
     */ 
    public Account(String handle){
        this.handle = handle;
        this.accountId = accountIdCounter++;
    }

    // public getters and setter for private instance
    public String getHandle(){
        return handle;
    }
    public String getDescription(){
        return description;
    }
    public int getAccountId(){
        return accountId;
    }

    
    public void setHandle(String handle){
        this.handle = handle;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    /**
     * Returns a list of all the posts made by the account.
     *
     * @return a list of all the posts made by the account
     */
    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**
     * Adds a new post to the account's list of posts.
     *
     * @param post the post to add
     */
    public void addPost(Post post) {
        posts.add(post);
    }

    /**
     * Removes a post from the account's list of posts.
     *
     * @param post the post to remove
     */
    public void removePost(Post post) {
        posts.remove(post);
    }

    /**
     * Returns a summary of the account, including its ID, handle, description, number of posts, and number of endorsements.
     * @return a summary of the account
     */
    public String getSummary(){
        int postCount = 0;
        int endorseCount = 0;
        for (Post post: posts){
            postCount++;
            endorseCount += post.getEndorsements().size();
        }
        String summary = String.format("ID: %s\nHandle: %s\nDescription: %s\nPost count: %d\nEndorse count: %d",
        accountId, handle, description, postCount, endorseCount);

        return summary;
    }
 
    /**
     * Returns the current account ID counter.
     * @return the current account ID counter
     */
    public static int getCurrentId() {
        return accountIdCounter;
    }
    
    /**
     * Sets the current account ID counter.
     * @param currentId the new account ID counter
     */
    public static void setCurrentId(int currentId) {
        accountIdCounter = currentId;
    }

}