import javax.swing.*;

public class LoginForm {
    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton logInRegisterButton;
    private JFrame frame= new JFrame("Log in / Register");
    public JButton getLogInRegisterButton() {
        return logInRegisterButton;
    }

    public LoginForm(){
        frame.add(mainPanel);
        frame.setSize(300,300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

}
