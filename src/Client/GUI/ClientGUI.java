package Client.GUI;

import Client.ClientController;
import Server.ChatManager.Room;
import Server.ChatManager.User;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

/**
 * Created by pewtroof on 2014-11-05.
 */
public class ClientGUI extends JFrame {
    private JPanel entryPanel;
    private JTabbedPane tabbedPaneRoom;
    private JTextField textFieldInputText;
    private JButton buttonSend;
    private JList listName;
    private JTextArea textAreaOutputText;
    private JButton buttonAddRoom;
    private JPanel lobbyPanel;
    private JButton buttonLeaveRoom;

    private ClientController clientController;
    private DefaultListModel<String> model;
    private List<Room> roomList;
    private Map<Room, JTextArea> roomMap;

    public ClientGUI() {
        model = (DefaultListModel) listName.getModel();
        clientController = ClientController.getInstance();

        setTitle("Super chat of " + clientController.getMyNickname());
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        roomList = new ArrayList();
        roomMap = new HashMap();

        buttonLeaveRoom.setEnabled(false); /**Disable le boutton de leaveRoom car il est impossible de quitter le lobby */

        /** Envoi le texte lorsqu'on appuie sur le bouton Send */
        buttonSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                sendCommunication();
            }
        });

        /** Envoi le texte lorsqu'on appuie sur Enter */
        textFieldInputText.addKeyListener(
                new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                            sendCommunication();
                        }
                    }
                }
        );

        /** Met a jour le texte et la liste d'utilisateur lorsqu'on change de tab/Room */
        tabbedPaneRoom.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                model.clear();
                int tabIndex = tabbedPaneRoom.getSelectedIndex();
                Room room = roomList.get(tabIndex);
                fillTab(room);

                /** Disable le bouton de leaveRoom si la Room courante est le lobby */
                if (tabIndex == 0) {
                    buttonLeaveRoom.setEnabled(false);
                } else {
                    buttonLeaveRoom.setEnabled(true);
                }
            }
        });

        /** Envoi un colis getRoomList au serveur lorsqu'on appuie sur le boutton Add Room */
        buttonAddRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.getRoomList();
            }
        });

        /** Envoi un colis leaveRoom au serveur lorsqu'on appuie sur le boutton Leave Room et delete la tab courante*/
        buttonLeaveRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.leaveRoom(getCurrentRoom().getID());
                roomList.remove(tabbedPaneRoom.getSelectedIndex());
                tabbedPaneRoom.remove(tabbedPaneRoom.getSelectedIndex());
            }
        });

        /**Sets the caret enabling the auto-scrolling of the chat window*/
        DefaultCaret caret = (DefaultCaret) textAreaOutputText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        /**Sets the font on the chat panel*/
        Font font = new Font("Calibri", Font.BOLD, 14);
        textAreaOutputText.setFont(font);
    }

    /**
     * Envoi un paquet communication lors"on appuie sur "Enter" ou sur le bouton "Send"
     */
    private void sendCommunication() {
        if (!textFieldInputText.getText().isEmpty()) {
            clientController.communication(getCurrentRoom().getID(), textFieldInputText.getText());
            textFieldInputText.setText("");
        }
    }

    /**
     * Retourne la room courante
     */
    public Room getCurrentRoom() {
        Room currentRoom = roomList.get(tabbedPaneRoom.getSelectedIndex());
        return currentRoom;
    }

    /**
     * Update le JTextArea output lorsqu'on recoit un colis updateText du serveur
     */
    public void updateText(Integer roomID, String text) {
        Room toUpdate = getRoom(roomID);

        toUpdate.addMessage(text);

        JTextArea toUpdateField = roomMap.get(toUpdate);
        toUpdateField.append(text + "\n");
        toUpdateField.repaint();
    }

    /**
     * Ajoute un nom a la liste d'utilisateur
     */
    private void updateAddName(String name) {
        model.addElement(name);
        listName.setModel(model);
    }

    /**
     * Enleve un nom de la liste d'utilisateur
     */
    public void updateRemoveName(String userName) {
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).equals(userName)) {
                model.removeElementAt(i);
            }
        }

        listName.setModel(model);
    }

    /**
     * Ajoute un utilisateur a une room particuliere
     */
    public void addNameToRoom(String roomName, User user) {
        Room room = getRoom(roomName);
        if (isCurrentRoom(room)) {
            updateAddName(user.getUsername());
        }
        room.addUser(user);
    }

    /**
     * Enleve un utilisateur d'une room particuliere
     */
    public void removeNameFromRoom(Room room, User userToRemove) {
        if (isCurrentRoom(room)) {
            updateRemoveName(userToRemove.getUsername());
        }

        /** Necessaire a cause que les references d'user du server et du client ne sont pas les memes  **/
        for (Iterator<User> userIterator = room.getMyUsers().iterator(); userIterator.hasNext(); ) {
            User user = userIterator.next();
            if (user.getUsername().equals(userToRemove.getUsername())) {
                userIterator.remove();
            }
        }
    }

    /**
     * Enleve un utilisateur d'une Room 'a partir de l'ID de la Room
     */
    public void removeNameFromRoom(int roomID, User userToRemove) {
        Room roomToRemove = getRoom(roomID);
        removeNameFromRoom(roomToRemove, userToRemove);
    }

    /**
     * Enleve un utilisateur de toutes les rooms
     */
    public void removeNameFromAllRooms(User user) {
        for (Room room : roomList) {
            removeNameFromRoom(room, user);
        }
    }

    /**
     * Recoit les informations du lobby du serveur
     */
    public void fullUpdate(List<Room> roomList) {
        Room lobby = roomList.get(0);
        this.roomList.add(lobby);

        roomMap.put(lobby, textAreaOutputText);
        fillTab(lobby);
    }

    /**
     * Va chercher la room qu'on vient de selectionner et affiche le texte et la liste d'utilisateurs
     */
    private void fillTab(Room room) {
        for (User user : room.getMyUsers()) {
            updateNameFromList(user.getUsername());
        }

        textAreaOutputText.setText("");
        for (String line : room.getMessageChain()) {
            updateTextAreaFromList(line);
        }
    }

    /**
     * Cree une tab pour une nouvelle room
     */
    public void createRoomTab(Room room) {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        JScrollPane myNewScrollPane = new JScrollPane(textArea);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        Font font = new Font("Calibri", Font.BOLD, 14);
        textArea.setFont(font);
        panel.add(myNewScrollPane);
        tabbedPaneRoom.addTab(room.getName(), panel);

        roomList.add(room);
        roomMap.put(room, textArea);
    }

    private void updateNameFromList(String name) {
        model.addElement(name);
    }

    private void updateTextAreaFromList(String text) {
        textAreaOutputText.append(text + "\n");
    }

    /**
     * Va chercher l'objet Room a partir de son nom
     */
    private Room getRoom(String roomName) {
        Room room = new Room();

        for (Room r : roomList) {
            if (r.getName().equals(roomName)) {
                room = r;
                break;
            }
        }

        return room;
    }

    /**
     * Va chercher l'objet Room a partir de son ID
     */
    private Room getRoom(Integer ID) {
        Room room = null;
        for (Room r : roomList) {
            if (r.getID().equals(ID)) {
                room = r;
                break;
            }
        }
        return room;
    }

    /**
     * Verifie si la Room passe en parametre est la Room courante
     */
    private boolean isCurrentRoom(Room room) {
        if (room.getName().equals(getCurrentRoom().getName())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retourne la liste des rooms contenue dans le Client
     */
    public List<Room> getRoomList() {
        return roomList;
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
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        entryPanel = new JPanel();
        entryPanel.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));
        entryPanel.setPreferredSize(new Dimension(500, 500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        entryPanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabbedPaneRoom = new JTabbedPane();
        tabbedPaneRoom.setName("Lobby");
        tabbedPaneRoom.setOpaque(true);
        panel1.add(tabbedPaneRoom, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        lobbyPanel = new JPanel();
        lobbyPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        lobbyPanel.setName("Lobby");
        tabbedPaneRoom.addTab("Lobby", lobbyPanel);
        final JScrollPane scrollPane1 = new JScrollPane();
        lobbyPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAreaOutputText = new JTextArea();
        textAreaOutputText.setEditable(false);
        scrollPane1.setViewportView(textAreaOutputText);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setForeground(new Color(-12171706));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5), null));
        textFieldInputText = new JTextField();
        panel2.add(textFieldInputText, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 30), new Dimension(150, 30), new Dimension(-1, 30), 0, false));
        buttonSend = new JButton();
        buttonSend.setText("Send");
        panel2.add(buttonSend, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        entryPanel.add(panel3, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(95, -1), new Dimension(95, -1), new Dimension(95, -1), 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-9408400)), null));
        listName = new JList();
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        listName.setModel(defaultListModel1);
        panel3.add(listName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        entryPanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, 1, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null));
        buttonLeaveRoom = new JButton();
        buttonLeaveRoom.setText("Leave Room");
        panel4.add(buttonLeaveRoom, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAddRoom = new JButton();
        buttonAddRoom.setText("Add Room");
        panel4.add(buttonAddRoom, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return entryPanel;
    }
}
