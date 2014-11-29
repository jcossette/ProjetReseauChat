package Server.ChatManager;

import Server.Job;
import Server.ServerController;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Julien Cossette on 11/22/2014.
 */
public class ChatManagerJob extends Job{
    private static ChatManagerJob instance;
    private ColisHandler myColisHandler;
    private UserManager myUserManager;
    private ArrayList<SessionJob> mySessions;
    private BlockingQueue<ColisClient> toTreat;
    private ServerController myController;

    private ChatManagerJob(){
        toTreat = new ArrayBlockingQueue(1024);
        mySessions = new ArrayList();
    }

    public void assignController(ServerController myController){
        this.myController = myController;
    }

    public static ChatManagerJob getInstance(){
        if(instance == null){
            instance = new ChatManagerJob();
            return instance;
        }else{
            return instance;
        }
    }

    public void execute(){
        processColisQueue();
    }

    private void processColisQueue(){
        while(run == true){
            ColisClient toProcess = toTreat.poll();
            myColisHandler.handleColis(toProcess);
        }
    }


    public synchronized void reportColis(ColisClient toOffer){
        myController.writeMessage(
            "Colis type: " +
            toOffer.getMyColis().getType().toString() +
            " // Sent by: " +
            toOffer.getMySession().toString()
        );
        toTreat.offer(toOffer);
    }

    public void addSession(SessionJob newSession){
        mySessions.add(newSession);
    }
}
