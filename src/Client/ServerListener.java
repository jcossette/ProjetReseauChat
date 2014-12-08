package Client;

import Colis.Colis;
import Colis.TypeColisEnum;
import Client.GUI.ClientGUI;
import Client.GUI.RoomSelectionGUI;
import Server.ChatManager.Room;
import Server.ChatManager.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by fillioca on 2014-11-19.
 */
public class ServerListener implements Runnable{

    ClientController controller;
    ClientGUI myGUI;
    Thread myThread;
    ObjectInputStream in;
    boolean running;

    public ServerListener(){
        controller = ClientController.getInstance();
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
            try {
                receivedColis = (Colis)in.readObject();
                System.out.println("Colis received");
                handleColis(receivedColis);
            } catch (SocketException e) {
                System.out.println("Connection with server lost");
                running = false;
                JOptionPane.showMessageDialog(null, "Connection to server lost", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleColis(Colis colis) {
        TypeColisEnum type = colis.getType();
        if (type == TypeColisEnum.fullUpdate){
            /** Affiche le GUI principal avec les informations du lobby */
            myGUI = new ClientGUI();
            myGUI.fullUpdate((ArrayList<Room>) colis.popParameter());
        }
        else if(type == TypeColisEnum.roomList) {
            /** Affiche le GUI de d'ajout de Room */
            ArrayList<Room> roomList = (ArrayList<Room>)colis.popParameter();
            new RoomSelectionGUI(roomList, myGUI);
        }
        else {
            switch (type) {
                case updateText:
                    /** Update le texte de la room concernee */
                    myGUI.updateText((Integer)colis.popParameter(), (String)colis.popParameter());
                    break;
                case updateRemoveUserFromRoom:
                    /** Update la liste de user de la room concernee */
                    myGUI.removeNameFromRoom((int)colis.popParameter(), (User)colis.popParameter());
                    break;
                case updateRemoveUser:
                    /** Update la liste de user de toute les rooms */
                    myGUI.removeNameFromAllRooms((User) colis.popParameter());
                    break;
                case updateAddUser:
                    /** Update la liste de user de la room concernee */
                    myGUI.addNameToRoom((String) colis.popParameter(), (User)colis.popParameter());
                    break;
                case joinRoom:
                    /** Cree une tab pour la room cree ou jointe */
                    myGUI.createRoomTab((Room) colis.popParameter());
                    break;
                case acceptedConnection:
                    /** Affiche le GUI de chat principal lorsque la connexion est acceptee */
                    controller.getFullUpdate();
                    break;
                case refusedConnection:
                    /** Affiche un message d'ereur lorsque le nom d'utilisateur n'est pas disponible */
                    JOptionPane.showMessageDialog(null, colis.popParameter(), "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case updateListRequest:
                    /** Affiche la liste des clients  dans un JoptionPane */
                    ArrayList<User> userList = (ArrayList<User>)colis.popParameter();
                    String stringList = "";
                    for (User u : userList){
                        stringList = stringList + u.getUsername() + "\n";
                    }
                    JOptionPane.showMessageDialog(null, stringList, "Liste d'utilisateurs",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    break;
            }
        }
    }
}
