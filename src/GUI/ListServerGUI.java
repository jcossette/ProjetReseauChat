package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                //Execute when button is pressed
                new UserConnectionGUI();
                setVisible(false);
            }
        });
    }
}
