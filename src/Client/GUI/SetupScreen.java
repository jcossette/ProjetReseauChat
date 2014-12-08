package Client.GUI;

import Client.GUI.ServerList.Controllers.SetupScreenController;
import Client.GUI.ServerList.ServerInfo;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Julien Cossette on 12/5/2014.
 */
public class SetupScreen extends JFrame{
    private SetupScreenController myController;
    private Map<String, ServerInfo> myMapServerInfo;

    private JPanel rootPanel;
    private JPanel containerPanel;
    private JList serverList;
    private JPanel buttonPanel;
    private JTextField serverNameField;
    private JTextField serverPortField;
    private JTextField serverIPField;
    private JButton addServerButton;
    private JTextField nicknameField;
    private JButton joinButton;
    private JLabel nicknameLabel;
    private JButton deleteServerButton;
    private JFormattedTextField IPTextField;

    public SetupScreen(){
        super("Select Server");
        myMapServerInfo = new LinkedHashMap<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setVisible(true);
    }

    public void assignController(SetupScreenController myNewController){
        this.myController = myNewController;
        init();
    }

    /**
     * Manual initiation of components
     */
    private void init(){
        addListeners();
        updateServerList();
    }

    /**
     * This method adds the listener to the different items of the form
     */
    private void addListeners(){
        addServerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                handleAddServerButton();
            }
        });
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleJoinButton();
            }
        });
        serverList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                selectServer();
            }
        });
        deleteServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteServer();
            }
        });
    }

    private void deleteServer(){
        String newSelection = (String)serverList.getSelectedValue();
        ServerInfo selectedServerInfo = myMapServerInfo.get(newSelection);
        myMapServerInfo.remove(newSelection);
        myController.removeServer(selectedServerInfo);
        updateServerList();
    }

    private void selectServer(){
        String newSelection = (String)serverList.getSelectedValue();
        ServerInfo selectedServerInfo = myMapServerInfo.get(newSelection);
        serverNameField.setText(selectedServerInfo.getServerName());
        serverIPField.setText(selectedServerInfo.getMyAddress().toString());
        serverPortField.setText(String.valueOf(selectedServerInfo.getMyPort()));
    }

    public void updateServerList() {
        ArrayList<ServerInfo> myServers = myController.getServerList();

        DefaultListModel myModel = (DefaultListModel) serverList.getModel();
        myModel.clear();

        for (ServerInfo si : myServers) {
            String serverInfoString = si.toString();
            myMapServerInfo.put(serverInfoString, si);
            myModel.addElement(serverInfoString);
        }
    }

    private void handleAddServerButton(){
        String newServerName = serverNameField.getText();
        String newServerIP = serverIPField.getText();
        String newServerPort = serverPortField.getText();
        myController.addServer(newServerName, newServerIP, newServerPort);
    }

    /**
     * Handles the Join Button
     */
    private void handleJoinButton(){
        String myNickName = nicknameField.getText();

        if(getServerInfoFromList() == null){
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un serveur");
            return;
        }

        if(myNickName.length() > 2){
            myController.joinServer(getServerInfoFromList(), myNickName);
        }else{
            JOptionPane.showMessageDialog(null, "Le nickname doit avoir au moin 3 lettres.");
        }
    }

    /**
     * Returns the ServerInfo object linked to the selected server.
     * @return
     */
    private ServerInfo getServerInfoFromList(){
        String selected = (String)serverList.getSelectedValue();
        return myMapServerInfo.get(selected);
    }

    private void createUIComponents(){
        serverList = new JList<String>(new DefaultListModel<String>());
    }
}
