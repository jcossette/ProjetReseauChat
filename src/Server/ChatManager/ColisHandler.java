package Server.ChatManager;

import Colis.Colis;
import Colis.TypeColisEnum;

import java.util.ArrayList;

/**
 * Created by coylter on 11/19/2014.
 */
public class ColisHandler {
    private UserManager myUserManager;
    private RoomManager myRoomManager;

    public ColisHandler(){
        myUserManager = new UserManager();
        myRoomManager = new RoomManager();
    }

    public void handleColis(ColisClient toHandle){
        switch(toHandle.getMyColis().getType()){
            case connection:
                handleConnection(toHandle);
                break;
            case getFullUpdate:
                handleFullUpdate(toHandle);
            default:
                break;
        }
    }

    private void handleConnection(ColisClient toHandle){
        String username = (String)toHandle.getMyColis().popParameter();
        if(myUserManager.hasUser(username)){
            Colis returnDeny = new Colis(TypeColisEnum.refusedConnection);
            returnDeny.addParameter("Connection refusé: Username en utilisation");
            toHandle.getMySession().send(returnDeny);
        }else{
            User newUser = new User(username);
            toHandle.getMySession().setUser(newUser);
            Colis returnAccept = new Colis(TypeColisEnum.acceptedConnection);
            returnAccept.addParameter("Connection accepté: Username = " + username);
            autoLobby(newUser);
            toHandle.getMySession().send(returnAccept);
        }
    }

    private void autoLobby(User userToJoin){
        myRoomManager.getRoom(0).addUser(userToJoin);
    }

    private void handleFullUpdate(ColisClient toHandle){
        ArrayList<Room> roomList = myRoomManager.getRoomList();
        ArrayList<User> userList = myUserManager.getUserList();
        Colis fullUpdateColis = new Colis(TypeColisEnum.fullUpdate);
        fullUpdateColis.addParameter(roomList);
        fullUpdateColis.addParameter(userList);
        toHandle.getMySession().send(fullUpdateColis);
    }

    public void removeUser(User toRemove){
        for(Room r : toRemove.getMyRooms()){
            r.removeUser(toRemove);
        }
        myUserManager.removeUser(toRemove);
    }

}
