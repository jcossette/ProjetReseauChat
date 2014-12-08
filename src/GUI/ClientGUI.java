package GUI;

import Client.ClientController;
import Client.GUIController;
import Server.ChatManager.Room;
import Server.ChatManager.User;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
public class ClientGUI extends JFrame
{
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

    public ClientGUI()
    {
        model = (DefaultListModel)listName.getModel();

        clientController = ClientController.getInstance();

        setTitle("Form 1.1");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        roomList = new ArrayList();
        roomMap = new HashMap();

        buttonSend.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                sendCommunication();
            }
        });

        textFieldInputText.addKeyListener(
                new KeyAdapter()
                {
                    public void keyPressed(KeyEvent e)
                    {
                        if(e.getKeyChar() == KeyEvent.VK_ENTER)
                        {
                            sendCommunication();
                        }
                    }
                }
        );

        tabbedPaneRoom.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                model.clear();

                Room room = roomList.get(tabbedPaneRoom.getSelectedIndex());
                fillTab(room);
            }
        });
        buttonAddRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.getRoomList();
            }
        });
        buttonLeaveRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.leaveRoom(getCurrentRoom().getID());
                roomList.remove(tabbedPaneRoom.getSelectedIndex());
                tabbedPaneRoom.remove(tabbedPaneRoom.getSelectedIndex());
            }
        });
    }

    //Envoi un paquet communication lors"on appuie sur "Enter" ou sur le bouton "Send"
    private void sendCommunication(){
        if(!textFieldInputText.getText().isEmpty())
        {
            clientController.communication(getCurrentRoom().getID(), textFieldInputText.getText());
            textFieldInputText.setText("");
        }
    }

    //Retourne la room courante
    public Room getCurrentRoom(){
        Room currentRoom = roomList.get(tabbedPaneRoom.getSelectedIndex());
        return currentRoom;
    }

    //Update le JTextArea output lorsqu'on recoit un colis updateText du serveur
    public void updateText(Integer roomID, String text)
    {
        Room toUpdate = getRoom(roomID);

        toUpdate.addMessage(text);

        JTextArea toUpdateField = roomMap.get(toUpdate);
        toUpdateField.append(text + "\n");
        toUpdateField.repaint();
    }

    //Ajoute un nom a la liste d'utilisateur
    private void updateAddName(String name)
    {
        model.addElement(name);
        listName.setModel(model);
    }

    //Enleve un nom de la liste d'utilisateur
    public  void updateRemoveName(String userName)
    {
        for(int i = 0; i<model.getSize(); i++)
        {
            if (model.getElementAt(i).equals(userName))
            {
                model.removeElementAt(i);
            }
        }

        listName.setModel(model);
    }

    //Ajoute un utilisateur a une room particuliere
    public void addNameToRoom(String roomName, User user)
    {
        Room room = getRoom(roomName);
        if(isCurrentRoom(room)) {
            updateAddName(user.getUsername());
        }
        room.addUser(user);
    }

    //Enleve un utilisateur d'une room particuliere
    public void removeNameFromRoom(Room room, User userToRemove)
    {
        if(isCurrentRoom(room)) {
            updateRemoveName(userToRemove.getUsername());
        }

        //Necessaire a cause que les references d'user du server et du client ne sont pas les memes
        for (Iterator<User> userIterator = room.getMyUsers().iterator(); userIterator.hasNext(); ) {
            User user = userIterator.next();
            if (user.getUsername().equals(userToRemove.getUsername())) {
                userIterator.remove();
            }
        }
    }

    public void removeNameFromRoom(int roomID, User userToRemove){
        Room roomToRemove = getRoom(roomID);
        removeNameFromRoom(roomToRemove, userToRemove);
    }

    //Enleve un utilisateur de toutes les rooms
    public void removeNameFromAllRooms(User user) {
        for (Room room : roomList){
            removeNameFromRoom(room, user);
        }
    }

    //Recoi les informations du lobby du serveur
    public void fullUpdate(List<Room> roomList)
    {
        Room lobby = roomList.get(0);
        this.roomList.add(lobby);

        roomMap.put(lobby, textAreaOutputText);
        fillTab(lobby);
    }

    //Va chercher la room qu'on vient de selectionner et affiche le texte et la liste d'utilisateurs
    private void fillTab(Room room){
        for(User user : room.getMyUsers()) {
            updateNameFromList(user.getUsername());
        }

        textAreaOutputText.setText("");
        for(String line : room.getMessageChain()){
            updateTextAreaFromList(line);
        }
    }

    //Cree une tab pour une nouvelle room
    public void createRoomTab(Room room){
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        panel.add(textArea);
        tabbedPaneRoom.addTab(room.getName(), panel);

        roomList.add(room);
        roomMap.put(room, textArea);
    }

    private void updateNameFromList(String name)
    {
        model.addElement(name);
    }

    private void updateTextAreaFromList(String text)
    {
        textAreaOutputText.append(text + "\n");
    }

    //Va chercher l'objet Room a partir de son nom
    private Room getRoom(String roomName){
        Room room = new Room();

        for (Room r : roomList){
            if (r.getName().equals(roomName)){
                room = r;
                break;
            }
        }

        return room;
    }

    //Va chercher l'objet Room a partir de son ID
    private Room getRoom(Integer ID){
        Room room = null;
        for (Room r : roomList){
            if (r.getID().equals(ID)){
                room = r;
                break;
            }
        }
        return room;
    }

    //Verifie si la Room passe en parametre est la Room courante
    private boolean isCurrentRoom(Room room){
        if (room.getName().equals(getCurrentRoom().getName())) {
            return true;
        } else {
            return false;
        }
    }
}
