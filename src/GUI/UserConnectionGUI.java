package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by pewtroof on 2014-11-10.
 */
public class UserConnectionGUI extends JFrame
{
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;
    private JButton buttonConnection;
    private JPanel entryPanel;

    public UserConnectionGUI()
    {

        setTitle("Form 1.1");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        buttonConnection.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                new ClientGUI();
            }
        });
    }
}
