package Server.ChatManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Julien Cossette on 11/29/2014.
 */
public class Room implements Serializable{
    private String name;
    private ArrayList<User> myUsers;
    private Deque<String> messageChain;
    private Integer myID;
    private static int IDticker = 0;

    public Room(){
        messageChain = new LinkedList();
        myUsers = new ArrayList();
        myID = new Integer(IDticker++);
    }

    public String getName(){
        return name;
    }

    public Integer getID(){
        return myID;
    }

    public void setName(String myName){
        this.name = myName;
    }

    public ArrayList<User> getMyUsers(){
        return myUsers;
    }

    public void addUser(User toAdd){
        myUsers.add(toAdd);
    }

    public void removeUser(User toRemove){
        myUsers.remove(toRemove);
    }

    public Deque<String> getMessageChain(){
        return messageChain;
    }

    public void addMessage(String message){
        messageChain.offer(message);
    }

    @Override
    public String toString(){
        return name;
    }
}
