package Server;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is the worker pool. Its function is to accept new jobs and dispatch them to available workers.
 * Created by Julien Cossette on 11/5/2014.
 */
public class WorkerPool{
    private ConcurrentLinkedQueue<Job> jobQueue = new ConcurrentLinkedQueue();
    private ConcurrentLinkedQueue<Worker> freeWorkers = new ConcurrentLinkedQueue();
    private Map<Integer, Job> currentJobs = new HashMap();
    private Queue<Integer> freeNumbers = new LinkedList();
    private int jobTicker = 0;

    private int workerCount; //Number of static worker that will always exist.

    /**
     * Private constructor.
     */
    public WorkerPool(){
        workerCount = Runtime.getRuntime().availableProcessors() * 4;
        init();
    }

    /**
     * Initiates the pool's workers
     */
    private void init(){
        Worker toInit;
        for(int i = 0; i < workerCount; i++){
            toInit = new Worker(this);
            freeWorkers.offer(toInit);
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
            Integer key;
            if(freeNumbers.isEmpty()){
                key = new Integer(jobTicker++);
            }else{
                key = freeNumbers.poll();
            }
            Job toDispatch = jobQueue.poll();
            freeWorkers.poll().giveJob(toDispatch, key);
            addCurrentJob(toDispatch, key);
        }
    }

    /**
     * This method is called by the workers when they finish a job.
     * @param isDone The worker returning
     */
    public synchronized void punchIn(Worker isDone){
        removeCurrentJob(isDone.getCurrentJobID());
        isDone.free();
        freeWorkers.offer(isDone);
        if(!jobQueue.isEmpty()){
            dispatch();
        }
    }

    private synchronized void addCurrentJob(Job toAdd, Integer newKey){
        currentJobs.put(newKey, toAdd);
    }

    private synchronized void removeCurrentJob(Integer IDtoRemove){
        currentJobs.remove(IDtoRemove);
    }

    public synchronized String listCurrentJobs(){
        String compiledJobs = "LIST OF RUNNING JOBS: \n";
        for(Map.Entry<Integer, Job>  j : currentJobs.entrySet()){
            compiledJobs += "ID:" + j.getKey().intValue() + "   JOB:" + j.getValue() + "\n";
        }
        return compiledJobs;
    }

    public synchronized int freeWorkers(){
        return freeWorkers.size();
    }

    public synchronized void killJob(int IDtoKill){
        for(Map.Entry<Integer, Job>  j : currentJobs.entrySet()){
            if(j.getKey().intValue() == IDtoKill){
                j.getValue().stop();
                currentJobs.remove(j.getKey());
            }
        }
    }
}
