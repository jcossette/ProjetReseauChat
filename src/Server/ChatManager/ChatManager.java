package Server.ChatManager;

import Colis.Colis;

/**
 * Created by Julien Cossette on 11/22/2014.
 */
public class ChatManager{
    private static ChatManager instance;
    private ColisHandler myColisHandler;
    private UserManager myUserManager;

    private ChatManager(){

    }

    public static ChatManager getInstance(){
        if(instance == null){
            instance = new ChatManager();
            return instance;
        }else{
            return instance;
        }
    }

    public void receiveColis(SessionJob sender, Colis received){

    }
}
