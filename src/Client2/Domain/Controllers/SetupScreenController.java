package Client2.Domain.Controllers;

import Client2.Domain.Context;
import Client2.Domain.Interfaces.Observer;
import Client2.Domain.ServerInfo;
import Client2.GUI.SetupScreen;

import javax.swing.*;
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
        JOptionPane.showMessageDialog(null, "Connection en cour");

        //TODO
        //Connect to server
        //Open Chat Window
    }
}
