package Server.ChatManager;

import java.util.ArrayList;

/**
 * Created by Julien Cossette on 11/29/2014.
 */
public class RoomManager{
    private ArrayList<Room> myRooms;

    public RoomManager(){
        initLobby();
    }

    private void initLobby(){
        Room lobby = new Room();
        lobby.setName("Lobby");
        myRooms.add(lobby);
    }

    public ArrayList<Room> getRoomList(){
        return myRooms;
    }
}
