package Server;

import java.util.Queue;

/**
 * This is the Server Controller, it is the interface by which the console will pass commands to the underlying server.
 * Created by Julien Cossette on 11/5/2014.
 */
public class ServerController{
    private ChatServerJob myServer;
    private CommandParser myCommandParser;
    private ServerConsoleGUI myGUI;

    /**
     * Private constructor
     */
    public ServerController(){
        this.myCommandParser = new CommandParser(this);
    }

    public void hookGUI(ServerConsoleGUI toHook){
        this.myGUI = toHook;
    }

    /**
     * Parses the requested command and executes it.
     * @param toExecute Text command to execute
     * @return Potential function return text.
     */
    public void doThis(String toExecute){
        myCommandParser.parseCommand(toExecute);
    }

    /**
     * This method is called to display a message in the server console.
     * @param myMessage
     */
    public synchronized void writeMessage(String myMessage){
        myGUI.writeToConsole(myMessage);
    }

    /**
     * Passes a new Socket Server Job to the server.
     */
    private void addJob(Job toGive){
        toGive.assignController(this);
        myServer.giveJob(toGive);
    }

    public void startChatServerJob(Queue<String> parameters){
        try{
            Integer port = Integer.parseInt(parameters.poll());
            ChatServerJob newChatServerJob = new ChatServerJob(port, this);
            this.myServer = newChatServerJob;
            addJob(newChatServerJob);
            writeMessage("Chat Manager Job created!");
        }catch(Exception e){
            writeMessage("Chat Manager Job creation command error: " + e.toString());
        }
    }

    public void createTestJob(){
        TestJob newTestJob = new TestJob();
        addJob(newTestJob);
    }

    public void viewJobs(){
        writeMessage(myServer.listCurrentJobs());
    }

    public void viewFreeWorkers(){
        writeMessage("NUMBER OF FREE WORKERS: " + myServer.countFreeWorkers());
    }

    public void killJob(Queue<String> parameters){
        try{
            int IDtoKill = Integer.parseInt(parameters.poll());
            myServer.killJob(IDtoKill);
            writeMessage("Job Stopped: " + IDtoKill);
        }catch(Exception e){
            writeMessage("Kill Job Command error: " + e.toString());
        }
    }
}
