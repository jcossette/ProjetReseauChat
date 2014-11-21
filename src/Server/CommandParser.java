package Server;

import java.util.*;

/**
 * This class parses String commands into actual commands.
 * Created by Julien Cossette on 11/7/2014.
 */
public class CommandParser{
    private ServerController myController;

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
        selectServer(parameters);
    }

    private void selectServer(Queue<String> commandParameters){
        String server = commandParameters.poll();
        switch(server){
            case "start":
                startServer(commandParameters);
                break;
            case "server":
                executeCommand(commandParameters);
                break;
            default:
                myController.writeMessage("Command unknown");
                break;
        }
    }

    private void startServer(Queue<String> commandParameters){
        String serverJobName = commandParameters.poll();
        switch(serverJobName){
            case "chatserver":
                myController.startChatServerJob(commandParameters);
                break;
            default:
                myController.writeMessage("Command unknown");
                break;
        }
    }

    private void executeCommand(Queue<String> commandParameters){
        String category = commandParameters.poll();
        switch(category){
            case "addjob":
                addJobCommand(commandParameters);
                break;
            case "job":
                optionJobCommand(commandParameters);
                break;
            default:
                myController.writeMessage("Command unknown");
                break;
        }
    }

    private void addJobCommand(Queue<String> commandParameters){
        String jobCommand = commandParameters.poll();
        switch(jobCommand){
            case "test":
                myController.createTestJob();
                break;
            default:
                myController.writeMessage("Command unknown");
                break;
        }
    }

    private void optionJobCommand(Queue<String> commandParameters){
        String jobCommand = commandParameters.poll();
        switch(jobCommand){
            case "view":
                myController.viewJobs();
                break;
            case "workers":
                myController.viewFreeWorkers();
                break;
            case "kill":
                myController.killJob(commandParameters);
                break;
        }
    }
}
