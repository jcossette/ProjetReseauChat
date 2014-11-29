package Client;

import Colis.TypeColisEnum;
import Colis.Colis;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Julien Cossette on 11/21/2014.
 */
public class TestInjector{
    private static int port = 2500;
    private static String IP = "localhost";
    private static ObjectOutputStream myOutputStream;

    public static void main(String[] args){
        openStream();
        sendColis();
    }

    public static void openStream(){
        try{
            Socket mySocket = new Socket(IP, port);
            myOutputStream = new ObjectOutputStream(mySocket.getOutputStream());
        }catch(IOException ie){
            System.out.println("Fail to open stream");
        }
    }

    private static void sendColis(){
        Colis toSend = new Colis(TypeColisEnum.connection);
        try{
            myOutputStream.writeObject(toSend);
        }catch(IOException ie){
            System.out.println("Fail to send Colis");
        }
    }


}
