package Server;

import java.util.HashMap;
import java.util.Map;

/**
 * This classes parses String commands into actual commands.
 * Created by Julien Cossette on 11/7/2014.
 */
public class CommandParser{
    private static CommandParser instance;
    private ServerController myController;
    private Map<String, Command> commands = new HashMap<String, Command>();

    /**
     * Private constructor.
     */
    private CommandParser(){
        myController = ServerController.getInstance();
    }

    /**
     * Starts or gets the instance of the singleton.
     * @return
     */
    public static CommandParser getInstance(){
        if(instance == null){
            instance = new CommandParser();
            return instance;
        }else{
            return instance;
        }
    }

    public void initCommands(){
        commands.put("job", (String parameters) -> this.jobCommand(parameters));
    }

    public String parseCommand(String toParse){
        return "";
    }

    public String jobCommand(String parameters){

        return "";
    }
}
