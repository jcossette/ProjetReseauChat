package Server.ChatManager;

import Server.Job;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
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
    private Selector myChannelSelector;

    public ChatManagerJob(){
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
            if(!registerQueue.isEmpty()){
                registerNewSession();
            }
            monitorChannels();
        }
    }

    private void monitorChannels(){

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
