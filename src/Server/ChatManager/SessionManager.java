package Server.ChatManager;

import java.util.ArrayList;

/**
 * Created by coylter on 11/19/2014.
 */
public class SessionManager {
    private static SessionManager instance;
    private ArrayList<Session> mySessions = new ArrayList();

    private SessionManager(){

    }

    public static SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }else{
            return instance;
        }
        return instance;
    }

    public void addSession(Session toAdd){
        mySessions.add(toAdd);
    }

    public void removeSession(Session toRemove){
        mySessions.remove(toRemove);
    }
}
