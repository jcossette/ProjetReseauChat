package Server.ChatManager;

import Colis.TypeColisEnum;
import Colis.Colis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Julien Cossette on 11/29/2014.
 */
public class RoomManager{
    private Map<Integer, Room> myRooms;
    private UserManager myUserManager;

    public RoomManager(){
        myRooms = new HashMap();
        initLobby();
    }

    public void setPartnerUserManager(UserManager toPartner){
        this.myUserManager = toPartner;
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

    public void appendText(Integer roomID, String message){
        Room toAppendTo = myRooms.get(roomID);
        toAppendTo.addMessage(message);
        ArrayList<User> concernedUsers = toAppendTo.getMyUsers();
        for(User u : concernedUsers){
            sendUpdateColis(myUserManager.getUserSession(u), toAppendTo, message);
        }
    }

    private void sendUpdateColis(SessionJob destination, Room concernedRoom, String text){
        Colis updateColis = new Colis(TypeColisEnum.updateText);
        updateColis.addParameter(concernedRoom.getName());
        updateColis.addParameter(text);
        destination.send(updateColis);
    }
}
