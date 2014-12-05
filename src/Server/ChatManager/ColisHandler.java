package Server.ChatManager;

import Colis.Colis;
import Colis.TypeColisEnum;

import java.util.ArrayList;

/**
 * This class handles colis sent by users. It operates by receiving a Colis/Session data object representing the colis
 * that the server received and the client sending the colis. This class routes and treats the colis according to its type.
 * Created by Julien Cossette on 11/19/2014.
 */
public class ColisHandler {
    private UserManager myUserManager;
    private RoomManager myRoomManager;

    public ColisHandler(){
        myUserManager = new UserManager();
        myRoomManager = new RoomManager();
        myUserManager.setPartnerRoomManager(myRoomManager);
        myRoomManager.setPartnerUserManager(myUserManager);
    }

    public void handleColis(ColisClient toHandle){
        switch(toHandle.getMyColis().getType()){
            case connection:
                handleConnection(toHandle);
                break;
            case getFullUpdate:
                handleFullUpdate(toHandle);
                break;
            case communication:
                handleUpdateText(toHandle);
                break;
            case getRoomList:
                handleGetRoomList(toHandle);
                break;
            case createRoom:
                handleCreateRoom(toHandle);
                break;
            case joinRoom:
                handleJoinRoom(toHandle);
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
            addNewUser(newUser, toHandle.getMySession());
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

    /**
     * Handles the creation of a new room as requested by a user.
     * @param toHandle The Colis and its relation session
     */
    private void handleCreateRoom(ColisClient toHandle){
        SessionJob requestingSession = toHandle.getMySession();
        Room newRoom = myRoomManager.createRoom((String)toHandle.getMyColis().popParameter(), requestingSession.getUser());
        //Automatically join the requesting user to the requested room
            Colis colisToSend = new Colis(TypeColisEnum.joinRoom);
            requestingSession.getUser().addRoom(newRoom);
            colisToSend.addParameter(newRoom);
            requestingSession.send(colisToSend);
    }

    private void handleJoinRoom(ColisClient toHandle){
        SessionJob session = toHandle.getMySession();
        //Retrieve the roomID from the colis that the user wishes to join
            int roomID = (int)toHandle.getMyColis().popParameter();
            Room roomToJoin = myRoomManager.getRoom(roomID);
        //Envoi un update user sur la room aux autres users deja la
            myRoomManager.updateUser(roomID, session.getUser());
            roomToJoin.addUser(session.getUser());
        //Join the requesting user to the room
            Colis colisToSend = new Colis(TypeColisEnum.joinRoom);
            session.getUser().addRoom(roomToJoin);
            colisToSend.addParameter(roomToJoin);
            session.send(colisToSend);
    }

    private void addNewUser(User toAdd, SessionJob mySession){
        toAdd.addRoom(myRoomManager.getRoom(0));
        myRoomManager.getRoom(0).addUser(toAdd);
        myUserManager.addUser(toAdd, mySession);
    }

    public void removeUser(User toRemove){
        for(Room r : toRemove.getMyRooms()){
            r.removeUser(toRemove);
        }
        myUserManager.removeUser(toRemove);
    }
}
