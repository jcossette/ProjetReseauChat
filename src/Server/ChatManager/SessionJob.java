package Server.ChatManager;

import Colis.Colis;
import Server.ChatServerJob;
import Server.Job;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SelectionKey;


/**
 * Created by Julien Cossette on 11/19/2014.
 */
public class SessionJob extends Job{
    private User myUser;
    private ChatServerJob myChatServerJob;
    private ChatManagerJob myManager;
    private SelectionKey myKey;
    private Socket mySocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public SessionJob(Socket myWorkingSocket, ChatServerJob myServer, ChatManagerJob myManager){
        this.myManager = ChatManagerJob.getInstance();
        this.mySocket = myWorkingSocket;
        this.myChatServerJob = myServer;
        this.myUser = null;  //Anonymous Session at first
        initStreams();
    }

    private void initStreams(){
        try{
            inputStream = new ObjectInputStream(mySocket.getInputStream());
            outputStream = new ObjectOutputStream(mySocket.getOutputStream());
        }catch(IOException ie){
            myChatServerJob.writeMessage("Session failed to open stream:" + myUser.getUsername());
        }
    }

    public synchronized void send(Colis toSend){
        try{
            outputStream.writeObject(toSend);
        }catch(IOException ie){
            myChatServerJob.writeMessage("Error writing to stream:" + myUser.getUsername());
        }
    }

    public void execute(){
        monitorInputSocket();
    }

    private void monitorInputSocket(){
        Colis received;
        while(run == true){
            try{
                received = (Colis)inputStream.readObject();
                myChatServerJob.writeMessage("ÒKAY");
                ColisClient newColisClient = new ColisClient(received, this);
                myManager.reportColis(newColisClient);
            }catch(ClassNotFoundException cnfe){
                myChatServerJob.writeMessage("Error reading from stream, Object not found: " + cnfe.getMessage());
            }catch(IOException ie){
                myChatServerJob.writeMessage("Error reading from stream: " + this.toString());
            }
        }
    }

    public User getUser(){
        return myUser;
    }

}
