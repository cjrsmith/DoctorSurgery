package Assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * DoctorList class:
 *
 *            Select a new doctor from a list of all doctors.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class DoctorList extends Window{

    private JFrame backTo;
    private PatientDetails detailsScreen;

    /**
     * Constructor
     * @param backTo
     * @param dID
     * @param p
     */
    public DoctorList(JFrame backTo, String dID, PatientDetails p) {
        super(dID);
        detailsScreen = p;
        this.backTo = backTo;
        super.construct(backTo, "Doctors", "Select a doctor from the list");
        build();
    }

    /**
     * Customize the the inherited features of Window to include all the functionality needed to display a list of doctors.
     */
    public void build(){
        JPanel header = new JPanel();
        JLabel first = new JLabel("First Name");
        JLabel last = new JLabel("Last Name");
        JLabel id = new JLabel("Doctor ID");
        JLabel room = new JLabel("Room");
        JLabel select = new JLabel("Select");

        JScrollPane tablePanel = new JScrollPane();
        tablePanel = new JScrollPane(innerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        bottom.setLayout(new GridLayout(1, 2));

        header.setLayout(new GridLayout(1,5));
        header.setPreferredSize(new Dimension(500, 20));

        first.setHorizontalAlignment(JLabel.CENTER);
        last.setHorizontalAlignment(JLabel.CENTER);
        id.setHorizontalAlignment(JLabel.CENTER);
        room.setHorizontalAlignment(JLabel.CENTER);
        select.setHorizontalAlignment(JLabel.CENTER);

        header.setBackground(new Color(200,200,200));

        header.add(id);
        header.add(first);
        header.add(last);
        header.add(room);
        header.add(select);
        innerPanel.add(header);

        ArrayList<String> drList = db.getDoctorList();
        for(int i = 0; i < drList.size(); i=i+4){
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(1,5));
            row.setPreferredSize(new Dimension(500,20));
            JLabel did = new JLabel(drList.get(i));
            did.setHorizontalAlignment(JLabel.CENTER);
            JLabel fName = new JLabel(drList.get(i+1));
            fName.setHorizontalAlignment(JLabel.CENTER);
            JLabel sName = new JLabel(drList.get(i+2));
            sName.setHorizontalAlignment(JLabel.CENTER);
            JLabel rm = new JLabel(drList.get(i+3));
            rm.setHorizontalAlignment(JLabel.CENTER);
            JButton selectDr = new JButton("Select");
            selectDr.setName(drList.get(i));
            row.add(did);
            row.add(fName);
            row.add(sName);
            row.add(rm);
            row.add(selectDr);
            innerPanel.add(row);
            selectDr.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    detailsScreen.updateScreen(sName.getText(), selectDr.getName(), true);
                    DoctorList.super.mainFrame.setVisible(false);
                    db.addLog(Log.SELECT_DOCTOR, detailsScreen.getPatientDr(), DoctorID);
                }
            });
        }

        main.add(tablePanel);
        mainFrame.setVisible(true);
    }
}
