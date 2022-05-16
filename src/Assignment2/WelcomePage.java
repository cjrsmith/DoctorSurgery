package Assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * WelcomePage class:
 *
 *          Display a welcome page that shows any un-read messages. From here you can view patients or appointments.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class WelcomePage extends Window{

    private JPanel headers;
    private WelcomePage welPage;

    /**
     * Constructor
     * @param dID
     */
    public WelcomePage(String dID) {
        super(dID);
        welPage = this;
        super.construct(null, "Welcome Page ", "Welcome");
        build();
    }

    /**
     * Customize the the inherited features of Window to include all the functionality needed to display messages.
     */
    public void build(){
        String surname = db.getDoctor(DoctorID);

        headers = new JPanel();

        JLabel heading = new JLabel("Welcome Back, Dr. " + surname + ". Here are your un-read messages.");
        JLabel msgID = new JLabel("Message ID");
        JLabel from = new JLabel("From Doctor");
        JLabel date = new JLabel("Message Date");
        JLabel time = new JLabel("Message Time");
        JLabel messages = new JLabel("Message");
        JLabel read = new JLabel("Mark as read");

        JButton appointmentButton, patientsButton;
        appointmentButton = new JButton("Appointments");
        patientsButton = new JButton("Patients");

        JScrollPane messagePanel;
        messagePanel = new JScrollPane(innerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        bottom.setLayout(new GridLayout(1,2));

        headers.setLayout(new GridLayout(1,4));
        headers.setPreferredSize(new Dimension(745, 20));

        msgID.setHorizontalAlignment(JLabel.CENTER);
        from.setHorizontalAlignment(JLabel.CENTER);
        date.setHorizontalAlignment(JLabel.CENTER);
        time.setHorizontalAlignment(JLabel.CENTER);
        messages.setHorizontalAlignment(JLabel.CENTER);
        read.setHorizontalAlignment(JLabel.CENTER);

        messagePanel.setBackground(new Color(255,255,255));
        headers.setBackground(new Color(200,200,200));

        heading.setFont(new Font("Verdana", Font.BOLD, 18));

        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainFrame.setVisible(false);
                db.addLog(Log.PATIENTS, null, DoctorID);
                PatientSummary summary = new PatientSummary(mainFrame, DoctorID, welPage);
            }
        });
        appointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainFrame.setVisible(false);
                db.addLog(Log.APPOINTMENTS, null, DoctorID);
                AppointmentList aptList = new AppointmentList(mainFrame, DoctorID, welPage);
            }
        });
        bottom.remove(back);
        bottom.add(patientsButton, BorderLayout.CENTER);
        bottom.add(appointmentButton, BorderLayout.CENTER);
        headers.add(msgID);
        headers.add(from);
        headers.add(date);
        headers.add(time);
        headers.add(read);
        innerPanel.add(headers);

        showMessages();

        main.add(heading, Component.TOP_ALIGNMENT);
        main.add(messagePanel);
        mainFrame.setVisible(true);
    }

    /**
     * Loop through an arrayList of messages and build a panel of information (representing a row) to be added to the innerPane.
     */
    public void showMessages(){
        ArrayList<String> messageDetails = db.getDoctorMessages(DoctorID);
        for(int i = 0; i < messageDetails.size(); i=i+5){
            JPanel rows = new JPanel();
            JPanel msgRow = new JPanel();
            JTextArea msg = new JTextArea("MESSAGE: " + messageDetails.get(i+2));
            JLabel ID = new JLabel(messageDetails.get(i));
            JLabel fromD = new JLabel(messageDetails.get(i+1));
            JLabel messageD = new JLabel(messageDetails.get(i+3));
            JLabel messageT = new JLabel(messageDetails.get(i+4));
            JButton mark = new JButton("Mark as Read");

            rows.setLayout(new GridLayout(1,4));
            rows.setPreferredSize(new Dimension(650,20));

            msgRow.setLayout(new FlowLayout(FlowLayout.LEFT));
            msgRow.setPreferredSize(new Dimension(650,20));

            ID.setHorizontalAlignment(JLabel.CENTER);
            fromD.setHorizontalAlignment(JLabel.CENTER);
            messageD.setHorizontalAlignment(JLabel.CENTER);
            messageT.setHorizontalAlignment(JLabel.CENTER);

            msgRow.setBackground(new Color(255,255,255));

            mark.setName(messageDetails.get(i));

            rows.add(ID);
            rows.add(fromD);
            rows.add(messageD);
            rows.add(messageT);
            rows.add(mark);
            msgRow.add(msg);
            innerPanel.add(rows);
            innerPanel.add(msgRow);

            mark.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    db.readMessage(mark.getName());
                    mainFrame.setVisible(false);
                    db.addLog(Log.READ, ("MsgID: " + ID.getText()), DoctorID);
                    updateScreen();
                    setVisibility(true);
                }
            });
        }
    }

    /**
     * Update the screen with the current list of messages.
     */
    public void updateScreen(){
        innerPanel.removeAll();
        innerPanel.add(headers);
        showMessages();
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Set the frame visible or not.
     * (Needed for functionality in other classes).
     * @param b
     */
    public void setVisibility(Boolean b){
        mainFrame.setVisible(b);
    }
}
