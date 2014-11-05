package Server;

import java.util.HashMap;

/**
 * Created by Julien Cossette on 11/5/2014.
 */
public class ServerController{
    private ServerController instance;
    private HashMap<String, Integer> functions = new HashMap<String, Integer>();

    /**
     * Private constructor
     */
    private ServerController(){}

    /**
     * Starts or gets the instance of the singleton.
     * @return
     */
    public ServerController getInstance(){
        if(instance == null){
            instance = new ServerController();
            return instance;
        }else{
            return instance;
        }
    }

    /**
     * Finds the requested command in the Map and executes it.
     * @param toExecute
     * @return
     */
    public int doThis(String toExecute){
        int ID = functions.get(toExecute);
        switch(ID){
            case 0: return 0;
            default: return -1;
        }
    }
}
