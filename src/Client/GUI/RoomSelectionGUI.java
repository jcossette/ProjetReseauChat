package Client.GUI;

import Client.ClientController;
import Server.ChatManager.Room;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private final List<Room> clientRoomList;

    public RoomSelectionGUI(ArrayList<Room> roomList, ClientGUI clientGui){
        setTitle("Add room");
        setContentPane(addRoomPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        clientController = ClientController.getInstance();
        clientRoomList = clientGui.getRoomList();


        for (Room room : roomList){
            comboBoxJoinRoom.addItem(room);
        }
        buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Room roomToJoin = (Room)comboBoxJoinRoom.getSelectedItem();
                boolean canJoin = true;

                for (Room r : clientRoomList){
                    if (r.getID() == roomToJoin.getID()){
                        JOptionPane.showMessageDialog(null, "Erreur, vous êtes déjà présent dans cette room.", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        canJoin = false;
                        break;
                    }
                }
                if (canJoin) {
                    clientController.joinRoom(roomToJoin.getID());
                    closeWindow();
                }
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
