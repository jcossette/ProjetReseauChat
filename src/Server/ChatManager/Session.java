package Server.ChatManager;

import Server.ServerController;
import Colis.Colis;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

/**
 * Created by coylter on 11/19/2014.
 */
public class Session {
    User myUser;
    SocketChannel mySocketChannel;
    ObjectInputStream myInputStream;
    ObjectOutputStream myOutputStream;

    public Session(){
        myUser = null;  //Anonymous Session at first
//        this.mySocketChannel = mySocketChannel;
//        try {
//            myInputStream = new ObjectInputStream(this.mySocketChannel.socket().getInputStream());
//            myOutputStream = new ObjectOutputStream(this.mySocketChannel.socket().getOutputStream());
//        }catch(IOException e){
//            myController.writeMessage("Erreur de creation des streams: " + e.getMessage());
//        }
    }

    public void send(Colis toSend){
//        try{
//            myOutputStream.writeObject(toSend);
//        }catch(IOException e){
//            myController.writeMessage("Erreur d'ecriture sur le stream: " + e.getMessage());
//        }
    }

    /*public void receiveColis(SocketChannel receptionChannel){
        try{
            myInputStream = new ObjectInputStream(receptionChannel.socket().getInputStream());

        }catch(IOException ie){
            myController.writeMessage("Erreur d'ecriture sur le stream: " + e.getMessage());
        }
    }*/
}
