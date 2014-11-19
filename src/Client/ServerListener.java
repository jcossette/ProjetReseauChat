package Client;

import Colis.Colis;
import Colis.TypeColisEnum;
import GUI.ClientGUI;
import GUI.UserConnectionGUI;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fillioca on 2014-11-19.
 */
public class ServerListener implements Runnable{

    ClientController controller;
    GUIController guiController;
    ObjectInputStream in;

    public ServerListener(){
        controller = ClientController.getInstance();
        guiController = GUIController.getInstance();
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
        if (type == TypeColisEnum.fullUpdate){
            ArrayList<List> fullUpdateList = colis.getFullUpdateParameters();
            //clientGui.fullUpdate(fullUpdateList.get(0), fullUpdateList.get(1), fullUpdateList.get(2));
        }
        else {
            ArrayList<String> resultList = colis.getParameters();
            switch (type) {
                case updateText:
                    //clientGui.updateText(resultList.get(0), resultList.get(1), resultList.get(2));
                    break;
                case updateRemoveUser:
                    //clientGui.removeNameToRoom(resultList.get(0), resultList.get(1));
                    break;
                case updateAddUser:
                    //clientGui.addNameFromRoom(resultList.get(0), resultList.get(1));
                    break;
                case error:
                    break;
                case acceptedConnection:
                    UserConnectionGUI connectionGui = guiController.getConnectionGUI();
                    new ClientGUI();
                    connectionGui.closeWindow();
                    break;
                case refusedConnection:
                    JOptionPane.showMessageDialog(null, resultList.get(0), "Ereurr",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    break;
            }
        }
    }
}
