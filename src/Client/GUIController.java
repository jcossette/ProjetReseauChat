package Client;

import GUI.ClientGUI;
import GUI.UserConnectionGUI;

/**
 * Created by fillioca on 2014-11-19.
 */
public class GUIController {
    private static GUIController instance;
    private ClientGUI clientGui;
    private UserConnectionGUI connectionGui;

    private GUIController(){
    }

    public static GUIController getInstance(){
        if(instance == null){
            instance = new GUIController();
            return instance;
        } else{
            return instance;
        }
    }

    public void setClientGui(ClientGUI clientGui){
        this.clientGui = clientGui;
    }

    public ClientGUI getClientGUI(){
        return clientGui;
    }

    public void setConnectionGui(UserConnectionGUI connectionGui){
        this.connectionGui = connectionGui;
    }

    public UserConnectionGUI getConnectionGUI(){
        return connectionGui;
    }
}
