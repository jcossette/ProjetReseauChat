package Server;

/**
 * This is the server singleton, the server is a facade singleton representing the actual server.
 * Created by Julien Cossette on 11/5/2014.
 */
public class Server{
    private Server instance;
    private WorkerPool myWorkerPool;

    /**
     * Private constructor
     */
    private Server(){}

    /**
     * Starts or gets the instance of the singleton.
     * @return
     */
    public Server getInstance(){
        if(instance == null){
            instance = new Server();
            return instance;
        }else{
            return instance;
        }
    }
}
