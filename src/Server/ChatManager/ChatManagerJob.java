package Server.ChatManager;

import Server.Job;
import Colis.Colis;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This job runs a facade managing the different components of the chat server.
 * Created by Julien Cossette on 11/10/2014.
 */
public class ChatManagerJob extends Job {
    private ArrayList<Session> mySessions;
    private Queue<SocketChannel> registerQueue = new LinkedList();
    private UserManager myUserManager;
    private ColisHandler myColisHandler;
    private Selector myChannelSelector;
    private ObjectInputStream myInputStream;
    private ObjectOutputStream myOutputStream;

    public ChatManagerJob(){
        myColisHandler = ColisHandler.getInstance();
        mySessions = new ArrayList();
        myUserManager = new UserManager();
        try{
            myChannelSelector = Selector.open();
        }catch(IOException e){
            myController.writeMessage("Erreur de creation du channel selector: " + e.getMessage());
        }
    }

    public void execute(){
        while(run == true){
            doTasks();
            startWaiting();
        }
    }

    /**
     * This method puts the thread into a waiting state until it is notified of a task to execute.
     */
    private void startWaiting(){
        try{
            this.wait();
        }catch(InterruptedException ie){
            myController.writeMessage("Thread interupted: " + ie.getMessage());
        }
    }

    /**
     * If we have new session to register this method will register them but only in groups of 5, after which it will
     * manage its channels and then continue processing new sessions. Otherwise it will simply manage its channels.
     */
    private void doTasks(){
        while(!registerQueue.isEmpty()){
            registerNewSession();
            monitorChannels();
        }
        monitorChannels();
    }

    /**
     * This method verifies if any of the channel is ready to read or write and executes these actions.
     */
    private void monitorChannels(){
        try {
            if(myChannelSelector.select() > 0) {
                Iterator selectedKeys = this.myChannelSelector.selectedKeys().iterator();
                while(selectedKeys.hasNext()){
                    SelectionKey key = (SelectionKey)selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if(key.isReadable()){
                        readChannel(key);
                    }

                    if(key.isWritable()){
                        writeChannel(key);
                    }

                }
            }
        }catch(IOException e){
            myController.writeMessage("Erreur de vérification de l'état du selector: " + e.getMessage());
        }
    }

    private void readChannel(SelectionKey toRead){
        Session sender = (Session)toRead.attachment();
        Colis toHandle;
        try{
            SocketChannel mySocketChannel = (SocketChannel)toRead.channel();
            myInputStream = new ObjectInputStream(mySocketChannel.socket().getInputStream());
            toHandle = (Colis)myInputStream.readObject();
            myColisHandler.handleColis(toHandle, sender);
        }catch(IOException ie){
            myController.writeMessage("Erreur d'ecriture sur le stream: " + ie.getMessage());
        } catch (ClassNotFoundException cnfe) {
            myController.writeMessage("Classe introuvable " + cnfe.getMessage());
        }
    }

    private void writeChannel(SelectionKey toWrite){

    }

    private void registerNewSession(){
        int registerLimit = 5;
        while(!registerQueue.isEmpty() && registerLimit > 0){
            SocketChannel myChannel = registerQueue.poll();
            try {
                Session newSession = new Session();
                SelectionKey newKey = myChannel.register(myChannelSelector, SelectionKey.OP_READ & SelectionKey.OP_WRITE);
                newKey.attach(newSession);
            } catch (ClosedChannelException ce) {
                myController.writeMessage("Le channel demandé est fermé: " + ce.getMessage());
            }
            registerLimit--;
        }
    }

    public void addToRegisterQueue(SocketChannel myChannel){
        registerQueue.offer(myChannel);
    }


}
