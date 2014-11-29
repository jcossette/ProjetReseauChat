package Server.ChatManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Julien Cossette on 11/29/2014.
 */
public class RoomManager{
    private Map<Integer, Room> myRooms;

    public RoomManager(){
        initLobby();
    }

    private void initLobby(){
        Room lobby = new Room();
        lobby.setName("Lobby");
        myRooms.put(lobby.getID(), lobby);
    }

    public ArrayList<Room> getRoomList(){
        ArrayList<Room> myRoomList = new ArrayList(myRooms.values());
        return myRoomList;
    }

    public Room getRoom(Integer ID){
        return myRooms.get(ID);
    }
}
