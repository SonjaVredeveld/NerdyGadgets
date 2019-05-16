package kbs2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Boolean.FALSE;
import javax.swing.*;

public class LoginScreen extends JFrame implements ActionListener {

    private JTextField JTFUsername;
    private JTextField JTFPassword;
    private JButton JBSubmit;
    private JLabel JLinfoText;
    private JLabel JLtitle;
    private JLabel JLlostPassword;
    private JLabel JLuserName;
    private JLabel JLpassWord;
    private JPanel Main;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    public LoginScreen() {
        //Layout
        setTitle("Inloggen");
        setSize(800, 600);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel background = new JLabel(new ImageIcon(this.getClass().getResource("squares-3d-digital-background-free-file.jpg")));
        add(background);
        background.setLayout(new GridBagLayout());

        // JFrame elements
        JLtitle = new JLabel();
        JLtitle.setText("NerdyGadgets Backoffice");
        Font font1 = new Font("Rockwell", Font.BOLD, 14);
        JLtitle.setFont(font1);

        JLuserName = new JLabel();
        JLuserName.setText("User Name :");
        JTFUsername = new JTextField();

        JLpassWord = new JLabel();
        JLpassWord.setText("Password :");
        JTFPassword = new JPasswordField();

        JBSubmit = new JButton("Inloggen");
        JBSubmit.setBackground(new Color(158, 188, 237));
        JBSubmit.setForeground(Color.BLACK);
        JBSubmit.addActionListener(this);

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
        background.add(panel2, gbc);
        background.add(JLinfoText, gbc);
        background.add(panel1, gbc);
        background.add(JLlostPassword, gbc);
        background.add(panel3, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public boolean login(String userName, String password) {
        User user = new User(userName, password);
        String level = user.getLevel();
        System.out.println(level);
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
            return FALSE;
        }
    }

    public String getUsername() {
        return JTFUsername.getText();
    }

    public String getPassword() {
        //hier moet dan iets met hashing komen
        return JTFPassword.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (login(getUsername(), getPassword())) {
            dispose();

            // Hier moet nog bepaalt worden wie heeft ingelogd en welk scherm vervolgens wordt getoond.
        } else {
            //Text for wrong input
            JLinfoText.setText("Uw gebruikersnaam of wachtwoord is verkeerd");
            JLinfoText.setForeground(Color.RED);
            JLlostPassword.setText("<html>gebruikersnaam of wachtwoord vergeten? <br> neem contact op met uw systeembeheerder.</html>");
        }
    }

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
        LoginScreen LS = new LoginScreen();
        LS.setVisible(true);

    }
}
