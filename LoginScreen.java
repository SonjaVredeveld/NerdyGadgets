
package kbs2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Boolean.FALSE;


public class LoginScreen extends JFrame implements ActionListener{
    private JTextField JTFUsername;
    private JTextField JTFPassword;
    private JButton JBSubmit;
    private JLabel JLinfoText;
    private JLabel JLtitle;
    private JLabel JLlostPassword;
    private JLabel JLuserName;
    private JLabel JLpassWord;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel4;


    public LoginScreen(){
        setLayout(new GridBagLayout());
        setTitle("Inloggen");
        setSize(800, 600);
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints gba = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
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

        panel = new JPanel(new GridLayout(3, 1));
        panel.setPreferredSize(new Dimension(300, 100));

        panel2 = new JPanel(new GridLayout(1, 1));
        panel2.setPreferredSize(new Dimension(300, 100));

        panel4 = new JPanel(new GridLayout(1, 1));
        panel4.setPreferredSize(new Dimension(0, 100));

        // Volgorde voor het toevoegen van elementen
        panel2.add(JLtitle, gbc);
        panel.add(JLuserName, gbc);
        panel.add(JTFUsername, gbc);
        panel.add(JLpassWord, gbc);
        panel.add(JTFPassword, gbc);
        panel.add(JBSubmit, gbc);
        add(JLinfoText, gbc);
        add(panel2, gbc);
        add(panel, gbc);
        add(JLlostPassword, gbc);
        add(panel4, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    public boolean login(String username,String password) {
        return FALSE;
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
        String userName = JTFUsername.getText();
        String password = JTFPassword.getText();
        if (userName.trim().equals("admin") && password.trim().equals("admin")) {

            // Hier moet nog bepaalt worden wie heeft ingelogd en welk scherm vervolgens wordt getoond.

            JLinfoText.setText(" Hello " + userName
                    + "");
        } else {
            JLinfoText.setText("Uw gebruikersnaam of wachtwoord is verkeerd");
            JLinfoText.setForeground(Color.RED);
            JLlostPassword.setText("<html>gebruikersnaam of wachtwoord vergeten? <br> neem contact op met uw systeembeheerder.</html>");
        }
    }
}
