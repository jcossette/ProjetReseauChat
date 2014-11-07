package Server;

import java.util.Dictionary;
import java.util.HashMap;

/**
 * This is the Server Controller, it is the interface by which the console will pass commands to the underlying server.
 * Created by Julien Cossette on 11/5/2014.
 */
public class ServerController{
    private static ServerController instance;
    private CommandParser myCommandParser;
    private Server myServer;

    /**
     * Private constructor
     */
    private ServerController(){
        myServer = Server.getInstance();
        myCommandParser = CommandParser.getInstance();
    }

    /**
     * Starts or gets the instance of the singleton.
     * @return Returns the instance of the controller.
     */
    public static ServerController getInstance(){
        if(instance == null){
            instance = new ServerController();
            return instance;
        }else{
            return instance;
        }
    }

    /**
     * Finds the requested command in the Map and executes it.
     * @param toExecute Text command to execute
     * @return Potential function return text.
     */
    public String doThis(String toExecute){
        return myCommandParser.parseCommand(toExecute);
    }

    /**
     * Passes a new Socket Server Job to the server.
     */
    private void addJob(Job toGive){
        myServer.giveJob(toGive);
    }
}
