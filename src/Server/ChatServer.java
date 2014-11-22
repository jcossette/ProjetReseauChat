package Server;

import Server.ChatManager.ChatManager;
import Server.ChatManager.SessionJob;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This job runs a facade managing the different components of the chat server.
 * Created by Julien Cossette on 11/10/2014.
 */
public class ChatServer extends Job{
    private ServerSocket myServerSocket;
    private ChatManager myChatManager;
    private ArrayList<SessionJob> mySessions;
    private WorkerPool myWorkerPool;
    private ServerController myController;
    private int myPort;

    public ChatServer(int port){
        this.myPort = port;
        this.mySessions = new ArrayList();
        this.myWorkerPool = new WorkerPool();
        initSocketServer();
    }

    public void execute(){
        acceptConnections();
    }

    private void initSocketServer(){
        try{
            myServerSocket = new ServerSocket(myPort);
            myServerSocket.setSoTimeout(500);
        }catch(IOException ie){
            myController.writeMessage("Erreur de creation du socket server: " + ie.getMessage());
        }
    }

    /**
     * This method verifies if any of the channel is ready to read or write and executes these actions.
     */
    private void acceptConnections(){
        Socket newClientSocket = null;
        SessionJob newSessionJob;
        while(run == true){
            try{
                newClientSocket = this.myServerSocket.accept();
                if(newClientSocket != null){
                    new SessionJob(newClientSocket, this, myChatManager);
                }
            }catch(IOException ie){
                myController.writeMessage("newClientSocket is not null " + ie.getMessage());        //Temp message for testing
            }
        }
    }

    public synchronized void giveJob(Job toGive){
        myWorkerPool.addJob(toGive);
    }

    public String listCurrentJobs(){
        return myWorkerPool.listCurrentJobs();
    }

    public int countFreeWorkers(){
        return myWorkerPool.freeWorkers();
    }

    public void killJob(int toKill){
        myWorkerPool.killJob(toKill);
    }

    public void writeMessage(String myMessage){
        myController.writeMessage(myMessage);
    }
}
