package Client.GUI;

import Client.ClientController;
import Server.ChatManager.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by pewtroof on 2014-11-29.
 */
public class RoomSelectionGUI extends JFrame{
    private JComboBox comboBoxJoinRoom;
    private JButton buttonJoin;
    private JTextField textFieldCreateRoom;
    private JButton buttonCreateRoom;
    private JButton buttonAnnuler;
    private JPanel addRoomPanel;

    private ClientController clientController;


    public RoomSelectionGUI(ArrayList<Room> roomList){
        setTitle("Form 1.1");
        setContentPane(addRoomPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        clientController = ClientController.getInstance();

        for (Room room : roomList){
            comboBoxJoinRoom.addItem(room);
        }
        buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Room roomToJoin = (Room)comboBoxJoinRoom.getSelectedItem();
                clientController.joinRoom(roomToJoin.getID());
                closeWindow();
            }
        });
        buttonCreateRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.createRoom(textFieldCreateRoom.getText());
                closeWindow();
            }
        });
        buttonAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
    }

    private void closeWindow(){
        this.dispose();
    }
}
