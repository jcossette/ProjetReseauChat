package Server.ChatManager;

import Colis.Colis;
import Server.ChatServer;
import Server.Job;

import java.net.Socket;
import java.nio.channels.SelectionKey;


/**
 * Created by coylter on 11/19/2014.
 */
public class SessionJob extends Job{
    private User myUser;
    private ChatServer myChatManager;
    private SelectionKey myKey;

    public SessionJob(Socket myWorkingSocket){
        myUser = null;  //Anonymous Session at first
    }

    public void send(Colis toSend){

    }

    public void execute(){

    }

}
