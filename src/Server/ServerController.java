package Server;

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
        this.myCommandParser = CommandParser.getInstance();
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
    public String doThis(String toExecute){
        return myCommandParser.parseCommand(toExecute);
    }

    /**
     * This method is called to display message in the server console.
     * @param myMessage
     */
    public void writeMessage(String myMessage){
        //Send message to console for write
    }

    /**
     * Passes a new Socket Server Job to the server.
     */
    private void addJob(Job toGive){
        toGive.assignController(this);
        myServer.giveJob(toGive);
    }
}
