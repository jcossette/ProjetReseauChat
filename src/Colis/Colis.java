package Colis;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by pewtroof on 2014-11-17.
 */
public class Colis
{
    Queue colisQueue = new LinkedList();
    TypeColisEnum type;

    public Colis(TypeColisEnum type)
    {
        this.type = type;
    }

    public void SetParameter(String parameter)
    {
        colisQueue.add(parameter);
    }

    public void SetParameter(List parameter)
    {
        colisQueue.add(parameter);
    }
}
