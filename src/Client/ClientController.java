package Client;

import Colis.TypeColisEnum;
import Colis.Colis;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Controleur du côté client
 * Created by fillioca on 2014-11-19.
 */
public class ClientController {
    private String myNickname;
    private static ClientController instance;
    private ObjectOutputStream out;
    private TypeColisEnum typeColis;
    private int defaultPort = 1088;
    private int port;
    private Socket socketClient;
    private ServerListener serverListener;

    /**
     * Private constructor
     */
    private ClientController() {
    }

    /**
     * Starts or gets the instance of the singleton.
     * @return Returns the instance of the controller.
     */
    public static ClientController getInstance(){
        if(instance == null){
            instance = new ClientController();
            return instance;
        } else{
            return instance;
        }
    }

    /**
     * Initie le thread Listener
     */
    public void initListener(){
        serverListener = new ServerListener();
    }

    /**
     * Création du socket
     */
    public void createSocket(String hostname, String enteredPort) throws IOException {
        port = Integer.parseInt(enteredPort);
        if (port < 1024 || port > 65535 ){
            port = defaultPort;
        }

        socketClient = new Socket(hostname, port);

        out = new ObjectOutputStream(socketClient.getOutputStream());
    }

    public Socket getSocket() {
        return socketClient;
    }

    /**
     * Méthode pour envoyer un colis vers le serveur
     */
    public void sendColis(Colis colis)  {
        try {
            out.writeObject(colis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Demande de connection
     */
    public void connect(String userName) {
        typeColis = TypeColisEnum.connection;
        this.myNickname = userName;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.addParameter(userName);

        sendColis(colisToSend);
    }

    /**
     * Demande une liste des rooms courantes
     */
    public void getRoomList(){
        typeColis = TypeColisEnum.getRoomList;

        Colis colisToSend = new Colis(typeColis);

        sendColis(colisToSend);
    }

    /**
     *  Création d'une nouvelle room
     */
    public void createRoom(String roomName){
        typeColis = TypeColisEnum.createRoom;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.addParameter(roomName);

        sendColis(colisToSend);
    }

    /**
     *  Demande pour joiner une room
     */
    public void joinRoom(Integer roomId){
        typeColis = TypeColisEnum.joinRoomRequest;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.addParameter(roomId);

        sendColis(colisToSend);
    }

    /**
     *  Envoi un colis pour signaler qu<on quitte une room en particulier
     */
    public void leaveRoom(Integer roomId){
        typeColis = TypeColisEnum.leaveRoom;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.addParameter(roomId);

        sendColis(colisToSend);
    }

    /**
     *  Envoi d'un message dans une room donnée
     */
    public void communication(Integer roomId, String message){
        typeColis = TypeColisEnum.communication;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.addParameter(roomId);
        colisToSend.addParameter(message);

        sendColis(colisToSend);
    }

    /**
     * Demande de fullUpdate (un refresh de la page)
     */
    public void getFullUpdate(){
        typeColis = TypeColisEnum.getFullUpdate;

        Colis colisToSend = new Colis(typeColis);

        sendColis(colisToSend);
    }

    public String getMyNickname(){
        return myNickname;
    }
}
