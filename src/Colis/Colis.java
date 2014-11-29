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

    public void addParameter(Object parameter)
    {
        colisQueue.offer(parameter);
    }

    public TypeColisEnum getType(){
        return type;
    }

    public ArrayList<String> getParameters(){
        ArrayList<String> resultList = new ArrayList<>();

        while(!colisQueue.isEmpty()){
            resultList.add((String) colisQueue.poll());
        }

        return resultList;
    }

    public Object popParemeter(){
        return colisQueue.poll();
    }

    public ArrayList<List> getFullUpdateParameters(){
        ArrayList<List> resultList = new ArrayList<>();

        while(!colisQueue.isEmpty()){
            resultList.add((List)colisQueue.poll());
        }

        return resultList;
    }
}
