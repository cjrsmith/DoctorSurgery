package Assignment2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * EditAppointments class:
 *
 *                 Display a window that allows you to edit the appointment details and prescription details.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class EditAppointments extends Window {

    private String ApptID;
    private boolean newDetailsSet;
    private boolean modOld;
    private JFrame backTo;
    private WelcomePage welPage;
    private ViewAppointment viewPage;

    /**
     * Constructor.
     */
    public EditAppointments(JFrame backTo, String dID, String aID, WelcomePage wp) {
        super(dID);
        welPage = wp;
        ApptID = aID;
        newDetailsSet = false;
        modOld = false;
        this.backTo = backTo;
        super.construct(backTo, "Edit Appointment", "Edit Appointment");
        build();
    }

    /**
     * Customize the the inherited features of Window to include all the functionality needed to display the details of the appointment.
     */
    public void build(){
        ArrayList<String> appointmentDetails = db.getAppointmentDetails(ApptID);

        JPanel idPanel, datePanel, timePanel, roomPanel, detailsPanel, prescIdPanel, prescDetailsPanel, patientIdPanel, firstPanel;
        idPanel = new JPanel();
        datePanel = new JPanel();
        timePanel = new JPanel();
        roomPanel = new JPanel();
        detailsPanel = new JPanel();
        prescIdPanel = new JPanel();
        prescDetailsPanel = new JPanel();
        patientIdPanel = new JPanel();
        firstPanel = new JPanel();

        JLabel id = new JLabel("Appointment ID:");
        JLabel ApptDate = new JLabel("Appointment Date:");
        JLabel ApptTime = new JLabel("Appointment Time:");
        JLabel RoomNumber = new JLabel("Room Number:");
        JLabel ApptDetails = new JLabel("Appointment Details:");
        JLabel PrescriptID = new JLabel("Prescription ID:");
        JLabel PrescriptDetails = new JLabel("Prescription Details:");
        JLabel PatientID = new JLabel("Patient ID:");
        JLabel PatientName = new JLabel("Patient Name:");
        JLabel aID = new JLabel(appointmentDetails.get(0));
        JLabel aDate = new JLabel(appointmentDetails.get(1));
        JLabel aTime = new JLabel(appointmentDetails.get(2));
        JLabel rNum = new JLabel(appointmentDetails.get(3));
        JLabel ptID = new JLabel(appointmentDetails.get(7));
        JLabel ptName = new JLabel(appointmentDetails.get(8) + " " + appointmentDetails.get(9));
        JLabel prID = new JLabel(appointmentDetails.get(5));

        JTextArea aDetails = new JTextArea(5, 5);
        JTextArea prDetails = new JTextArea(5, 5);

        JButton confirmation;
        confirmation = new JButton("Confirm Changes");

        JScrollPane scrollPane1 = new JScrollPane(aDetails, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane scrollPane2 = new JScrollPane(prDetails, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        bottom.setLayout(new GridLayout(1,2));

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

        aDetails.setText(appointmentDetails.get(4));
        aDetails.setLineWrap(true);
        if(!(aDetails.getText().equals(""))){
            modOld = true;
        }

        prDetails.setText(appointmentDetails.get(6));
        prDetails.setLineWrap(true);

        String prescriptID = prID.getText();

        aDetails.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                newDetailsSet = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newDetailsSet = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                newDetailsSet = true;
            }
        });
        prDetails.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                newDetailsSet = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newDetailsSet = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                newDetailsSet = true;
            }
        });

        confirmation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addLog(Log.CONFIRM, null, DoctorID);
                String newDetails = aDetails.getText();
                String newPresDet = prDetails.getText();
                String query1 = String.format("update Appointment set ApptDetails = '%s' where ApptID = '%s';", newDetails, ApptID);
                String query2;
                String query3;
                db.update(query1);
                db.addLog(Log.EDIT_APPT_DET, ApptID, DoctorID);
                if(prescriptID == null){
                    switch(newPresDet){
                        case "":
                            break;
                        default:
                            query2 = String.format("INSERT INTO Prescription (PatientID, PrescriptDetails) VALUES('%s', '%s');", appointmentDetails.get(7), newPresDet);
                            db.addLog(Log.ADD_PRES_DET, ApptID, DoctorID);
                            db.update(query2);
                    }
                    String id = db.getLastPrescriptionID();
                    int i = Integer.parseInt(id);
                    String newPresID = Integer.toString(i);
                    query3 = String.format("update Appointment set PrescriptID = '%s' where ApptID = '%s';",newPresID, ApptID);
                    db.update(query3);
                }
                else{
                    switch(newPresDet){
                        case "":
                            query2 = String.format("update Prescription set PrescriptDetails = null where PrescriptID = '%s';", prescriptID);
                            db.addLog(Log.EDIT_PRES_DET, ApptID, DoctorID);
                            db.update(query2);
                            break;
                        default:
                            query2 = String.format("update Prescription set PrescriptDetails = '%s' where PrescriptID = '%s';", newPresDet, prescriptID);
                            db.addLog(Log.EDIT_PRES_DET, ApptID, DoctorID);
                            db.update(query2);
                    }
                }
                if(newDetailsSet){
                    if(modOld){
                        SendConfirmation.sendConfirmationMessages(Confirmation.UPDATE_ENTRY, appointmentDetails.get(7), "", "", DoctorID, db);
                    }
                    else{
                        SendConfirmation.sendConfirmationMessages(Confirmation.NEW_ENTRY, appointmentDetails.get(7), "", "", DoctorID, db);
                    }
                }
                mainFrame.setVisible(false);
                welPage.updateScreen();
                if(viewPage != null){
                    viewPage.updateScreen();
                }
                backTo.setVisible(true);
            }
        });

        bottom.remove(back);
        bottom.add(back, BorderLayout.CENTER);
        bottom.add(confirmation, BorderLayout.CENTER);

        innerPanel.add(idPanel);
        idPanel.add(id);
        idPanel.add(aID);

        innerPanel.add(datePanel);
        datePanel.add(ApptDate);
        datePanel.add(aDate);

        innerPanel.add(timePanel);
        timePanel.add(ApptTime);
        timePanel.add(aTime);

        innerPanel.add(roomPanel);
        roomPanel.add(RoomNumber);
        roomPanel.add(rNum);

        innerPanel.add(detailsPanel);
        detailsPanel.add(ApptDetails);
        detailsPanel.add(scrollPane1);

        innerPanel.add(prescIdPanel);
        prescIdPanel.add(PrescriptID);
        prescIdPanel.add(prID);

        innerPanel.add(prescDetailsPanel);
        prescDetailsPanel.add(PrescriptDetails);
        prescDetailsPanel.add(scrollPane2);

        innerPanel.add(patientIdPanel);
        patientIdPanel.add(PatientID);
        patientIdPanel.add(ptID);

        innerPanel.add(firstPanel);
        firstPanel.add(PatientName);
        firstPanel.add(ptName);

        mainFrame.setVisible(true);
    }

    /**
     * Pass a ViewAppointment instance. For use with updating the window.
     * @param vapt
     */
    public void receiveViewPanel(ViewAppointment vapt){
        viewPage = vapt;
    }
}