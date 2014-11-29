package GUI;

import Client.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by pewtroof on 2014-11-10.
 */
public class ListServerGUI extends JFrame
{
    String[] servers = {"Server1","Server2","Server3"};
    private JButton buttonJoin;
    private JButton buttonDelete;
    private JPanel entryPanel;
    private JComboBox comboBoxServers;
    private JTextField portField;
    ClientController controller;

    public ListServerGUI()
    {

        setTitle("Form 1.1");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        buttonJoin.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               // String ip = comboBoxServers.getSelectedItem().toString();
                String ip = "184.162.229.22";

                controller = ClientController.getInstance();
                try {
                    controller.createSocket(ip, portField.getText());

                    /**
                     * Executes after socket is created
                     * Initialize the serverListener thread and shows the UserConnectionGUI
                     */
                    controller.initListener();

                    new UserConnectionGUI();
                    setVisible(false);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Échec de connection au serveur", "Ereurr",
                            JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });
    }
}
