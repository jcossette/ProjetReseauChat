package Server.ChatManager;

import java.util.ArrayList;

/**
 * Created by Julien Cossette on 11/29/2014.
 */
public class RoomManager{
    private ArrayList<Room> myRooms;

    public RoomManager(){

    }

    public ArrayList<Room> getRoomList(){
        return myRooms;
    }
}
