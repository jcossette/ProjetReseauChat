package Client2.Domain;

import Client2.Domain.Controllers.SetupScreenController;
import Client2.GUI.SetupScreen;

/**
 * The Client 2.0 entry point.
 * Created by Julien Cossette on 12/5/2014.
 */
public class EntryPoint{
    /**
     * The entry point.
     * @param args
     */
    public static void main(String[] args)
    {
        Context.getInstance();
        new SetupScreenController(new SetupScreen());
    }
}
