package Client;

import Colis.Colis;
import Colis.TypeColisEnum;
import GUI.ClientGUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by fillioca on 2014-11-19.
 */
public class ServerListener implements Runnable{

    ClientController controller;
    GUIController guiController;
    ObjectInputStream in;

    public ServerListener(){
        controller.getInstance();
        guiController.getInstance();
    }

    @Override
    public void run(){
        try {
            in = new ObjectInputStream(controller.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            Colis receivedColis;
            try {
                receivedColis = (Colis)in.readObject();
                handleColis(receivedColis);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleColis(Colis colis){
        TypeColisEnum type = colis.getType();
        ClientGUI clientGui = guiController.getClientGUI();

        ArrayList<String> resultList = colis.getParameters();
        switch(type){
            case updateText:
                clientGui.updateText(resultList.get(0), resultList.get(1), resultList.get(2));
                break;
            case updateRemoveUser:
                break;
            case updateAddUser:
                break;
            case error:
                break;
            default:
                break;
        }
    }
}
