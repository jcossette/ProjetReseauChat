package Server;

import Server.ChatManager.ChatManagerJob;
import Server.ChatManager.SessionJob;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This job runs a facade managing the different components of the chat server.
 * Created by Julien Cossette on 11/10/2014.
 */
public class ChatServerJob extends Job{
    private ServerSocket myServerSocket;
    private ChatManagerJob myChatManagerJob;
    private WorkerPool myWorkerPool;
    private ServerController myController;
    private int myPort;

    public ChatServerJob(int port, ServerController myController){
        this.myChatManagerJob = ChatManagerJob.getInstance();
        this.myChatManagerJob.assignController(myController);
        this.myController = myController;
        this.myPort = port;
        this.myWorkerPool = new WorkerPool();
        initSocketServer();
    }

    public void execute(){
        acceptConnections();
    }

    private void initSocketServer(){
        try{
            myServerSocket = new ServerSocket(myPort);
            myServerSocket.setSoTimeout(1500);
        }catch(IOException ie){
            myController.writeMessage("Erreur de creation du socket server: " + ie.getMessage());
        }
    }

    /**
     * This method verifies if any of the channel is ready to read or write and executes these actions.
     */
    private void acceptConnections(){
        Socket newClientSocket;
        SessionJob newSessionJob;
        while(run == true){
            try{
                newClientSocket = this.myServerSocket.accept();
                if(newClientSocket != null){
                    newSessionJob = new SessionJob(newClientSocket, this, myChatManagerJob);
                    myChatManagerJob.addSession(newSessionJob);
                    myWorkerPool.addJob(newSessionJob);
                }
            }catch(IOException ie){
                myController.writeMessage("Server listening for connections: " + ie.getMessage());        //Temp message for testing
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
