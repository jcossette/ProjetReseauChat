package Server;

/**
 * Created by Julien Cossette on 11/5/2014.
 */
public class Worker implements Runnable{
    private WorkerPool myPool;
    private Job myJob;
    private Integer myJobID;
    private int status;
    private Thread myThread;

    public Worker(WorkerPool myPool){
        this.myPool = myPool;
        myThread = new Thread(this, "workerThread");
    }

    @Override
    public void run(){
        status = 1;
        myJob.execute();
        status = 0;
        myPool.punchIn(this);
    }

    public void giveJob(Job toGive, Integer ID){
        myJobID = ID;
        myJob = toGive;
        status = 1;
        myThread.start();
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
        myJobID = null;
        status = 0;
    }
}
