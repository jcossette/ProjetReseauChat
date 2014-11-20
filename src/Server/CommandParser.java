package Server;

import java.util.*;

/**
 * This class parses String commands into actual commands.
 * Created by Julien Cossette on 11/7/2014.
 */
public class CommandParser{
    private ServerController myController;

    /**
     * Private constructor.
     */
    public CommandParser(ServerController assignedController){
        this.myController = assignedController;
    }

    public void parseCommand(String toParse){
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
        executeCommand(parameters);
    }

    private void executeCommand(Queue<String> commandParameters){
        String category = commandParameters.poll();
        switch(category){
            case "job":
                jobCommand(commandParameters);
                break;
            default:
                myController.writeMessage("Command unknown");
                break;
        }
    }

    private void jobCommand(Queue<String> commandParameters){
        String jobType = commandParameters.poll();
        switch(jobType){
            case "chatmanager":
                myController.createChatManagerJob(commandParameters);
                break;
            case "test":
                myController.createTestJob();
                break;
            case "view":
                myController.viewJobs();
                break;
            case "workers":
                myController.viewFreeWorkers();
                break;
            case "kill":
                myController.killJob(commandParameters);
                break;
            default:
                myController.writeMessage("Command unknown");
                break;
        }
    }
}
