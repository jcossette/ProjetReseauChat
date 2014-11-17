package Server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * This is the entry socket for the server. It allocates working sockets to new clients.
 * Created by Julien Cossette on 11/5/2014.
 */
public class SocketServerJob extends Job{
    private int port;
    private ServerSocket mySocket;

    /**
     * Default constructor. Defaults the socket server at port 1337.
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

    private void openSocket(){
        try{
            mySocket = new ServerSocket(port);
        }catch(IOException e){
            myController.writeMessage("Erreur de creation du socket server: " + e.getMessage());
        }
    }

    public void execute(){

    }
}
