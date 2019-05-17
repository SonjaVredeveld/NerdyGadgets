package kbs2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginScreen extends JFrame implements ActionListener {

    private JTextField JTFUsername, JTFPassword;
    private JButton JBSubmit, JBCancel;
    private JLabel JLinfoText, JLtitle, JLlostPassword, JLuserName, JLpassWord;
    private JPanel panel1, panel2, panel3;

    public LoginScreen() {
        //Layout
        setTitle("Inloggen");
        setSize(800, 600);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel background = new JLabel(new ImageIcon("src//kbs2//squares-3d-digital-background-free-file.jpg"));
        add(background);
        background.setLayout(new GridBagLayout());

        // JFrame elements
        JLtitle = new JLabel();
        JLtitle.setText("NerdyGadgets Backoffice");
        Font rockwellFont = new Font("Rockwell", Font.BOLD, 14);
        JLtitle.setFont(rockwellFont);

        JLuserName = new JLabel();
        JLuserName.setText("Gebruikersnaam :");
        JTFUsername = new JTextField();

        JLpassWord = new JLabel();
        JLpassWord.setText("Wachtwoord :");
        JTFPassword = new JPasswordField();

        JBSubmit = new JButton("Inloggen");
        JBSubmit.setBackground(new Color(158, 188, 237));
        JBSubmit.setForeground(Color.BLACK);
        JBSubmit.addActionListener(this);

        JBCancel = new JButton("Annuleren");
        JBCancel.setBackground(new Color(158, 188, 237));
        JBCancel.setForeground(Color.BLACK);
        JBCancel.addActionListener(this);

        JLlostPassword = new JLabel();
        JLinfoText = new JLabel();

        panel1 = new JPanel(new GridLayout(3, 1));
        panel1.setPreferredSize(new Dimension(300, 100));
        panel1.setBackground(new Color(0, 0, 0, 0));

        panel2 = new JPanel(new GridLayout(1, 1));
        panel2.setPreferredSize(new Dimension(300, 100));
        panel2.setBackground(new Color(0, 0, 0, 0));

        panel3 = new JPanel(new GridLayout(1, 1));
        panel3.setPreferredSize(new Dimension(0, 100));
        panel3.setBackground(new Color(0, 0, 0, 0));

        // Order for elements to appear
        panel2.add(JLtitle, gbc);
        panel1.add(JLuserName, gbc);
        panel1.add(JTFUsername, gbc);
        panel1.add(JLpassWord, gbc);
        panel1.add(JTFPassword, gbc);
        panel1.add(JBSubmit, gbc);
        panel1.add(JBCancel, gbc);
        background.add(panel2, gbc);
        background.add(JLinfoText, gbc);
        background.add(panel1, gbc);
        background.add(JLlostPassword, gbc);
        background.add(panel3, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //tries to create a valid user. and redirects the user to his/her screen
    //param1: username from input
    //param2: password from input
    //return: true when succesful, false when user is not a valid user
    public boolean login(String userName, String password) {
        User user = new User(userName, password);
        String level = user.getLevel();

        //based on the lvl we wil redirect the user
        if (level.equals("Driver")) {
            DriverScreen DS = new DriverScreen(user);
            DS.setVisible(true);
            return true;
        } else if (level.equals("Planner")) {
            PlannerScreen PS = new PlannerScreen(/*user*/);
            PS.setVisible(true);
            return true;
        } else if (level.equals("Administrator")) {
            AdministratorScreen PS = new AdministratorScreen(/*user*/);
            PS.setVisible(true);
            return true;
        } else {
            return false;
        }
    }

    public String getUsername() {
        return JTFUsername.getText();
    }

    public String getPassword() {
        return JTFPassword.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.JBSubmit) {
            //valid user? yes -> we have been redirected so dispose
            if (login(getUsername(), getPassword())) {
                dispose();

                //not valid user -> notify user
            } else {
                JLinfoText.setText("Uw gebruikersnaam en/of wachtwoord is verkeerd’");
                JLinfoText.setForeground(Color.RED);
                JLlostPassword.setText("<html>gebruikersnaam of wachtwoord vergeten? <br> neem contact op met uw systeembeheerder.</html>");
            }
        } else if (e.getSource() == this.JBCancel) {
            dispose();

        }
    }
}
