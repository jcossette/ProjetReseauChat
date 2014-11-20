package Server;

import java.util.*;

/**
 * This class parses String commands into actual commands.
 * Created by Julien Cossette on 11/7/2014.
 */
public class CommandParser{
    private static CommandParser instance;
    private ServerController myController;

    /**
     * Private constructor.
     */
    private CommandParser(){

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

    public String parseCommand(String toParse){
        char[] toAnalyse = toParse.toCharArray();
        Queue<String> parameters = new LinkedList();
        String temp = "";
        for(int i = 0; i < toAnalyse.length; i++){
            if(toAnalyse[i] != ' '){
                temp += toAnalyse[i];
            }else{
                parameters.offer(temp);
                temp = "";
            }
        }
        parameters.offer(temp);
        return executeCommand(parameters);
    }

    private String executeCommand(Queue<String> commandParameters){
        String category = commandParameters.poll();
        switch(category){
            case "job":
                return jobCommand(commandParameters);
            default:
                return "Command unknown";
        }
    }

    public String jobCommand(Queue<String> commandParameters){

        return "";
    }
}
