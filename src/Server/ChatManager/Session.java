package Server.ChatManager;

import Colis.Colis;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by coylter on 11/19/2014.
 */
public class Session {
    private User myUser;
    private ChatManagerJob myChatManager;
    private SelectionKey myKey;

    public Session(SelectionKey myKey, ChatManagerJob myChatManager){
        this.myKey = myKey;
        this.myChatManager = myChatManager;
        myUser = null;  //Anonymous Session at first
    }

    public void send(Colis toSend){
        myChatManager.send(toSend, (SocketChannel)myKey.channel());
    }
}
