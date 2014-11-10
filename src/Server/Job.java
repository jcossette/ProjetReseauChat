package Server;

/**
 * All jobs must inherit from this class.
 * Created by Julien Cossette on 11/5/2014.
 */
public abstract class Job{

    private int priority;
    private int numExecution;

    /**
     * This method executes the code of the job
     */
    public abstract void execute();

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public int getNumExecution(){
        return numExecution;
    }

    public void setNumExecution(int numExecution){
        this.numExecution = numExecution;
    }
}