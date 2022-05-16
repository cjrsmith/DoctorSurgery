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
 * Window class:
 *
 *              Superclass from which all other classes inherit.
 *              Creates a basic window with stylization, to remove code duplication.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class Window {
    protected JFrame mainFrame;
    protected Database db;
    protected String DoctorID;
    protected JPanel main, bottom, top, innerPanel;
    protected JButton logout, back;
    protected LogInPage loginPage;

    /**
     * Constructor
     * @param dID
     */
    public Window(String dID){
        db = new Database();
        DoctorID = dID;
    }

    /**
     * Create the window and all its attributes.
     * @param backTo
     * @param frameName
     * @param title
     */
    public void construct(JFrame backTo, String frameName, String title){
        mainFrame = new JFrame(frameName);
        main = new JPanel();
        bottom = new JPanel();
        top = new JPanel();
        innerPanel = new JPanel();
        logout = new JButton("Logout");
        back = new JButton("Back");

        mainFrame.setSize(800, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                db.close();
            }
        });

        main.setLayout(new FlowLayout(FlowLayout.CENTER));
        main.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.CENTER, TitledBorder.TOP));
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        top.setLayout(new FlowLayout(FlowLayout.RIGHT));

        top.setBackground(new Color(64,224,208));
        main.setBackground(new Color(64,224,208));
        bottom.setBackground(new Color(64,224,208));

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainFrame.setVisible(false);
                db.addLog(Log.LOGOUT, null, DoctorID);
                db.close();
                loginPage = new LogInPage();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                db.addLog(Log.BACK, null, DoctorID);
                backTo.setVisible(true);
            }
        });

        top.add(logout, BorderLayout.CENTER);
        bottom.add(back, BorderLayout.CENTER);

        main.add(innerPanel);
        mainFrame.add(main, BorderLayout.CENTER);
        mainFrame.add(bottom, BorderLayout.SOUTH);
        mainFrame.add(top, BorderLayout.NORTH);
    }


}
