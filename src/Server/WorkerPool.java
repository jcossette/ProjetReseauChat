package Server;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Julien Cossette on 11/5/2014.
 */
public class WorkerPool{
    private WorkerPool instance;
    private ConcurrentLinkedQueue<Job> JobQueue = new ConcurrentLinkedQueue<Job>();

    private boolean dynamicMode = true; //Dynamic mode enables the creation of new workers when the pool is empty.
    private int maxDynamicWorker; //Maximum number of dynamic worker that can be spawned in dynamic mode.
    private int staticWorkerCount; //Number of static worker that will always exist.

    /**
     * Private constructor.
     */
    private WorkerPool(){
        setMaxDynamicWorker(Runtime.getRuntime().availableProcessors() * 4);
        setStaticWorkerCount(5);
    }

    /**
     * Starts or gets the instance of the singleton.
     * @return
     */
    public WorkerPool getInstance(){
        if(instance == null){
            instance = new WorkerPool();
            return instance;
        }else{
            return instance;
        }
    }

    /**
     * Retrieves the next job to be done by a worker.
     * @return
     */
    public Job getJob(){
        return JobQueue.poll();
    }

    /**
     * Offers a new job to the current job queue.
     * @param toAdd
     */
    public void addJob(Job toAdd){
        JobQueue.offer(toAdd);
    }

    public boolean isDynamicMode(){
        return dynamicMode;
    }

    public void setDynamicMode(boolean dynamicMode){
        this.dynamicMode = dynamicMode;
    }

    public int getMaxDynamicWorker(){
        return maxDynamicWorker;
    }

    public void setMaxDynamicWorker(int maxDynamicWorker){
        this.maxDynamicWorker = maxDynamicWorker;
    }

    public int getStaticWorkerCount(){
        return staticWorkerCount;
    }

    public void setStaticWorkerCount(int staticWorkerCount){
        this.staticWorkerCount = staticWorkerCount;
    }
}
