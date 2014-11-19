package Server.ChatManager;

import Server.Job;
import Colis.Colis;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This job runs a facade managing the different components of the chat server.
 * Created by Julien Cossette on 11/10/2014.
 */
public class ChatManagerJob extends Job {
    private ArrayList<Session> mySessions;
    private UserManager myUserManager;
    private ColisHandler myColisHandler;
    private Selector myChannelSelector;
    private ObjectInputStream myInputStream;
    private ObjectOutputStream myOutputStream;
    private int myPort;

    public ChatManagerJob(int port){
        this.myPort = port;
        this.myColisHandler = ColisHandler.getInstance();
        this.mySessions = new ArrayList();
        this.myUserManager = new UserManager();
        try{
            myChannelSelector = Selector.open();
        }catch(IOException e){
            myController.writeMessage("Erreur de creation du channel selector: " + e.getMessage());
        }
        initSocketServerChannel();
    }

    public void execute(){
        while(run == true){
            monitorChannels();
        }
    }

    private void initSocketServerChannel(){
        try{
            ServerSocketChannel myServerSocketChannel = ServerSocketChannel.open();
            myServerSocketChannel.socket().bind(new InetSocketAddress(myPort));
            myServerSocketChannel.register(myChannelSelector, SelectionKey.OP_ACCEPT);
        }catch(IOException e){
            myController.writeMessage("Erreur de creation du socket server: " + e.getMessage());
        }
    }

    /**
     * This method verifies if any of the channel is ready to read or write and executes these actions.
     */
    private void monitorChannels(){
        try{
            if(myChannelSelector.select() > 0){
                Iterator selectedKeys = this.myChannelSelector.selectedKeys().iterator();
                while(selectedKeys.hasNext()){
                    SelectionKey key = (SelectionKey)selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if(key.isReadable()){
                        readChannel(key);
                    }

                    if(key.isWritable()){
                        writeChannel(key);
                    }

                    if(key.isAcceptable()){
                        acceptChannel(key);
                    }

                }
            }
        }catch(IOException e){
            myController.writeMessage("Erreur de vérification de l'état du selector: " + e.getMessage());
        }
    }

    private void readChannel(SelectionKey toRead){
        Session sender = (Session)toRead.attachment();
        Colis toHandle;
        try{
            SocketChannel mySocketChannel = (SocketChannel)toRead.channel();
            myInputStream = new ObjectInputStream(mySocketChannel.socket().getInputStream());
            toHandle = (Colis)myInputStream.readObject();
            myColisHandler.handleColis(toHandle, sender);
        }catch(IOException ie){
            myController.writeMessage("Erreur d'ecriture sur le stream: " + ie.getMessage());
        } catch (ClassNotFoundException cnfe) {
            myController.writeMessage("Classe introuvable " + cnfe.getMessage());
        }
    }

    private void writeChannel(SelectionKey toWrite){

    }

    private void acceptChannel(SelectionKey toAccept){
        try{
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)toAccept.channel();
            SocketChannel newSocketChannel = serverSocketChannel.accept();
            newSocketChannel.configureBlocking(false);
            SelectionKey mySelKey = newSocketChannel.register(this.myChannelSelector, SelectionKey.OP_READ & SelectionKey.OP_WRITE);
            Session newSession = new Session(mySelKey, this);
            mySelKey.attach(newSession);
        }catch(IOException ie){
            myController.writeMessage("Erreur d'acceptation du socket: " + ie.getMessage());
        }
    }

    public void send(Colis toSend, SocketChannel toChannel){
        try{
            ObjectOutputStream myOutputStream = new ObjectOutputStream(toChannel.socket().getOutputStream());
        }catch(IOException ie){
            myController.writeMessage("Erreur d'envoie du Colis " + ie.getMessage());
        }
    }
}
