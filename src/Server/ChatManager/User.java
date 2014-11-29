package Server.ChatManager;

/**
 * Created by coylter on 11/19/2014.
 */
public class User {
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
