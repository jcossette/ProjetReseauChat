package Server;

import Server.ChatManager.ChatManagerJob;

import java.util.Queue;

/**
 * This is the Server Controller, it is the interface by which the console will pass commands to the underlying server.
 * Created by Julien Cossette on 11/5/2014.
 */
public class ServerController{
    private static ServerController instance;
    private CommandParser myCommandParser;
    private Server myServer;
    private ServerConsoleGUI myGUI;

    /**
     * Private constructor
     */
    private ServerController(){
        this.myServer = Server.getInstance();
        this.myCommandParser = new CommandParser(this);
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

    public void hookGUI(ServerConsoleGUI toHook){
        this.myGUI = toHook;
    }

    /**
     * Finds the requested command in the Map and executes it.
     * @param toExecute Text command to execute
     * @return Potential function return text.
     */
    public void doThis(String toExecute){
        myCommandParser.parseCommand(toExecute);
    }

    /**
     * This method is called to display message in the server console.
     * @param myMessage
     */
    public void writeMessage(String myMessage){
        myGUI.writeToConsole(myMessage);
    }

    /**
     * Passes a new Socket Server Job to the server.
     */
    private void addJob(Job toGive){
        toGive.assignController(this);
        myServer.giveJob(toGive);
    }

    public void createChatManagerJob(Queue<String> parameters){
        try{
            Integer port = Integer.parseInt(parameters.poll());
            ChatManagerJob newChatManager = new ChatManagerJob(port);
            addJob(newChatManager);
            writeMessage("Chat Manager Job created!");
        }catch(Exception e){
            writeMessage("Chat Manager Job creation command error");
        }
    }

    public void viewJobs(){
        writeMessage(myServer.listCurrentJobs());
    }

    public void viewFreeWorkers(){
        writeMessage("NUMBER OF FREE WORKERS: " + myServer.countFreeWorkers());
    }
}
