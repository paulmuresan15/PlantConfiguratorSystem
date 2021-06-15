import javax.swing.*;
import java.io.IOException;

public class GUI {
    private JPanel mainPanel;
    private JTextField userTextField;
    private JTextField addComponentTextField;
    private JButton addNewPlantComponentButton;
    private JComboBox componentsComboBox;
    private JButton removePlantComponentButton;
    private JComboBox sensorComponentsComboBox;
    private JTextField sensorIdTextField;
    private JButton addSensorButton;
    private JButton logInButton;
    private JTextArea logTextArea;
    private JComboBox changeStateComboBox;
    private JButton changeStateButton;
    private JScrollPane scrollPanel;
    private JButton logOutButton;
    private JButton clearLogButton;
    private JButton checkStateButton;
    private JFrame frame= new JFrame("Plant Configurator");

    public GUI(){
        frame.add(mainPanel);
        frame.setSize(650,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getUserTextField() {
        return userTextField;
    }



    public JTextField getAddComponentTextField() {
        return addComponentTextField;
    }

    public JButton getAddNewPlantComponentButton() {
        return addNewPlantComponentButton;
    }

    public JComboBox getComponentsComboBox() {
        return componentsComboBox;
    }

    public JButton getRemovePlantComponentButton() {
        return removePlantComponentButton;
    }

    public JComboBox getSensorComponentsComboBox() {
        return sensorComponentsComboBox;
    }

    public JTextField getSensorIdTextField() {
        return sensorIdTextField;
    }

    public JButton getAddSensorButton() {
        return addSensorButton;
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JTextArea getLogTextArea() {
        return logTextArea;
    }

    public JComboBox getChangeStateComboBox() {
        return changeStateComboBox;
    }

    public JButton getChangeStateButton() {
        return changeStateButton;
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }

    public JScrollPane getScrollPanel() {
        return scrollPanel;
    }

    public JButton getClearLogButton() {
        return clearLogButton;
    }

    public JButton getCheckStateButton() {
        return checkStateButton;
    }


}
