package GUI;

import Client.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by pewtroof on 2014-11-10.
 */
public class UserConnectionGUI extends JFrame
{
    private JTextField textFieldUsername;
    private JButton buttonConnection;
    private JPanel entryPanel;
    private ClientController controller;
    private JPasswordField passwordFieldUser;

    public UserConnectionGUI()
    {
        controller=ClientController.getInstance();

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
                //controller.connect(textFieldUsername.getText(), Arrays.toString(passwordFieldUser.getPassword()));

                new ClientGUI();
                setVisible(false);
            }
        });
    }
}
