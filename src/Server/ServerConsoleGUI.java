package Server;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by pewtroof on 2014-11-19.
 */
public class ServerConsoleGUI extends JFrame {
    private ServerController myController;
    private JTextField textFieldConsole;
    private JPanel entryPanel;
    private JTextArea textAreaConsole;

    public JPanel getPanel() {
        return entryPanel;
    }

    public ServerConsoleGUI() {
        setTitle("Server Console");
        setContentPane(entryPanel);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textFieldConsole.addKeyListener(
                new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                            if (!textFieldConsole.getText().isEmpty()) {
                                String command = textFieldConsole.getText();
                                myController.doThis(command);
                                textFieldConsole.setText("");
                            }
                        }
                    }
                }
        );
    }

    public void assignController(ServerController toAssign) {
        this.myController = toAssign;
    }

    public void writeToConsole(String toWrite) {
        textAreaConsole.append(">  " + toWrite + "\n");
        textAreaConsole.setCaretPosition(textAreaConsole.getDocument().getLength());
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
        entryPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        entryPanel.setMinimumSize(new Dimension(250, 250));
        entryPanel.setPreferredSize(new Dimension(700, 500));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        entryPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAreaConsole = new JTextArea();
        textAreaConsole.setBackground(new Color(-16777216));
        textAreaConsole.setEditable(false);
        textAreaConsole.setForeground(new Color(-1));
        scrollPane1.setViewportView(textAreaConsole);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        entryPanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        textFieldConsole = new JTextField();
        textFieldConsole.setBackground(new Color(-16777216));
        textFieldConsole.setForeground(new Color(-1));
        panel2.add(textFieldConsole, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return entryPanel;
    }
}
