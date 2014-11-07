package Server;

/**
 * This is the server singleton, the server is a facade singleton representing the actual server.
 * Created by Julien Cossette on 11/5/2014.
 */
public class Server{
    private static Server instance;
    private WorkerPool myWorkerPool;

    /**
     * Private constructor
     */
    private Server(){
        myWorkerPool = myWorkerPool.getInstance();
    }

    /**
     * Starts or gets the instance of the singleton.
     * @return
     */
    public static Server getInstance(){
        if(instance == null){
            instance = new Server();
            return instance;
        }else{
            return instance;
        }
    }

    /**
     * Accepts a new job for the server and passes it on to the worker pool.
     * @param toGive A new job to be run.
     */
    public void giveJob(Job toGive){
        myWorkerPool.addJob(toGive);
    }
}
