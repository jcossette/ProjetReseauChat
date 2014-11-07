package Server;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the worker pool. Its function is to accept new jobs and dispatch them to available workers.
 * Created by Julien Cossette on 11/5/2014.
 */
public class WorkerPool{
    private static WorkerPool instance;
    private ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue<Job>();
    private ConcurrentLinkedQueue<Worker> freeWorkers = new ConcurrentLinkedQueue<Worker>();

    private int workerCount; //Number of static worker that will always exist.

    /**
     * Private constructor.
     */
    private WorkerPool(){
        workerCount = Runtime.getRuntime().availableProcessors() * 4;
        init();
    }

    /**
     * Initiates the pool's workers
     */
    private void init(){
        Worker toInit;
        for(int i = 0; i < workerCount; i++){
            toInit = new Worker();
            freeWorkers.offer(toInit);
        }
    }

    /**
     * Starts or gets the instance of the singleton.
     * @return The singleton instance
     */
    public static WorkerPool getInstance(){
        if(instance == null){
            instance = new WorkerPool();
            return instance;
        }else{
            return instance;
        }
    }

    /**
     * Offers a new job to the current job queue.
     * @param toAdd A new job to give to the worker pool.
     */
    public void addJob(Job toAdd){
        jobQueue.offer(toAdd);
        dispatch();
    }

    /**
     * Dispatches a worker to the next Job.
     */
    public void dispatch(){
        if((freeWorkers.peek() != null) && (jobQueue.peek() != null)){
            freeWorkers.poll().giveJob(jobQueue.poll());
        }
    }

    /**
     * This method is called by the workers when they finish a job.
     * @param isDone The worker returning
     */
    public synchronized void punchIn(Worker isDone){
        freeWorkers.offer(isDone);
        if(!jobQueue.isEmpty()){
            dispatch();
        }
    }

}
