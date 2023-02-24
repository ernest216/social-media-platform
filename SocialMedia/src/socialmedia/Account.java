package socialmedia;

import java.io.IOException;

public class Account implements Serializable {
    // private instance variables
    private String handle;
    private String description;
    private int accountId;
    private static int accountIdCounter = 0;
    // constructor 
    public Account(String handle, String description){
        this.handle = handle;
        this.description = description;
        this.accountId = accountIdCounter++;
    }
    // public getters and setter for private instance
    public String getHandle(){
        return handle;
    }
    public String getDescription(){
        return description;
    }
    public static int getAccountId(){
        return accountId;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }
    public void setHandle(){
        this.handle = handle;
    }
    public void setDescription(){
        this.description = description;
    }





}