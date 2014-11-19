package Server.ChatManager;

import Colis.Colis;

/**
 * Created by coylter on 11/19/2014.
 */
public class ColisHandler {
    private static ColisHandler instance;

    private ColisHandler(){

    }

    public static ColisHandler getInstance(){
        if(instance == null){
            instance = new ColisHandler();
            return instance;
        }else{
            return instance;
        }
    }

    public void handleColis(Colis toHandle, Session sender){

    }
}
