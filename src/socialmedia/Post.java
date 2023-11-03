package socialmedia;

import java.util.ArrayList;
import java.io.Serializable;

/** 
 * Post class represents each post in the system, contains endorsements and comments 
 * in each post and methods to deal with them 
 */
public class Post implements Serializable {

    //private attributes
    private int postId;
    private String message;
    private boolean endorsement;
    private boolean commentPost;
    private static int postIdCounter = 0;
    private int originalPostId;
    private ArrayList<Post> endorsements = new ArrayList<Post>();
    private ArrayList<Post> comments = new ArrayList<Post>();

    /** Constructor for race class
	 *
	 * @param message content of new post
	 */
    public Post(String message) {
        this.postId = postIdCounter++;
        this.message = message;
        this.endorsement = false;
        this.commentPost = false;
    }

    //getter methods
    public int getPostId() {
        return postId;
    }
    public String getMessage() {
        return message;
    }
    
    public boolean isEndorsement(){
        return endorsement;
    }

    public boolean isComment(){
        return commentPost;
    }

    public ArrayList<Post> getEndorsements() {
        return endorsements;
    }

    public ArrayList<Post> getComments() {
        return comments;
    }

    //setter methods
    public void setPostId(int postId) {
        this.postId = postId;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setIsEndorsement(boolean endorsement){
        this.endorsement = endorsement;
    }

    public void setIsCommentPost(boolean commentPost){
        this.commentPost = commentPost;
    }

    /** Adds a new endorsement to the post
	 *
	 * @param endorsement Endorsement to be added to the list of endorsements
	 */
    public void addEndorsement(Post endorsement) {
        this.endorsements.add(endorsement);
    }

    /** Remove a endorsement from the post
	 *
	 * @param endorsement Endorsement to be removed from the list of endorsements
	 */
    public void removeEndorsement(Post endorsement) {
        this.endorsements.remove(endorsement);
    }

    /** Adds a new comment to the post
	 *
	 * @param comment comment to be added to the list of comments
	 */
    public void addComment(Post comment){
        this.comments.add(comment);
    }

    /** Removes a comment from the post
	 *
	 * @param comment comment to be removed from the list of comments
	 */
    public void removeComment(Post comment){
        this.comments.remove(comment);
    }

    /** get the post id for the post being commented on 
     * 
     * @return the id of the post being commented on
     */
    public int getOriginalPostId() {
        return originalPostId;
    }
    
    /** set the post id for the post being commented on 
     * 
     * 
     */
    public void setOriginalPostId(int originalPostId) {
        this.originalPostId = originalPostId;
    }

    /**
     * Returns the current post ID counter.
     * @return the current post ID counter
     */
    public static int getCurrentId() {
        return postIdCounter;
    }
    
    /**
     * Sets the current post ID counter.
     * @param currentId the new post ID counter
     */
    public static void setCurrentId(int currentId) {
        postIdCounter = currentId;
    }



    

}

