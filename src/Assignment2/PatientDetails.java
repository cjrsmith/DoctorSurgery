package Assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * PatientDetails class:
 *
 *            Create a window which displays all patient information and what doctor they are associated with.
 *            This window includes a button which allows you to change the doctor associated with the patient.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class PatientDetails extends Window{

    private JButton doctors;
    private JPanel drPanel;
    private JLabel doctor;
    private JLabel drDet;
    private JFrame backTo;
    private String patientID;
    private boolean newDoctor;
    private String patientDr;
    private String newDrSurname;
    private String doctorDetails;
    private PatientDetails pd;
    private PatientSummary sumScreen;
    private WelcomePage welPage;

    /**
     * Constructor
     * @param backTo
     * @param dID
     * @param pID
     * @param sum
     * @param wp
     */
    public PatientDetails(JFrame backTo, String dID, String pID, PatientSummary sum, WelcomePage wp) {
        super(dID);
        pd = this;
        welPage = wp;
        this.backTo = backTo;
        sumScreen = sum;
        this.patientID = pID;
        super.construct(backTo, "Patient Details", ("Patient Details for " + patientID));
        build();
    }

    /**
     * Customize the the inherited features of Window to include all the functionality needed to display the details of patients.
     */
    public void build(){
        ArrayList<String> patientInfo = db.getPatientInfo(patientID);
        newDrSurname = patientInfo.get(8);
        patientDr = patientInfo.get(6);
        doctorDetails = "Dr." + " " + newDrSurname + "  " + "(" + patientDr + ")";

        JButton confirm;
        doctors = new JButton("Change");
        confirm = new JButton("Confirm Changes");

        JLabel id = new JLabel("Patient ID:");
        JLabel firstName = new JLabel("First name:");
        JLabel surname = new JLabel("Surname:");
        JLabel gender = new JLabel("Gender:");
        JLabel dob = new JLabel("Date of birth:");
        JLabel phone = new JLabel("Contact number:");
        doctor = new JLabel("Doctor:");

        JPanel idPanel, firstPanel, secondPanel, genderPanel, dobPanel, phonePanel;
        idPanel = new JPanel();
        firstPanel = new JPanel();
        secondPanel = new JPanel();
        genderPanel = new JPanel();
        dobPanel = new JPanel();
        phonePanel = new JPanel();
        drPanel = new JPanel();

        JTextField pID = new JTextField(patientInfo.get(0));
        JTextField fName = new JTextField(patientInfo.get(1));
        JTextField sName = new JTextField(patientInfo.get(2));
        JTextField g = new JTextField(patientInfo.get(3));
        JTextField date = new JTextField(patientInfo.get(4));
        JTextField p = new JTextField(patientInfo.get(5));
        drDet = new JLabel(doctorDetails);

        idPanel.setLayout(new GridLayout(1,2));
        firstPanel.setLayout(new GridLayout(1,2));
        secondPanel.setLayout(new GridLayout(1,2));
        genderPanel.setLayout(new GridLayout(1,2));
        dobPanel.setLayout(new GridLayout(1,2));
        phonePanel.setLayout(new GridLayout(1,2));
        drPanel.setLayout(new GridLayout(1,3));
        drPanel.setPreferredSize(new Dimension(770, 20));

        bottom.setLayout(new GridLayout(1,2));

        doctors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addLog(Log.DOCTORS, null, DoctorID);
                DoctorList dList = new DoctorList(mainFrame, DoctorID, pd);
            }
        });
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addLog(Log.CONFIRM, null, DoctorID);
                String newID = pID.getText();
                String newFName = fName.getText();
                String newSName = sName.getText();
                String newGender = g.getText();
                String newDate = date.getText();
                String newNumber = p.getText();
                String query = String.format("update Patient set PatientID = '%s', PatientFirstName = '%s', PatientSurname = '%s', PatientGender = '%s', PatientDoB = '%s', PatientPhoneNo = '%s', DoctorID = '%s' where patientID = '%s';", newID, newFName, newSName, newGender, newDate, newNumber, patientDr, patientID);
                db.update(query);
                if(newDoctor){
                    db.addLog(Log.NEW_DOCTOR, ("PatientID: " + pID.getText() + " NewDoctorID: " + patientDr), DoctorID);
                    SendConfirmation.sendConfirmationMessages(Confirmation.NEW_DOCTOR, patientID, patientDr, newDrSurname, DoctorID, db);
                    newDoctor = false;
                }
                PatientDetails.super.mainFrame.setVisible(false);
                welPage.updateScreen();
                sumScreen.updateScreen();
            }
        });

        bottom.add(confirm);
        innerPanel.add(idPanel);
        idPanel.add(id);
        idPanel.add(pID);
        innerPanel.add(firstPanel);
        firstPanel.add(firstName);
        firstPanel.add(fName);
        innerPanel.add(secondPanel);
        secondPanel.add(surname);
        secondPanel.add(sName);
        innerPanel.add(genderPanel);
        genderPanel.add(gender);
        genderPanel.add(g);
        innerPanel.add(dobPanel);
        dobPanel.add(dob);
        dobPanel.add(date);
        innerPanel.add(phonePanel);
        phonePanel.add(phone);
        phonePanel.add(p);
        innerPanel.add(drPanel);
        drPanel.add(doctor);
        drPanel.add(drDet);
        drPanel.add(doctors);

        mainFrame.setVisible(true);
    }

    /**
     * Update the screen with new doctor details.
     * @param surname
     * @param doctorID
     * @param setNewDoctor
     */
    public void updateScreen(String surname, String doctorID, Boolean setNewDoctor){
        newDoctor = setNewDoctor;
        newDrSurname = surname;
        patientDr = doctorID;
        doctorDetails = "CONFIRM: Dr." + " " + newDrSurname + "  " + "(" + patientDr + ")";
        drPanel.remove(doctor);
        drPanel.remove(drDet);
        drPanel.remove(doctors);
        drDet = new JLabel(doctorDetails);
        drDet.setForeground(new Color(213, 18, 40));
        drPanel.add(doctor);
        drPanel.add(drDet);
        drPanel.add(doctors);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Return the patientDr
     * @return
     */
    public String getPatientDr(){
        return patientDr;
    }
}
