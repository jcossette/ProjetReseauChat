package Server.ChatManager;

import Server.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by coylter on 11/19/2014.
 */
public class Session {
    User myUser;
    Socket mySocket;
    ServerController myController;
    ObjectInputStream myInputStream;
    ObjectOutputStream myOutputStream;

    public Session(Socket mySocket, ServerController myController){
        myUser = null;  //Anonymous Session at first
        this.mySocket = mySocket;
        this.myController = myController;
        try {
            myInputStream = new ObjectInputStream(this.mySocket.getInputStream());
            myOutputStream = new ObjectOutputStream(this.mySocket.getOutputStream());
        }catch(IOException e){
            myController.writeMessage("Erreur de creation des streams: " + e.getMessage());
        }
    }
}
