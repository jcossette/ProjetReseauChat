package Client;

import GUI.ClientGUI;

/**
 * Created by fillioca on 2014-11-19.
 */
public class GUIController {
    private static GUIController instance;
    private ClientGUI clientGui;

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

}
