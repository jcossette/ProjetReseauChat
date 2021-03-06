package Client.GUI.ServerList.Controllers;

import Client.ClientController;
import Client.GUI.ServerList.Context;
import Client.GUI.ServerList.Interfaces.Observer;
import Client.GUI.ServerList.ServerInfo;
import Client.GUI.SetupScreen;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * This is the controller used by the setup screen to add servers to the context and also join servers.
 * Created by Julien Cossette on 12/5/2014.
 */
public class SetupScreenController implements Observer{
    private SetupScreen myUI;
    private Context myUserContext;

    /**
     * Assigns a SetupScreen to use this controller.
     * @param toControl
     */
    public SetupScreenController(SetupScreen toControl){
        this.myUserContext = Context.getInstance();
        this.myUI = toControl;
        toControl.assignController(this);
        myUserContext.addObserver(this);
    }

    public void update(){
        myUI.updateServerList();
    }

    public void addServer(String newServerName, String newServerIP, String newServerPort){
        String myName = checkServerName(newServerName);
        InetAddress myIP = checkServerIP(newServerIP);
        int myPort = checkServerPort(newServerPort);

        if(
            myName != null &&
            myIP != null &&
            myPort > 0
        ){
            myUserContext.addServer(myName, myIP, myPort);
        }
    }

    public void removeServer(ServerInfo toRemove){
        myUserContext.removeServer(toRemove);
    }

    private int checkServerPort(String toCheck){
        try{
            int myPort = Integer.parseInt(toCheck);
            if(myPort <= 65635 && myPort > 1024){
                return myPort;
            }else{
                JOptionPane.showMessageDialog(null, "Veuillez entrer une IP valide et un port entre 1024 et 65535");
                return -1;
            }
        }catch(NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Numero de port invalide");
            return -1;
        }
    }

    private InetAddress checkServerIP(String toCheck){
        try{
            if(toCheck.equals("")){
                JOptionPane.showMessageDialog(null, "IP invalide");
                return null;
            }
            return InetAddress.getByName(toCheck);
        }catch(UnknownHostException uhe){
            JOptionPane.showMessageDialog(null, "IP invalide");
            return null;
        }
    }

    private String checkServerName(String toCheck){
        if(toCheck.length() < 2){
            JOptionPane.showMessageDialog(null, "Le nom du serveur doit avoir au moin 3 lettres.");
            return null;
        }
        return toCheck;
    }

    public ArrayList<ServerInfo> getServerList(){
        return myUserContext.getServerList();
    }

    public void joinServer(ServerInfo toJoin, String nickname){
        ClientController myClientController = ClientController.getInstance();
        try{
            myClientController.createSocket(toJoin.getMyAddress().getHostAddress(), String.valueOf(toJoin.getMyPort()));

            /**
             * Executes after socket is created
             * Initialize the serverListener thread and shows the UserConnectionGUI
             */
            myClientController.initListener();
            myClientController.connect(nickname);
            myUI.setVisible(false);
            myUI.dispose();
        }catch(IOException e1){
            JOptionPane.showMessageDialog(null, "Échec de connection au serveur", "Ereurr",
                    JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
        }
    }
}
