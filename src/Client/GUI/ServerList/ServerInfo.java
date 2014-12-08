package Client.GUI.ServerList;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Julien Cossette on 12/5/2014.
 */
public class ServerInfo implements Serializable{
    private InetAddress myAddress;
    private int myPort;
    private String serverName;

    public ServerInfo(){

    }

    public InetAddress getMyAddress(){
        return myAddress;
    }

    public void setMyAddress(InetAddress myAddress){
        this.myAddress = myAddress;
    }

    public int getMyPort(){
        return myPort;
    }

    public void setMyPort(int myPort){
        this.myPort = myPort;
    }

    public String getServerName(){
        return serverName;
    }

    public void setServerName(String serverName){
        this.serverName = serverName;
    }

    @Override
    public String toString(){
        return "NOM: " + this.getServerName() + " // IP: " + this.getMyAddress() + " // PORT: " + this.getMyPort();
    }
}
