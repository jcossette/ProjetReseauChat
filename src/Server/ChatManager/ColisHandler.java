package Server.ChatManager;

import Colis.Colis;

/**
 * Created by coylter on 11/19/2014.
 */
public class ColisHandler {
    private UserManager myUserManager;
    private RoomManager myRoomManager;


    public ColisHandler(){

    }

    public void handleColis(ColisClient toHandle){
        System.out.println("treating a colis!");
        switch(toHandle.getMyColis().getType()){
            case connection:
                handleConnection(toHandle);
                break;
            default:
                break;
        }
    }

    private void handleConnection(ColisClient toHandle){

    }

}
