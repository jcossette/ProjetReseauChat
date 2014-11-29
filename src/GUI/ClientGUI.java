package GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

    private DefaultListModel<String> model;
    private List<List<String>> textAreaList;
    private List<List<String>> userNameList;
    private List<String> roomList;

    public ClientGUI()
    {
        listName = new JList(model);

        setTitle("Form 1.1");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        buttonSend.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                if(!textFieldInputText.getText().isEmpty())
                {
                    textAreaOutputText.append(">  "+textFieldInputText.getText()+"\n");
                    textFieldInputText.setText("");
                }
            }
        });

        textFieldInputText.addKeyListener(
                new KeyAdapter()
                {
                    public void keyPressed(KeyEvent e)
                    {
                        if(e.getKeyChar() == KeyEvent.VK_ENTER)
                        {
                            if(!textFieldInputText.getText().isEmpty())
                            {
                                textAreaOutputText.append("You >  "+textFieldInputText.getText()+"\n");
                                textFieldInputText.setText("");
                            }
                        }
                    }
                }
        );

        tabbedPaneRoom.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                textAreaOutputText.setText("");
                for(int i = 0; i<textAreaList.get(tabbedPaneRoom.getSelectedIndex()).size(); i++)
                {
                    updateTextAreaFromList(textAreaList.get(tabbedPaneRoom.getSelectedIndex()).get(i));
                }

                listName.clearSelection();
                for(int i = 0; i<userNameList.get(tabbedPaneRoom.getSelectedIndex()).size(); i++)
                {
                    updateNameFromList(userNameList.get(tabbedPaneRoom.getSelectedIndex()).get(i));
                }
            }
        });
    }

    public String getCurrentRoom(){
        String currentRoom = roomList.get(tabbedPaneRoom.getSelectedIndex());
        return currentRoom;
    }

    public void updateText(String roomName, String text, String name)
    {
        for(int i = 0; i<roomList.size(); i++)
        {
            if (roomList.get(i).equals(roomName))
            {
                textAreaList.get(i).add(name + " >  " + text + "\n");
                textAreaOutputText.append(name + " >  " + text + "\n");
            }
        }
    }

    private void updateAddName(String name)
    {
        model = (DefaultListModel)listName.getModel();
        model.addElement(name);
        listName.setModel(model);
    }

    public  void updateRemoveName(String userName)
    {
        model = (DefaultListModel)listName.getModel();

        for(int i = 0; i<model.getSize(); i++)
        {
            if (model.getElementAt(i).equals(userName))
            {
                model.removeElementAt(i);
            }
        }

        listName.setModel(model);
    }

    public void addNameToRoom(String room, String name)
    {
        for(int i = 0; i<roomList.size(); i++)
        {
            if (roomList.get(i).equals(room))
            {
                if(tabbedPaneRoom.getSelectedIndex() == i)
                    updateAddName(name);
                else
                    userNameList.get(i).add(name);
            }
        }
    }

    public void removeNameFromRoom(String room, String name)
    {
        for(int i = 0; i<roomList.size(); i++)
        {
            if (roomList.get(i).equals(room))
            {
                if (tabbedPaneRoom.getSelectedIndex() == i)
                    updateRemoveName(name);
                else
                    userNameList.get(i).remove(name);
            }
        }
    }

    public void removeNameFromAllRooms(String name){
        for(int i = 0; i<roomList.size(); i++)
        {
            for (int j = 0; j < userNameList.get(i).size(); j ++){
                if (userNameList.get(i).get(j).equals(name))
                {
                    if (tabbedPaneRoom.getSelectedIndex() == i)
                        updateRemoveName(name);
                    else
                        userNameList.get(i).remove(name);
                }
            }
        }
    }

    public void fullUpdate(List<String> roomNameList, List<List<String>> userNameList, List<List<String>> textList)
    {
        this.roomList = roomNameList;
        this.userNameList = userNameList;
        this.textAreaList = textList;

        for(int i = 0; i<roomList.size(); i++)
        {
            updateAllRoom(roomList.get(i));
        }

        for(int i = 0; i<userNameList.get(tabbedPaneRoom.getSelectedIndex()).size(); i++)
        {
            updateNameFromList(userNameList.get(tabbedPaneRoom.getSelectedIndex()).get(i));
        }

        for(int i = 0; i<textList.get(tabbedPaneRoom.getSelectedIndex()).size(); i++)
        {
            updateTextAreaFromList(textList.get(tabbedPaneRoom.getSelectedIndex()).get(i));
        }
    }

    private void updateAllRoom(String roomName)
    {
        tabbedPaneRoom.addTab(roomName, new JLabel("test: "+roomName));
    }

    private void updateNameFromList(String name)
    {
        model = (DefaultListModel)listName.getModel();
        model.addElement(name);
        listName.setModel(model);
    }

    private void updateTextAreaFromList(String text)
    {
        textAreaOutputText.append(text);
    }
}
