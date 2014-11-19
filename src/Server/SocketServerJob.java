package Server;

import Server.ChatManager.ChatManagerJob;
import Server.ChatManager.Session;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * This is the entry socket for the server. It allocates working sockets to new clients.
 * Created by Julien Cossette on 11/5/2014.
 */
public class SocketServerJob extends Job{
    private int port;
    private ServerSocketChannel mySocketChannel;
    private ChatManagerJob myChatManagerJob;
    private SocketChannel newChannel;

    /**
     * Default constructor. Defaults the socket server at port 1337.
     */
    private SocketServerJob(){
        port = 1337;
    }

    /**
     * Constructor with specified port.
     * @param port Port to be used.
     */
    public SocketServerJob(int port, ChatManagerJob chatManager){
        this.myChatManagerJob = chatManager;
        this.port = port;
    }

    private void openSocket(){
        try{
            mySocketChannel = ServerSocketChannel.open();
            mySocketChannel.socket().bind(new InetSocketAddress(port));
        }catch(IOException e){
            myController.writeMessage("Erreur de creation du socket server: " + e.getMessage());
        }
        listen();
    }

    private void listen(){
        try{
            newChannel = mySocketChannel.accept();
            newChannel.configureBlocking(false);
            myChatManagerJob.addToRegisterQueue(newChannel);
        }catch(IOException e){
            myController.writeMessage("Erreur de creation de socket client: " + e.getMessage());
        }
    }

    public void execute(){
        while(run == true){
            listen();
        }
    }
}
