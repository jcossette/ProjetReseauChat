package Client.GUI;

import Client.ClientController;
import Server.ChatManager.Room;

import javax.swing.*;
import java.awt.*;
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


        for(Room room : roomList){
            comboBoxJoinRoom.addItem(room);
        }
        buttonJoin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Room roomToJoin = (Room) comboBoxJoinRoom.getSelectedItem();
                boolean canJoin = true;

                for(Room r : clientRoomList){
                    if(r.getID().equals(roomToJoin.getID())){
                        JOptionPane.showMessageDialog(null, "Erreur, vous etes deja present dans cette room.", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        canJoin = false;
                        break;
                    }
                }
                if(canJoin){
                    clientController.joinRoom(roomToJoin.getID());
                    closeWindow();
                }
            }
        });
        buttonCreateRoom.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clientController.createRoom(textFieldCreateRoom.getText());
                closeWindow();
            }
        });
        buttonAnnuler.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                closeWindow();
            }
        });
    }

    private void closeWindow(){
        this.dispose();
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     * @noinspection ALL
     */
    private void $$$setupUI$$$(){
        addRoomPanel = new JPanel();
        addRoomPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        addRoomPanel.setPreferredSize(new Dimension(300, 300));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        addRoomPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBoxJoinRoom = new JComboBox();
        panel1.add(comboBoxJoinRoom, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonJoin = new JButton();
        buttonJoin.setText("Join Room");
        panel1.add(buttonJoin, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        addRoomPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textFieldCreateRoom = new JTextField();
        panel2.add(textFieldCreateRoom, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonCreateRoom = new JButton();
        buttonCreateRoom.setText("Create Room");
        panel2.add(buttonCreateRoom, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAnnuler = new JButton();
        buttonAnnuler.setText("Annuler");
        panel2.add(buttonAnnuler, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        addRoomPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$(){
        return addRoomPanel;
    }
}
