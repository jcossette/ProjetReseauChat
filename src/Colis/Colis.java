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

    public Object popParameter(){
        return colisQueue.poll();
    }
}
