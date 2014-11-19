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
    private static ClientController instance;
    ObjectOutputStream out;
    private TypeColisEnum typeColis;
    int defaultPort = 1088;
    private Socket socketClient;

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
     * Création du socket
     */
    public void createSocket(String hostname) throws IOException {
        socketClient = new Socket(hostname, defaultPort);

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
    public void connect(String userName, String password) {
        typeColis = TypeColisEnum.connection;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.setParameter(userName);
        colisToSend.setParameter(password);

        sendColis(colisToSend);
    }

    /**
     * Déconnexion
     */
    public void disconnect(){
        typeColis = TypeColisEnum.disconnection;

        Colis colisToSend = new Colis(typeColis);

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
    public void createRooom(String roomName){
        typeColis = TypeColisEnum.createRoom;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.setParameter(roomName);

        sendColis(colisToSend);
    }

    /**
     *  Demande pour joiner une room
     */
    public void joinRoom(String roomName){
        typeColis = TypeColisEnum.joinRoom;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.setParameter(roomName);

        sendColis(colisToSend);
    }

    /**
     *  Envoi d'un message dans une room donnée
     */
    public void communication(String roomName, String message){
        typeColis = TypeColisEnum.communication;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.setParameter(roomName);
        colisToSend.setParameter(message);

        sendColis(colisToSend);
    }

    /**
     * Demande de la liste des usagers dans la room
     */
    public void listRequest(String roomName){
        typeColis = TypeColisEnum.listRequest;

        Colis colisToSend = new Colis(typeColis);
        colisToSend.setParameter(roomName);

        sendColis(colisToSend);
    }

    /**
     * Demande de fullUpdate (un refresh de la page)
     */
    public void fullUpdate(){
        typeColis = TypeColisEnum.fullUpdate;

        Colis colisToSend = new Colis(typeColis);

        sendColis(colisToSend);
    }
}
