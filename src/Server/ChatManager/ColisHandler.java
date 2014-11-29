package Server.ChatManager;

import Colis.Colis;
import Colis.TypeColisEnum;

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
        String username = (String)toHandle.getMyColis().popParameter();
        if(myUserManager.hasUser(username)){
            Colis returnDeny = new Colis(TypeColisEnum.refusedConnection);
            returnDeny.addParameter("Connection refusé: Username en utilisation");
            toHandle.getMySession().send(returnDeny);
        }else{
            Colis returnAccept = new Colis(TypeColisEnum.acceptedConnection);
            returnAccept.addParameter("Connection accepté: Username = " + username);
            toHandle.getMySession().send(returnAccept);
        }
    }

}
