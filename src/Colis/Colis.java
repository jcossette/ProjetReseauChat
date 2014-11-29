package Colis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by pewtroof on 2014-11-17.
 */
public class Colis implements Serializable
{
    Queue colisQueue = new LinkedList();
    TypeColisEnum type;

    public Colis(TypeColisEnum type)
    {
        this.type = type;
    }

    public void setParameter(String parameter)
    {
        colisQueue.add(parameter);
    }

    public void setParameter(List parameter)
    {
        colisQueue.add(parameter);
    }

    public TypeColisEnum getType(){
        return type;
    }

    public ArrayList<String> getParameters(){
        ArrayList<String> resultList = new ArrayList<>();

        while(!colisQueue.isEmpty()){
            resultList.add((String)colisQueue.poll());
        }

        return resultList;
    }

    public String popParemeter(){
        return (String) colisQueue.poll();
    }

    public ArrayList<List> getFullUpdateParameters(){
        ArrayList<List> resultList = new ArrayList<>();

        while(!colisQueue.isEmpty()){
            resultList.add((List)colisQueue.poll());
        }

        return resultList;
    }
}
