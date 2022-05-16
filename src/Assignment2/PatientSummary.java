package Assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * PatientSummary class:
 *
 *          Brings up a list of patients that are assigned to the doctor who is currently logged in.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class PatientSummary extends Window{

    private JPanel header;
    private JFrame backTo;
    private JScrollPane tablePanel;
    private PatientSummary sumScreen;
    private WelcomePage welPage;

    /**
     * Constructor
     * @param backTo
     * @param dID
     * @param wp
     */
    public PatientSummary(JFrame backTo, String dID, WelcomePage wp) {
        super(dID);
        sumScreen = this;
        welPage = wp;
        this.backTo = backTo;
        super.construct(backTo, "Patient Information", "Patient Information");
        build();
    }

    /**
     * Customize the the inherited features of Window to include all the functionality needed to display summaries of patients.
     */
    public void build(){
        header = new JPanel();

        JLabel first = new JLabel("First Name");
        JLabel last = new JLabel("Last Name");
        JLabel phone = new JLabel("Phone Number");
        JLabel view = new JLabel("View Details");

        tablePanel = new JScrollPane(innerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        bottom.setLayout(new GridLayout(1,2));

        tablePanel.setBackground(new Color(255,255,255));
        header.setBackground(new Color(200,200,200));

        header.setLayout(new GridLayout(1,4));
        header.setPreferredSize(new Dimension(650, 20));

        first.setHorizontalAlignment(JLabel.CENTER);
        last.setHorizontalAlignment(JLabel.CENTER);
        phone.setHorizontalAlignment(JLabel.CENTER);
        view.setHorizontalAlignment(JLabel.CENTER);

        header.add(first);
        header.add(last);
        header.add(phone);
        header.add(view);
        innerPanel.add(header);

        listOfPatients();

        main.remove(innerPanel);
        main.add(tablePanel);

        mainFrame.setVisible(true);
    }

    /**
     * Build a list of patients for the current doctor.
     */
    public void listOfPatients(){
        ArrayList<String> patientInfo = db.getNamesAndNumbers(DoctorID);
        for(int i = 0; i < patientInfo.size(); i=i+4){
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(1,4));
            row.setPreferredSize(new Dimension(650,20));
            JLabel fName = new JLabel(patientInfo.get(i+1));
            fName.setHorizontalAlignment(JLabel.CENTER);
            JLabel sName = new JLabel(patientInfo.get(i+2));
            sName.setHorizontalAlignment(JLabel.CENTER);
            JLabel number = new JLabel(patientInfo.get(i+3));
            number.setHorizontalAlignment(JLabel.CENTER);
            JButton viewPatient = new JButton("View");
            viewPatient.setName(patientInfo.get(i));
            row.add(fName);
            row.add(sName);
            row.add(number);
            row.add(viewPatient);
            innerPanel.add(row);
            viewPatient.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PatientSummary.super.mainFrame.setVisible(false);
                    db.addLog(Log.VIEW_PATIENT, ("PatientID: " + viewPatient.getName()), DoctorID);
                    PatientDetails patientPanel = new PatientDetails(mainFrame, DoctorID, viewPatient.getName(), sumScreen, welPage);
                }
            });
        }
    }

    /**
     * Update the screen with the current list of patients.
     */
    public void updateScreen(){
        innerPanel.removeAll();
        innerPanel.add(header);
        listOfPatients();
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }


}
