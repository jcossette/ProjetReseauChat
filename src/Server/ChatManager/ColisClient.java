package Server.ChatManager;

import Colis.Colis;

/**
 * Created by Julien Cossette on 11/29/2014.
 */
public class ColisClient{
    private Colis myColis;
    private SessionJob mySession;

    public ColisClient(Colis colisToAdd, SessionJob sessionToAdd){
        this.setMyColis(colisToAdd);
        this.setMySession(sessionToAdd);
    }

    public Colis getMyColis(){
        return myColis;
    }

    public void setMyColis(Colis myColis){
        this.myColis = myColis;
    }

    public SessionJob getMySession(){
        return mySession;
    }

    public void setMySession(SessionJob mySession){
        this.mySession = mySession;
    }
}
