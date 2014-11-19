package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by pewtroof on 2014-11-05.
 */
public class ClientGUI extends JFrame
{
    private JPanel entryPanel;
    private JTabbedPane tabbedPane1;
    private JTextField textFieldInputText;
    private JButton buttonSend;
    private JList listName;
    private JTextArea textAreaOutputText;

    public ClientGUI()
    {
        setTitle("Form 1.1");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        buttonSend.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(!textFieldInputText.getText().isEmpty())
                {
                    textAreaOutputText.append(">  "+textFieldInputText.getText()+"\n");
                    textFieldInputText.setText("");
                }
            }
        });

        textFieldInputText.addKeyListener(
                new KeyAdapter()
                {
                    public void keyPressed(KeyEvent e)
                    {
                        if(e.getKeyChar() == KeyEvent.VK_ENTER)
                        {
                            if(!textFieldInputText.getText().isEmpty())
                            {
                                textAreaOutputText.append("You >  "+textFieldInputText.getText()+"\n");
                                textFieldInputText.setText("");
                            }
                        }
                    }
                }
        );
    }

    public void updateText(String text, String name)
    {
        textAreaOutputText.append(name+" >  "+text+"\n");
    }
}
