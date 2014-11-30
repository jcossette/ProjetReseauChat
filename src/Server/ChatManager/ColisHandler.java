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
                break;
            case updateText:
                handleUpdateText(toHandle);
                break;
            case getRoomList:
                handleGetRoomList(toHandle);
                break;
            default:
                break;
        }
    }

    private void handleGetRoomList(ColisClient toHandle){
        Colis toSend = new Colis(TypeColisEnum.roomList);
        toSend.addParameter(myRoomManager.getRoomList());
        toHandle.getMySession().send(toSend);
    }

    private void handleUpdateText(ColisClient toHandle){
        User sender = toHandle.getMySession().getUser();
        Integer roomID = (Integer)toHandle.getMyColis().popParameter();
        String message = (String)toHandle.getMyColis().popParameter();
        myRoomManager.appendText(roomID, sender.getUsername() + ":= " + message);
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
            addUser(newUser);
            toHandle.getMySession().send(returnAccept);
        }
    }

    private void handleFullUpdate(ColisClient toHandle){
        ArrayList<Room> roomList = myRoomManager.getRoomList();
        ArrayList<User> userList = myUserManager.getUserList();
        Colis fullUpdateColis = new Colis(TypeColisEnum.fullUpdate);
        fullUpdateColis.addParameter(roomList);
        fullUpdateColis.addParameter(userList);
        toHandle.getMySession().send(fullUpdateColis);
    }

    private void addUser(User toAdd){
        toAdd.addRoom(myRoomManager.getRoom(0));
        myRoomManager.getRoom(0).addUser(toAdd);
        myUserManager.addUser(toAdd);
    }

    public void removeUser(User toRemove){
        for(Room r : toRemove.getMyRooms()){
            r.removeUser(toRemove);
        }
        myUserManager.removeUser(toRemove);
    }

}
