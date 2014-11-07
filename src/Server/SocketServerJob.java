package Server;

/**
 * Created by Julien Cossette on 11/5/2014.
 */
public class SocketServerJob extends Job{
    private int port;

    /**
     * Default constructor default the socket server at port 1337.
     */
    public SocketServerJob(){
        port = 1337;
    }

    /**
     * Constructor with specified port.
     * @param port Port to be used.
     */
    public SocketServerJob(int port){
        this.port = port;
    }

    public void execute(){

    }
}
