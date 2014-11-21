package Server;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by pewtroof on 2014-11-19.
 */
public class ServerConsoleGUI extends JFrame
{
    private ServerController myController;
    private JTextField textFieldConsole;
    private JPanel entryPanel;
    private JTextArea textAreaConsole;

    public JPanel getPanel()
    {
        return entryPanel;
    }

    public ServerConsoleGUI()
    {
        setTitle("Form 1");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        textFieldConsole.addKeyListener(
            new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    if(e.getKeyChar() == KeyEvent.VK_ENTER)
                    {
                        if(!textFieldConsole.getText().isEmpty())
                        {
                            String command = textFieldConsole.getText();
                            myController.doThis(command);
                            textFieldConsole.setText("");
                        }
                    }
                }
            }
        );
    }

    public void assignController(ServerController toAssign){
        this.myController = toAssign;
    }

    public void writeToConsole(String toWrite){
        textAreaConsole.append(">  " + toWrite + "\n");
    }


}
