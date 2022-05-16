package Assignment2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * AppointmentList class:
 *
 *            Display a list of today's appointments.
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class AppointmentList extends Window{

    private String AppointmentID;
    private JPanel body;
    private JFrame backTo;
    private WelcomePage welPage;

    /**
     * Constructor.
     */
    public AppointmentList(JFrame backTo, String dID, WelcomePage wp) {
        super(dID);
        this.backTo = backTo;
        welPage = wp;
        super.construct(backTo, "Appointment List", "Appointment List");
        build();
    }

    /**
     * Customize the the inherited features of Window to include all the functionality needed to display the appointments.
     */
    public void build(){
        HashMap<String, Integer> monthsKey = new HashMap<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthsKey.put("Jan", 1);
        monthsKey.put("Feb", 2);
        monthsKey.put("Mar", 3);
        monthsKey.put("Apr", 4);
        monthsKey.put("May", 5);
        monthsKey.put("Jun", 6);
        monthsKey.put("Jul", 7);
        monthsKey.put("Aug", 8);
        monthsKey.put("Sep", 9);
        monthsKey.put("Oct", 10);
        monthsKey.put("Nov", 11);
        monthsKey.put("Dec", 12);
        ArrayList<String> yearsList = new ArrayList<String>();
        for(int y = Calendar.getInstance().get(Calendar.YEAR); y>= 1980; y--) {
            yearsList.add(y+"");
        }

        JPanel filterPanel, buttonPanel;
        filterPanel = new JPanel();
        buttonPanel = new JPanel();
        JPanel header = new JPanel();
        JPanel dropBoxes, enterButton;
        dropBoxes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        enterButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel ApptID = new JLabel("ApptID");
        JLabel ApptDate = new JLabel("ApptDate");
        JLabel ApptTime = new JLabel("ApptTime");
        JLabel RoomNumber = new JLabel("Room");
        JLabel PatientFirstName = new JLabel("FirstName");
        JLabel PatientSurname = new JLabel("Surname");
        JLabel viewDetails = new JLabel("View");
        JLabel editDetails = new JLabel("          Edit");

        JButton enter, all, today;
        enter = new JButton("Enter");
        all = new JButton("   All   ");
        today = new JButton("Today");

        JScrollPane tablePanel;
        tablePanel = new JScrollPane(super.innerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JComboBox year = new JComboBox(yearsList.toArray());
        JComboBox month = new JComboBox(months);

        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Filter", TitledBorder.CENTER, TitledBorder.TOP));
        filterPanel.setPreferredSize(new Dimension(500, 110));

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Shortcut", TitledBorder.CENTER, TitledBorder.TOP));
        buttonPanel.setPreferredSize(new Dimension(100, 110));

        header.setLayout(new GridLayout(1,4));
        header.setPreferredSize(new Dimension(750, 20));

        all.setAlignmentX(Component.CENTER_ALIGNMENT);
        today.setAlignmentX(Component.CENTER_ALIGNMENT);
        ApptID.setHorizontalAlignment(JLabel.CENTER);
        ApptDate.setHorizontalAlignment(JLabel.CENTER);
        ApptTime.setHorizontalAlignment(JLabel.CENTER);
        RoomNumber.setHorizontalAlignment(JLabel.CENTER);
        PatientFirstName.setHorizontalAlignment(JLabel.CENTER);
        PatientSurname.setHorizontalAlignment(JLabel.CENTER);
        viewDetails.setHorizontalAlignment(JLabel.CENTER);
        viewDetails.setHorizontalAlignment(JLabel.CENTER);

        top.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottom.setLayout(new GridLayout(1,2));

        tablePanel.setBackground(new Color(255,255,255));
        filterPanel.setBackground(new Color(152, 215, 209));
        buttonPanel.setBackground(new Color(152, 215, 209));
        dropBoxes.setBackground(new Color(152, 215, 209));
        enterButton.setBackground(new Color(152, 215, 209));
        header.setBackground(new Color(200,200,200));

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentList.super.innerPanel.remove(body);
                String m = month.getSelectedItem().toString();
                String y = year.getSelectedItem().toString();
                int year = Integer.parseInt(y);
                int month = monthsKey.get(m);
                ArrayList<String> appointments = db.appointmentsByMonthAndYear(DoctorID, month, year);
                populate(appointments);
                AppointmentList.super.mainFrame.revalidate();
                AppointmentList.super.mainFrame.repaint();
                db.addLog(Log.FILTER, (m + " " + y), DoctorID);
            }
        });
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentList.super.innerPanel.remove(body);
                ArrayList<String> appointments = db.listOfAppointments(DoctorID);
                populate(appointments);
                AppointmentList.super.mainFrame.revalidate();
                AppointmentList.super.mainFrame.repaint();
                db.addLog(Log.ALL_APPTS, null, DoctorID);
            }
        });
        today.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppointmentList.super.innerPanel.remove(body);
                ArrayList<String> appointments = db.todaysAppointments(DoctorID);
                populate(appointments);
                AppointmentList.super.mainFrame.revalidate();
                AppointmentList.super.mainFrame.repaint();
                db.addLog(Log.TODAY, null, DoctorID);
            }
        });

        dropBoxes.add(month);
        dropBoxes.add(year);
        enterButton.add(enter);
        filterPanel.add(dropBoxes);
        filterPanel.add(enterButton);
        buttonPanel.add(all);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(today);
        top.remove(logout);
        top.add(buttonPanel);
        top.add(filterPanel);
        top.add(logout, BorderLayout.CENTER);
        header.add(ApptID);
        header.add(ApptDate);
        header.add(ApptTime);
        header.add(RoomNumber);
        header.add(PatientFirstName);
        header.add(PatientSurname);
        header.add(viewDetails);
        header.add(editDetails);
        innerPanel.add(header);

        ArrayList<String> appointments = db.todaysAppointments(DoctorID);
        populate(appointments);

        main.add(tablePanel);
        mainFrame.setVisible(true);
    }

    /**
     * Populates the appointment list with appointments.
     */
    public void populate(ArrayList<String> appointments){
        //Remove old entries and create new body
        body = new JPanel();
        body.setBackground((new Color(255,255,255)));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        if(appointments != null){
            for(int i = 0; i < appointments.size(); i=i+6) {
                JPanel row = new JPanel();
                JLabel aID = new JLabel(appointments.get(i));
                JLabel aDate = new JLabel(appointments.get(i + 1));
                JLabel aTime = new JLabel(appointments.get(i + 2));
                JLabel rNum = new JLabel(appointments.get(i + 3));
                JLabel fName = new JLabel(appointments.get(i + 4));
                JLabel sName = new JLabel(appointments.get(i + 5));
                JButton viewAppointment = new JButton("View");
                JButton editAppointment = new JButton("Edit");

                row.setLayout(new GridLayout(1, 8));
                row.setPreferredSize(new Dimension(750, 20));
                aID.setHorizontalAlignment(JLabel.CENTER);
                aDate.setHorizontalAlignment(JLabel.CENTER);
                aTime.setHorizontalAlignment(JLabel.CENTER);
                rNum.setHorizontalAlignment(JLabel.CENTER);
                fName.setHorizontalAlignment(JLabel.CENTER);
                sName.setHorizontalAlignment(JLabel.CENTER);
                viewAppointment.setName(appointments.get(i));
                editAppointment.setName(appointments.get(i));

                row.add(aID);
                row.add(aDate);
                row.add(aTime);
                row.add(rNum);
                row.add(fName);
                row.add(sName);
                row.add(viewAppointment);
                row.add(editAppointment);
                body.add(row);

                viewAppointment.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AppointmentList.super.mainFrame.setVisible(false);
                        AppointmentID = editAppointment.getName();
                        db.addLog(Log.VIEW_APPT, AppointmentID, DoctorID);
                        ViewAppointment viewPanel = new ViewAppointment(mainFrame, DoctorID, AppointmentID, welPage);
                    }
                });
                editAppointment.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AppointmentList.super.mainFrame.setVisible(false);
                        AppointmentID = editAppointment.getName();
                        db.addLog(Log.EDIT_APPT, AppointmentID, DoctorID);
                        EditAppointments editPanel = new EditAppointments(mainFrame, DoctorID, AppointmentID, welPage);
                    }
                });
            }
            super.innerPanel.add(body);
        }
    }
}
