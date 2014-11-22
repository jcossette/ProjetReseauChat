package Server.ChatManager;

import Colis.Colis;
import Server.ChatServer;
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
    private ChatServer myChatServer;
    private ChatManager myManager;
    private SelectionKey myKey;
    private Socket mySocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public SessionJob(Socket myWorkingSocket, ChatServer myServer, ChatManager myManager){
        this.myManager = ChatManager.getInstance();
        this.mySocket = myWorkingSocket;
        this.myChatServer = myServer;
        this.myUser = null;  //Anonymous Session at first
    }

    private void initStreams(){
        try{
            inputStream = new ObjectInputStream(mySocket.getInputStream());
            outputStream = new ObjectOutputStream(mySocket.getOutputStream());
        }catch(IOException ie){
            myChatServer.writeMessage("Session failed to open stream:" + myUser.getUsername());
        }
    }

    public void send(Colis toSend){

    }

    public void execute(){
        monitorInputSocket();
    }

    private void monitorInputSocket(){
        while(run == true){
            try{
                inputStream.readObject();
            }catch(ClassNotFoundException cnfe){
                myChatServer.writeMessage("Error reading from stream, Object not found:" + cnfe.getMessage());
            }catch(IOException ie){
                myChatServer.writeMessage("Error reading from stream:" + ie.getMessage());
            }
        }
    }

    private void reportColis(Colis toReport){

    }
}
