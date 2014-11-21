package Server;

import Server.ChatManager.ColisHandler;
import Server.ChatManager.SessionJob;
import Server.ChatManager.UserManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.*;
import java.util.ArrayList;

/**
 * This job runs a facade managing the different components of the chat server.
 * Created by Julien Cossette on 11/10/2014.
 */
public class ChatServer extends Job{
    private ServerSocket myServerSocket;
    private ArrayList<SessionJob> mySessions;
    private WorkerPool myWorkerPool;
    private int myPort;

    private UserManager myUserManager;
    private ColisHandler myColisHandler;
    private Selector myChannelSelector;
    private ObjectInputStream myInputStream;
    private ObjectOutputStream myOutputStream;

    public ChatServer(int port){
        this.myPort = port;
        this.mySessions = new ArrayList();
        this.myWorkerPool = new WorkerPool();

        this.myUserManager = new UserManager();
        try{
            myChannelSelector = Selector.open();
        }catch(IOException e){
            myController.writeMessage("Erreur de creation du channel selector: " + e.getMessage());
        }
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
                newSessionJob = new SessionJob(newClientSocket);
                mySessions.add(newSessionJob);
            }catch(IOException ie){
                //ZZZZzzzZZZzZzzzZz
            }
        }
    }

    public void giveJob(Job toGive){
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
}
