package Assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * ViewAppointment class:
 *
 *               Allows users to view a specific appointments details.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class ViewAppointment extends Window{

    private JPanel idPanel, datePanel, timePanel, roomPanel, detailsPanel, prescIdPanel, prescDetailsPanel, patientIdPanel, firstPanel;
    private JLabel id, apptDate, apptTime, roomNumber, apptDetails, prescriptID, prescriptDetails, patientID, patientName;
    private JLabel aID, aDate, aTime, rNum, aDetails, ptID, ptName, prID, prDetails;

    private String ApptID;
    private boolean newDetailsSet;
    private boolean modOld;
    private JFrame backTo;
    private WelcomePage welPage;

    private ViewAppointment viewPage;

    /**
     * Constructor
     * @param backTo
     * @param dID
     * @param aID
     * @param wp
     */
    public ViewAppointment(JFrame backTo, String dID, String aID, WelcomePage wp) {
        super(dID);
        viewPage = this;
        welPage = wp;
        ApptID = aID;
        newDetailsSet = false;
        modOld = false;
        this.backTo = backTo;
        super.construct(backTo, "View Appointment", "View Appointment");
        build();
    }

    /**
     * Customize the the inherited features of Window to include all the functionality needed to display the appointments.
     */
    public void build(){
        JButton editButton;
        editButton = new JButton("Edit");

        bottom.setLayout(new GridLayout(1,2));

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ViewAppointment.super.mainFrame.setVisible(false);
                db.addLog(Log.EDIT_APPT, ApptID, DoctorID);
                EditAppointments editPanel = new EditAppointments(mainFrame, DoctorID, ApptID, welPage);
                editPanel.receiveViewPanel(viewPage);
            }
        });

        displayDetails();

        bottom.add(editButton, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    public void displayDetails(){
        ArrayList<String> appointmentDetails = db.getAppointmentDetails(ApptID);

        id = new JLabel("Appointment ID:");
        apptDate = new JLabel("Appointment Date:");
        apptTime = new JLabel("Appointment Time:");
        roomNumber = new JLabel("Room Number:");
        apptDetails = new JLabel("Appointment Details:");
        prescriptID = new JLabel("Prescription ID:");
        prescriptDetails = new JLabel("Prescription Details:");
        patientID = new JLabel("Patient ID:");
        patientName = new JLabel("Patient Name:");

        idPanel = new JPanel();
        datePanel = new JPanel();
        timePanel = new JPanel();
        roomPanel = new JPanel();
        detailsPanel = new JPanel();
        prescIdPanel = new JPanel();
        prescDetailsPanel = new JPanel();
        patientIdPanel = new JPanel();
        firstPanel = new JPanel();

        idPanel.setLayout(new GridLayout(1,2));
        datePanel.setLayout(new GridLayout(1, 2));
        timePanel.setLayout(new GridLayout(1,2));
        roomPanel.setLayout(new GridLayout(1,2));
        detailsPanel.setLayout(new GridLayout(1,2));
        prescIdPanel.setLayout(new GridLayout(1,2));
        prescDetailsPanel.setLayout(new GridLayout(1,2));
        patientIdPanel.setLayout(new GridLayout(1,2));
        firstPanel.setLayout(new GridLayout(1,2));
        firstPanel.setPreferredSize(new Dimension(600, 20));

        aID = new JLabel(appointmentDetails.get(0));
        aDate = new JLabel(appointmentDetails.get(1));
        aTime = new JLabel(appointmentDetails.get(2));
        rNum = new JLabel(appointmentDetails.get(3));
        aDetails = new JLabel(appointmentDetails.get(4));
        ptID = new JLabel(appointmentDetails.get(7));
        ptName = new JLabel(appointmentDetails.get(8) + " " + appointmentDetails.get(9));
        prID = new JLabel(appointmentDetails.get(5));
        prDetails = new JLabel(appointmentDetails.get(6));

        innerPanel.add(idPanel);
        idPanel.add(id);
        idPanel.add(aID);

        innerPanel.add(datePanel);
        datePanel.add(apptDate);
        datePanel.add(aDate);

        innerPanel.add(timePanel);
        timePanel.add(apptTime);
        timePanel.add(aTime);

        innerPanel.add(roomPanel);
        roomPanel.add(roomNumber);
        roomPanel.add(rNum);

        innerPanel.add(detailsPanel);
        detailsPanel.add(apptDetails);
        detailsPanel.add(aDetails);

        innerPanel.add(prescIdPanel);
        prescIdPanel.add(prescriptID);
        prescIdPanel.add(prID);

        innerPanel.add(prescDetailsPanel);
        prescDetailsPanel.add(prescriptDetails);
        prescDetailsPanel.add(prDetails);

        innerPanel.add(patientIdPanel);
        patientIdPanel.add(patientID);
        patientIdPanel.add(ptID);

        innerPanel.add(firstPanel);
        firstPanel.add(patientName);
        firstPanel.add(ptName);
    }

    /**
     * Update the screen with new doctor details.
     */
    public void updateScreen(){
        innerPanel.removeAll();
        displayDetails();
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
