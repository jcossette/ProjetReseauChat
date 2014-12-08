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
    private RoomManager myRoomManager;

    public UserManager(){
        myUsers = new ArrayList();
        userSessions = new HashMap();
    }

    public void setPartnerRoomManager(RoomManager toPartner){
        this.myRoomManager = toPartner;
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

    public void addUser(User toAdd, SessionJob mySession){
        myUsers.add(toAdd);
        userSessions.put(toAdd, mySession);
    }

    public void removeUser(User toRemove){
        for(Room r : toRemove.getMyRooms()){
            r.removeUser(toRemove);
        }
        myUsers.remove(toRemove);
    }

    public SessionJob getUserSession(User toGet){
        return userSessions.get(toGet);
    }

}
