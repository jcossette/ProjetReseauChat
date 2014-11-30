package Server.ChatManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by coylter on 11/19/2014.
 */
public class UserManager {
    private ArrayList<User> myUsers;
    private Map<User, SessionJob> userSessions;

    public UserManager(){
        myUsers = new ArrayList();
        userSessions = new HashMap();
    }

    public boolean hasUser(String username){
        for(User u : myUsers){
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<User> getUserList(){
        return myUsers;
    }

    public void addUser(User toAdd){
        myUsers.add(toAdd);
    }

    public void removeUser(User toRemove){
        myUsers.remove(toRemove);
    }

}
