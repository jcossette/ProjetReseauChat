package Server.ChatManager;

import Colis.Colis;

import java.util.ArrayList;

/**
 * Created by Julien Cossette on 11/22/2014.
 */
public class ChatManager{
    private static ChatManager instance;
    private ColisHandler myColisHandler;
    private UserManager myUserManager;
    private ArrayList<SessionJob> mySessions;

    private ChatManager(){
        mySessions = new ArrayList();
    }

    public static ChatManager getInstance(){
        if(instance == null){
            instance = new ChatManager();
            return instance;
        }else{
            return instance;
        }
    }

    public void reportColis(SessionJob sender, Colis received){

    }

    public void addSession(SessionJob newSession){
        mySessions.add(newSession);
    }
}
