package Server;

/**
 * Created by Julien Cossette on 11/5/2014.
 */
public class Worker implements Runnable{
    private WorkerPool myPool;
    private Job myJob;
    private Integer myJobID;
    private int status;

    public Worker(WorkerPool myPool){
        this.myPool = myPool;
    }

    @Override
    public void run(){
        if(myJob != null){
            status = 1;
            myJob.execute();
        }
        status = 0;
        myPool.punchIn(this);
    }

    public void giveJob(Job toGive, Integer ID){
        myJobID = ID;
        myJob = toGive;
        status = 1;
    }

    /**
     * Returns the status of the worker
     * @return 0 = Free, 1 = Running
     */
    public int getStatus(){
        return status;
    }

    public Job getCurrentJob(){
        return myJob;
    }

    public Integer getCurrentJobID(){
        return myJobID;
    }

    public void free(){
        myJob = null;
        status = 0;
    }
}
