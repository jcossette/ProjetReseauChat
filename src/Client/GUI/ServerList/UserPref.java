package Client.GUI.ServerList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Julien Cossette on 12/5/2014.
 */
public class UserPref implements Serializable{
    private ArrayList<ServerInfo> myFavoriteServers;
    private String myNickname;

    public ArrayList<ServerInfo> getMyFavoriteServers(){
        return myFavoriteServers;
    }

    public void setMyFavoriteServers(ArrayList<ServerInfo> myFavoriteServers){
        this.myFavoriteServers = myFavoriteServers;
    }

    public String getMyNickname(){
        return myNickname;
    }

    public void setMyNickname(String myNickname){
        this.myNickname = myNickname;
    }
}
