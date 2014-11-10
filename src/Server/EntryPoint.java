package Server;

/**
 * Created by Julien Cossette on 11/4/2014.
 */
public class EntryPoint{

    /**
     * The entry point
     * @param args
     */
    public static void main(String[] args){
        ServerController myController = ServerController.getInstance();
        myController.doThis("job socketserver");
    }
}
