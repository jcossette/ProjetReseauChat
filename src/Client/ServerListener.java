package Client;

import Colis.Colis;
import Colis.TypeColisEnum;
import GUI.ClientGUI;
import GUI.RoomSelectionGUI;
import GUI.UserConnectionGUI;
import Server.ChatManager.Room;
import Server.ChatManager.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fillioca on 2014-11-19.
 */
public class ServerListener implements Runnable{

    ClientController controller;
    GUIController guiController;
    Thread myThread;
    ObjectInputStream in;
    boolean running;

    public ServerListener(){
        controller = ClientController.getInstance();
        guiController = GUIController.getInstance();
        running = true;

        myThread = new Thread(this, "serverListener");
        myThread.start();
    }

    @Override
    public void run(){
        try {
            in = new ObjectInputStream(controller.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(running == true){
            Colis receivedColis;
            System.out.println("Wow");
            try {
                receivedColis = (Colis)in.readObject();
                System.out.println("Colis received");
                if (receivedColis != null){
                    System.out.println("Not null colis received");
                }
                handleColis(receivedColis);
            } catch (SocketException e) {
                System.out.println("Connection with server lost");
                running = false;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleColis(Colis colis) {
        TypeColisEnum type = colis.getType();
        ClientGUI clientGui;
        if (type == TypeColisEnum.fullUpdate){
            clientGui = new ClientGUI();

            clientGui.fullUpdate((ArrayList<Room>) colis.popParameter());
        }
        else if(type == TypeColisEnum.getRoomList) {
            ArrayList<String> roomList = (ArrayList<String>)colis.popParameter();
            new RoomSelectionGUI(roomList);
        }
        else if(type == TypeColisEnum.roomInfos) {
            clientGui = guiController.getClientGUI();
            clientGui.joinRoom((Room) colis.popParameter());
        }
        else {
            clientGui = guiController.getClientGUI();
            switch (type) {
                case updateText:
                    clientGui.updateText((String)colis.popParameter(), (String)colis.popParameter());
                    break;
                case updateRemoveUserFromRoom:
                    clientGui.removeNameFromRoom((String)colis.popParameter(), (User)colis.popParameter());
                    break;
                case updateRemoveUser:
                    clientGui.removeNameFromAllRooms((User)colis.popParameter());
                    break;
                case updateAddUser:
                    clientGui.addNameToRoom((String) colis.popParameter(), (User)colis.popParameter());
                    break;
                case error:
                    break;
                case acceptedConnection:
                    UserConnectionGUI connectionGui = guiController.getConnectionGUI();
                    connectionGui.closeWindow();
                    controller.getFullUpdate();
                    break;
                case refusedConnection:
                    JOptionPane.showMessageDialog(null, (String)colis.popParameter(), "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    break;
            }
        }
    }
}
