package GUI;

import javax.swing.*;
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
    }

    public void updateText(String text, String name)
    {
        textAreaOutputText.append(name+" >  "+text+"\n");
    }

    public void addNameToList(String name)
    {
        model = (DefaultListModel)listName.getModel();
        model.addElement(name);
        listName.setModel(model);
    }

    public void removeNameToList(String name)
    {
        model = (DefaultListModel)listName.getModel();

        for(int i = 0; i<model.getSize(); i++)
        {
            if (model.getElementAt(i).equals(name))
            {
                model.removeElementAt(i);
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
            updateNameList(userNameList.get(tabbedPaneRoom.getSelectedIndex()).get(i));
        }

        for(int i = 0; i<textList.get(tabbedPaneRoom.getSelectedIndex()).size(); i++)
        {
            updateTextArea(textList.get(tabbedPaneRoom.getSelectedIndex()).get(i));
        }
    }

    private void updateAllRoom(String roomName)
    {
        tabbedPaneRoom.addTab(roomName, new JLabel("test: "+roomName));
    }

    private void updateNameList(String name)
    {
        model = (DefaultListModel)listName.getModel();
        model.addElement(name);
        listName.setModel(model);
    }

    private void updateTextArea(String text)
    {
        textAreaOutputText.append(text);
    }
}
