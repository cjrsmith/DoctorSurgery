package Assignment2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * LogInPage class:
 *
 *      This creates the login page which is the first page that pops up when the program is ran.
 *      It's requires that a doctors enters their correct username and password in the allocated field
 *      If the username or password entered is incorrect, a message is prompt telling the doctor to retry
 *      if the username and password is correct then the login is successful and they are then moved to the
 *      welcome page.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class LogInPage {
    private Database db;

    private static JLabel usernameLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton loginButton;
    private static JLabel successfulLogin;
    private String DoctorID;
    private Boolean newDoctor;

    public LogInPage(){
        DoctorID = "";
        db = new Database();
        newDoctor = false;

        JFrame loginFrame = new JFrame("Login");
        JPanel main, loginPanel, spacer1, spacer2, spacer3;
        JLabel title = new JLabel("The GROUP4B Surgery");
        JLabel logo = new JLabel();
        JLabel footer = new JLabel("By Connor (cs878), James (jv264), Kervin (kjb48) and Matt (mc974)");
        main = new JPanel();
        loginPanel = new JPanel();
        spacer1 = new JPanel();
        spacer2 = new JPanel();
        spacer3 = new JPanel();
        ImageIcon imageIcon = new ImageIcon("kent_logo.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(180, 100,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        usernameLabel = new JLabel("User:");
        passwordLabel = new JLabel("Password:");
        successfulLogin = new JLabel("");
        userText = new JTextField(30);
        passwordText = new JPasswordField(30);
        loginButton = new JButton("Login");
        logo.setIcon(imageIcon);

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(800, 500);
        loginFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                db.close();
            }
        });

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.setSize(800, 500);
        main.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Please enter user details to log in", TitledBorder.CENTER, TitledBorder.TOP));

        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setPreferredSize(new Dimension(400, 80));
        loginPanel.setMaximumSize(new Dimension(400, 80));
        loginPanel.setMinimumSize(new Dimension(400, 80));

        spacer1.setPreferredSize(new Dimension(800, 20));
        spacer1.setMaximumSize(new Dimension(800, 20));
        spacer1.setMinimumSize(new Dimension(800, 20));

        spacer2.setPreferredSize(new Dimension(800, 20));
        spacer2.setMaximumSize(new Dimension(800, 20));
        spacer2.setMinimumSize(new Dimension(800, 20));

        spacer3.setPreferredSize(new Dimension(800, 20));
        spacer3.setMaximumSize(new Dimension(800, 20));
        spacer3.setMinimumSize(new Dimension(800, 20));

        loginFrame.setBackground(new Color(64,224,208));
        main.setBackground(new Color(64,224,208));
        loginPanel.setBackground(new Color(64, 224, 208));
        spacer1.setBackground(new Color(64, 224, 208));
        spacer2.setBackground(new Color(64, 224, 208));
        spacer3.setBackground(new Color(64, 224, 208));
        title.setForeground(new Color(29, 81, 148));
        footer.setForeground(new Color(57, 56, 56));

        title.setFont(new Font("Century", Font.BOLD, 30));
        usernameLabel.setFont(new Font("Century", Font.BOLD, 15));
        passwordLabel.setFont(new Font("Century", Font.BOLD, 15));
        footer.setFont(new Font("Century", Font.PLAIN, 13));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String password = passwordText.getText();
                System.out.println(user + " ," + password);
                DoctorID = db.authenticate(user, password);
                if (!DoctorID.equals("Invalid")) {
                    successfulLogin.setText("Login successful!");
                    db.addLog(Log.LOGGED_IN, user, DoctorID);
                    loginFrame.setVisible(false);
                    WelcomePage welcome = new WelcomePage(DoctorID);
                } else {
                    DoctorID = "";
                    successfulLogin.setText("Oops! Try again.");
                    successfulLogin.setForeground(Color.RED);
                    successfulLogin.setFont(new Font("Century", Font.BOLD, 20));
                    db.addLog(Log.LOG_IN_ATTEMPT, (user + " " + password), null);
                }
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(userText);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordText);

        main.add(Box.createVerticalGlue());
        main.add(logo);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(spacer1);
        main.add(loginPanel);
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(spacer2);
        main.add(loginButton);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(spacer3);
        main.add(successfulLogin);
        successfulLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(Box.createVerticalGlue());

        loginFrame.add(main, BorderLayout.CENTER);
        loginFrame.add(footer, BorderLayout.SOUTH);
        loginFrame.setVisible(true);
    }
}
