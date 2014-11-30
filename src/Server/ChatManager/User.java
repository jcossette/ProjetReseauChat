package Server.ChatManager;

import java.io.Serializable;

/**
 * Created by coylter on 11/19/2014.
 */
public class User implements Serializable{
    private String username;

    public User(String newName){
        this.username = newName;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
