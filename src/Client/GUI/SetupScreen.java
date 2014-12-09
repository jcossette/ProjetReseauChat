package Client.GUI;

import Client.GUI.ServerList.Controllers.SetupScreenController;
import Client.GUI.ServerList.ServerInfo;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Julien Cossette on 12/5/2014.
 */
public class SetupScreen extends JFrame {
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

    public SetupScreen() {
        super("Select Server");
        myMapServerInfo = new LinkedHashMap<>();
        $$$setupUI$$$();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setVisible(true);
    }

    public void assignController(SetupScreenController myNewController) {
        this.myController = myNewController;
        init();
    }

    /**
     * Manual initiation of components
     */
    private void init() {
        addListeners();
        updateServerList();
    }

    /**
     * This method adds the listener to the different items of the form
     */
    private void addListeners() {
        addServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    private void deleteServer() {
        String newSelection = (String) serverList.getSelectedValue();
        ServerInfo selectedServerInfo = myMapServerInfo.get(newSelection);
        myMapServerInfo.remove(newSelection);
        myController.removeServer(selectedServerInfo);
        updateServerList();
    }

    private void selectServer() {
        String newSelection = (String) serverList.getSelectedValue();
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

    private void handleAddServerButton() {
        String newServerName = serverNameField.getText();
        String newServerIP = serverIPField.getText();
        String newServerPort = serverPortField.getText();
        myController.addServer(newServerName, newServerIP, newServerPort);
    }

    /**
     * Handles the Join Button
     */
    private void handleJoinButton() {
        String myNickName = nicknameField.getText();

        if (getServerInfoFromList() == null) {
            JOptionPane.showMessageDialog(null, "Veuillez selectionner un serveur");
            return;
        }

        if (myNickName.length() > 2) {
            try {
                myController.joinServer(getServerInfoFromList(), myNickName);
            } catch (Exception ConnectException) {
                JOptionPane.showMessageDialog(null, "Echec de connection.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le nickname doit avoir au moin 3 lettres.");
        }
    }

    /**
     * Returns the ServerInfo object linked to the selected server.
     *
     * @return
     */
    private ServerInfo getServerInfoFromList() {
        String selected = (String) serverList.getSelectedValue();
        return myMapServerInfo.get(selected);
    }

    private void createUIComponents() {
        serverList = new JList<String>(new DefaultListModel<String>());
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.setBackground(new Color(-16777216));
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        containerPanel.setBackground(new Color(-9736591));
        containerPanel.setForeground(new Color(-9736591));
        rootPanel.add(containerPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        buttonPanel.setBackground(new Color(-9736591));
        buttonPanel.setForeground(new Color(-9736591));
        containerPanel.add(buttonPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-9736591));
        panel1.setForeground(new Color(-9736591));
        buttonPanel.add(panel1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0), null));
        nicknameField = new JTextField();
        panel1.add(nicknameField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(150, 30), new Dimension(150, 30), new Dimension(150, 30), 0, false));
        nicknameLabel = new JLabel();
        nicknameLabel.setHorizontalAlignment(4);
        nicknameLabel.setHorizontalTextPosition(4);
        nicknameLabel.setText("Nickname: ");
        panel1.add(nicknameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        joinButton = new JButton();
        joinButton.setText("Join");
        joinButton.setMnemonic('J');
        joinButton.setDisplayedMnemonicIndex(0);
        panel1.add(joinButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(100, 30), new Dimension(100, 30), 0, false));
        deleteServerButton = new JButton();
        deleteServerButton.setText("Delete Server");
        buttonPanel.add(deleteServerButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(130, 30), new Dimension(130, 30), new Dimension(130, 30), 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBackground(new Color(-7959666));
        containerPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null));
        serverList.setForeground(new Color(-16777216));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        defaultListModel1.addElement("new DefaultListModel<String>();");
        serverList.setModel(defaultListModel1);
        serverList.setSelectionForeground(new Color(-9736591));
        scrollPane1.setViewportView(serverList);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-9736591));
        panel2.setForeground(new Color(-9736591));
        containerPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-9736591)));
        serverNameField = new JTextField();
        serverNameField.setForeground(new Color(-16777216));
        serverNameField.setText("Name");
        panel2.add(serverNameField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 30), new Dimension(150, 30), new Dimension(150, 30), 0, false));
        serverPortField = new JTextField();
        serverPortField.setText("Port");
        panel2.add(serverPortField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, 30), new Dimension(80, 30), new Dimension(80, 30), 0, false));
        addServerButton = new JButton();
        addServerButton.setText("Add Server");
        addServerButton.setMnemonic('A');
        addServerButton.setDisplayedMnemonicIndex(0);
        panel2.add(addServerButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(100, 30), new Dimension(100, 30), 0, false));
        serverIPField = new JFormattedTextField();
        serverIPField.setText("IP");
        panel2.add(serverIPField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, 30), new Dimension(150, 30), new Dimension(150, 30), 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
