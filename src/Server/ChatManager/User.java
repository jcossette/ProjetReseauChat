package Server.ChatManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by coylter on 11/19/2014.
 */
public class User implements Serializable{
    private Integer ID;
    private String username;
    private ArrayList<Room> myRooms;

    public User(String newName){
        myRooms = new ArrayList();
        this.username = newName;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public ArrayList<Room> getMyRooms(){
        return myRooms;
    }

    public void addRoom(Room toAdd){
        myRooms.add(toAdd);
    }

    public void removeRoom(Room toRemove){
        myRooms.remove(toRemove);
    }
}
