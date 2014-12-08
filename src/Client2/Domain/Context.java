package Client2.Domain;

import Client2.Domain.Interfaces.Observable;
import Client2.Domain.Interfaces.Observer;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Class that keeps the information on the current client user session. This keeps a list of saved Users as well as
 * the previously used user nickname.
 * Created by Julien Cossette on 12/5/2014.
 */
public class Context implements Observable{
    private static Context instance;
    private static UserPref currentUserPref;
    private static ArrayList<ServerInfo> myServerList;
    private static ArrayList<Observer> myObservers;

    private Context(){
        myObservers = new ArrayList<>();
        myServerList = new ArrayList<>();
    }

    public void addObserver(Observer myNewObserver){
        myObservers.add(myNewObserver);
    }

    /**
     * Singleton initiator, will load the previously saved client context or initiate a new context
     * @return
     */
    public static Context getInstance(){
        if(instance == null){
            instance = new Context();
            loadUserSettings();
            return instance;
        }else{
            return instance;
        }
    }

    /**
     * Adds a new server to the server list of this user
     * @param name  The name of the server
     * @param IP    The IP of the server
     * @param port  The port of the server
     */
    public void addServer(String name, InetAddress IP, int port){
        ServerInfo newServer = new ServerInfo();
        newServer.setServerName(name);
        newServer.setMyAddress(IP);
        newServer.setMyPort(port);
        myServerList.add(newServer);
        //We hard save the settings upon the change.
        saveUserSettings();
        notifyObservers();
    }

    /**
     * Sends a signal to each observer that our state has changed
     */
    private void notifyObservers(){
        for(Observer o : myObservers){
            o.update();
        }
    }

    private void saveUserSettings(){
        currentUserPref.setMyFavoriteServers(myServerList);
        try{
            FileOutputStream myOutputStream = new FileOutputStream("myUserPref.save");
            ObjectOutputStream myOutput = new ObjectOutputStream(myOutputStream);
            myOutput.writeObject(currentUserPref);
            myOutput.close();
        }catch(IOException ie){
            JOptionPane.showMessageDialog(null, "Error saving user profile");
        }
    }

    private static void loadUserSettings(){
        try{
            FileInputStream myInputStream = new FileInputStream("myUserPref.save");
            ObjectInputStream myInput = new ObjectInputStream(myInputStream);
            currentUserPref = (UserPref)myInput.readObject();
            myServerList = currentUserPref.getMyFavoriteServers();
            myInput.close();
        }catch(IOException ie){
            JOptionPane.showMessageDialog(null, "Error loading user profile: " + ie.getMessage());
            currentUserPref = new UserPref();
        }catch(ClassNotFoundException cnfe){
            JOptionPane.showMessageDialog(null, "Class not found: " + cnfe.getMessage());
            currentUserPref = new UserPref();
        }
    }

    public ArrayList<ServerInfo> getServerList(){
        return myServerList;
    }

    public void removeServer(ServerInfo toRemove) {
        myServerList.remove(toRemove);
    }
}
