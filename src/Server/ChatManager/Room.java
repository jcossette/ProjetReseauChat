package Server.ChatManager;

import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by Julien Cossette on 11/29/2014.
 */
public class Room{
    private String name;
    private ArrayList<User> myUsers;
    private Deque<String> messageChain;

    public Room(){

    }

    public String getName(){
        return name;
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

    public Deque<String> getMessageChain(){
        return messageChain;
    }

    public void addMessage(String message){
        messageChain.offer(message);
    }
}
