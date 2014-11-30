package GUI;

import Client.ClientController;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private ClientController clientController;

    private DefaultListModel<String> model;

    private List<Room> roomList;
    private Map<JPanel, Room> roomMap;

    public ClientGUI()
    {
        model = (DefaultListModel)listName.getModel();

        clientController = ClientController.getInstance();

        setTitle("Form 1.1");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

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

                Room room = roomMap.get(tabbedPaneRoom.getSelectedComponent());

                fillTab(room);
            }
        });
        buttonAddRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.getRoomList();
            }
        });
    }

    private void sendCommunication(){
        if(!textFieldInputText.getText().isEmpty())
        {
            clientController.communication(getCurrentRoom().getID(), textFieldInputText.getText());
            textFieldInputText.setText("");
        }
    }

    public Room getCurrentRoom(){
        Room currentRoom = roomList.get(tabbedPaneRoom.getSelectedIndex());
        return currentRoom;
    }

    public void updateText(String roomName, String text)
    {
        Room toUpdate = getRoom(roomName);

        toUpdate.addMessage(text);

        if (isCurrentRoom(toUpdate)){
            textAreaOutputText.append(text + "\n");
        }
    }

    private void updateAddName(String name)
    {
        //model = (DefaultListModel)listName.getModel();
        model.addElement(name);
        listName.setModel(model);
    }

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

    public void addNameToRoom(String roomName, User user)
    {
        Room room = getRoom(roomName);
        if(isCurrentRoom(room)) {
                    updateAddName(user.getUsername());
                }
       room.addUser(user);
    }

    public void removeNameFromRoom(String roomName, User user)
    {
        Room room = getRoom(roomName);
        if(isCurrentRoom(room)) {
            updateRemoveName(user.getUsername());
        }
        room.removeUser(user);
    }

    public void removeNameFromAllRooms(User user) {
        for (Room room : roomList){
            removeNameFromRoom(room.getName(), user);
        }
    }

    public void fullUpdate(List<Room> roomList)
    {
        this.roomList = roomList;
        Room lobby = roomList.get(0);

        for (Room room : roomList) {
            if (room.getName().equals(lobby.getName())){
                roomMap.put(lobbyPanel, room);
            } else {
                createRoom(room);
            }
        }

        fillTab(lobby);
    }

    private void fillTab(Room room){
        for(User user : room.getMyUsers()) {
            updateNameFromList(user.getUsername());
        }

        for(String line : room.getMessageChain()){
            updateTextAreaFromList(line);
        }
    }

    private void createRoom(Room room){
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textAreaOutputText);
        tabbedPaneRoom.addTab(room.getName(), panel);

        roomMap.put(panel, room);
    }


    public void joinRoom(Room room){
        createRoom(room);

        for (User user : room.getMyUsers()){
            updateNameFromList(user.getUsername());
        }
        this.roomList.add(room);
    }

    private void updateNameFromList(String name)
    {
        model.addElement(name);
    }

    private void updateTextAreaFromList(String text)
    {
        textAreaOutputText.append(text);
    }

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

    private boolean isCurrentRoom(Room room){
        if (room.getName().equals(getCurrentRoom().getName())) {
            return true;
        } else {
            return false;
        }
    }
}
