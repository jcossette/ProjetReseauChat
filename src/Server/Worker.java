package Server;

/**
 * Created by Julien Cossette on 11/5/2014.
 */
public class Worker implements Runnable{
    private Job myJob;
    private int status;

    public Worker(){

    }

    @Override
    public void run(){
        if(myJob != null){
            status = 1;
            myJob.execute();
        }
        status = 0;
    }

    public void giveJob(Job toGive){
        myJob = toGive;
    }

    /**
     * Returns the status of the worker
     * @return 0 = Free, 1 = Running
     */
    public int getStatus(){
        return status;
    }
}
