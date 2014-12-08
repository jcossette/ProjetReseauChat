package Client;

import Client.GUI.ServerList.Context;
import Client.GUI.ServerList.Controllers.SetupScreenController;
import Client.GUI.SetupScreen;

import javax.swing.*;

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
        initTheme();
        Context.getInstance();
        new SetupScreenController(new SetupScreen());
    }

    private static void initTheme(){
        try{
            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e){
            try{
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }catch(Exception ex){
                //Error?!? This is madness
            }
        }
    }
}
