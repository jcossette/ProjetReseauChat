package Server.ChatManager;

import java.util.ArrayList;

/**
 * Created by coylter on 11/19/2014.
 */
public class UserManager {
    private ArrayList<User> myUsers;

    public UserManager(){
        myUsers = new ArrayList();
    }

    public boolean hasUser(String username){
        for(User u : myUsers){
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

}
