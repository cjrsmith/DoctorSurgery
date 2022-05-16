package Assignment2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * Database class:
 * Stores methods for SQL statements from DB.
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class Database
{
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * Connection established on construction of database class.
     */
    public Database(){
        getConnection();
    }

    /**
     * Establish a connection to the database.
     */
    public void getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/group4b?user=group4b&password=group4b");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an arraylist of patient summary information in the format [PatientID, FirstName, Surname, Number, PatientID, FirstName, Surname, Number,...] for the given doctor.
     * @param doctorID, ID of authenticated doctor.
     * @return ArrayList of names, numbers of patients.
     */
    public ArrayList<String> getNamesAndNumbers(String doctorID){
        ArrayList<String> patientSummary = new ArrayList<>();
        try{
            String query = String.format("select PatientID, PatientFirstName, PatientSurname, PatientPhoneNo from Patient where DoctorID = '%s';", doctorID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                patientSummary.add(resultSet.getString("PatientID"));
                patientSummary.add(resultSet.getString("PatientFirstName"));
                patientSummary.add(resultSet.getString("PatientSurname"));
                patientSummary.add(resultSet.getString("PatientPhoneNo"));
            }
            return patientSummary;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Returns the doctorID if the given username and password is correct.
     * @param username Doctor's system username.
     * @param password Doctor's system password.
     * @return doctorID if valid or invalid message if not.
     */
    public String authenticate(String username, String password){
        try{
            String query = String.format("select DrUsername, DrPassword from Login where DrUsername = '%s';",username);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            String pWord = resultSet.getString("DrPassword");
            if(pWord.equals(password)){
                String q2 = String.format("select DoctorID from Doctor where DrUsername = '%s';",username);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(q2);
                resultSet.next();
                return resultSet.getString("DoctorID");
            }
            else{
                return "Invalid";
            }
        }
        catch(Exception e){
            return "Invalid";
        }
    }

    /**
     * Returns doctors surname.
     * @param doctorID Doctor's unique ID.
     * @return doctors surname.
     */
    public String getDoctor(String doctorID){
        try{
            String query = String.format("select DoctorSurname from Doctor where DoctorID = '%s';",doctorID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getString("DoctorSurname");
        }
        catch(Exception e){
            return "Invalid";
        }
    }

    /**
     * Retrieves any un-read messages sent to the doctor provided.
     * @param doctorID Doctor's unique ID.
     * @return ArrayList of MsgID, From, Message, Date, Time
     */
    public ArrayList<String> getDoctorMessages(String doctorID){
        ArrayList<String> messages = new ArrayList<>();
        try{
            String query = String.format("select MsgID, FromDoctor, Message, MsgDate, MsgTime from Messages where ToDoctor = '%s' and MsgRead = '0' order by MsgDate DESC, MsgTime DESC;", doctorID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                messages.add(resultSet.getString("MsgID"));
                messages.add(resultSet.getString("FromDoctor"));
                messages.add(resultSet.getString("Message"));
                messages.add(resultSet.getString("MsgDate"));
                messages.add(resultSet.getString("MsgTime"));
            }
            return messages;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Update an entry of the Patient table.
     * @param query
     * @return
     */
    public String update(String query){
        try{
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return "Updated successfully";
        }
        catch(Exception e){
            return "Failed";
        }
    }

    /**
     * Sets the given message to true if it has been read.
     * @param MsgID The message ID for the given message
     * @return A success message or a failed message.
     */
    public String readMessage(String MsgID){
        try{
            String query = String.format("update Messages set MsgRead = True where MsgID = '%s';", MsgID);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return "Message read";
        }
        catch(Exception e){
            return "Message ID doesn't exist";
        }
    }

    /**
     * Returns an arraylist of patient information in the format [PatientID, FirstName, Surname, Number, PatientID, FirstName, Surname, Number,...] for the given doctor.
     * @param patientID, ID of authenticated doctor.
     * @return ArrayList of names, numbers of patients.
     */
    public ArrayList<String> getPatientInfo(String patientID){
        ArrayList<String> getPatientInfo = new ArrayList<>();
        try{
            String query = String.format("select p.PatientID, p.PatientFirstName, p.PatientSurname, p.PatientGender, p.PatientDoB, p.PatientPhoneNo, p.DoctorID, d.DoctorFirstName, d.DoctorSurname from Patient p, Doctor d where p.PatientID = '%s' and d.DoctorID = p.DoctorID;", patientID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                getPatientInfo.add(resultSet.getString("PatientID"));
                getPatientInfo.add(resultSet.getString("PatientFirstName"));
                getPatientInfo.add(resultSet.getString("PatientSurname"));
                getPatientInfo.add(resultSet.getString("PatientGender"));
                getPatientInfo.add(resultSet.getString("PatientDoB"));
                getPatientInfo.add(resultSet.getString("PatientPhoneNo"));
                getPatientInfo.add(resultSet.getString("DoctorID"));
                getPatientInfo.add(resultSet.getString("DoctorFirstName"));
                getPatientInfo.add(resultSet.getString("DoctorSurname"));
            }
            return getPatientInfo;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Get a list of all doctors.
     * @return
     */
    public ArrayList<String> getDoctorList(){
        ArrayList<String> doctors = new ArrayList<>();
        try{
            String query = String.format("select DoctorID, DoctorFirstName, DoctorSurname, RoomNumber from Doctor;");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                doctors.add(resultSet.getString("DoctorID"));
                doctors.add(resultSet.getString("DoctorFirstName"));
                doctors.add(resultSet.getString("DoctorSurname"));
                doctors.add(resultSet.getString("RoomNumber"));
            }
            return doctors;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Loop through a given list of queries and execute one after the other, updating the Messages table with new messages.
     * @param queries
     */
    public void confirmationMessages(ArrayList<String> queries){
        try{
            for(String q : queries){
                statement = connection.createStatement();
                statement.executeUpdate(q);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get a list of all appointments for the given doctor.
     * @param doctorID
     */
    public ArrayList<String> listOfAppointments(String doctorID){
        ArrayList<String> appointments = new ArrayList<>();
        try{
            String query = String.format("select a.ApptID, a.ApptDate, a.ApptTime, a.RoomNumber, p.PatientFirstName, p.PatientSurname from Appointment a, Patient p where a.DoctorID = '%s' and a.PatientID = p.PatientID order by a.ApptDate, a.ApptTime;", doctorID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                appointments.add(resultSet.getString("ApptID"));
                appointments.add(resultSet.getString("ApptDate"));
                appointments.add(resultSet.getString("ApptTime"));
                appointments.add(resultSet.getString("RoomNumber"));
                appointments.add(resultSet.getString("PatientFirstName"));
                appointments.add(resultSet.getString("PatientSurname"));
            }
            return appointments;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Get an arraylist of appointments for a given doctor filtered by month and year.
     * @param doctorID
     * @param month
     * @param year
     * @return List of string values with information of appointment.
     */
    public ArrayList<String> appointmentsByMonthAndYear(String doctorID, int month, int year){
        String m = Integer.toString(month);
        String y = Integer.toString(year);
        ArrayList<String> appointments = new ArrayList<>();
        try{
            String query = String.format("select a.ApptID, a.ApptDate, a.ApptTime, a.RoomNumber, p.PatientFirstName, p.PatientSurname from Appointment a, Patient p where a.DoctorID = '%s' and a.PatientID = p.PatientID and year(a.ApptDate) = %s and month(a.ApptDate) = %s order by a.ApptTime;", doctorID, y, m);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                appointments.add(resultSet.getString("ApptID"));
                appointments.add(resultSet.getString("ApptDate"));
                appointments.add(resultSet.getString("ApptTime"));
                appointments.add(resultSet.getString("RoomNumber"));
                appointments.add(resultSet.getString("PatientFirstName"));
                appointments.add(resultSet.getString("PatientSurname"));
            }
            return appointments;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Return a list of todays appointments for a given doctor.
     * @param doctorID
     * @return
     */
    public ArrayList<String> todaysAppointments(String doctorID){
        ArrayList<String> appointments = new ArrayList<>();
        try{
            String query = String.format("select a.ApptID, a.ApptDate, a.ApptTime, a.RoomNumber, p.PatientFirstName, p.PatientSurname from Appointment a, Patient p where a.DoctorID = '%s' and a.PatientID = p.PatientID  and a.ApptDate = curdate() order by a.ApptTime;", doctorID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                appointments.add(resultSet.getString("ApptID"));
                appointments.add(resultSet.getString("ApptDate"));
                appointments.add(resultSet.getString("ApptTime"));
                appointments.add(resultSet.getString("RoomNumber"));
                appointments.add(resultSet.getString("PatientFirstName"));
                appointments.add(resultSet.getString("PatientSurname"));
            }
            return appointments;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Returns all the information of an appointment.
     * Will return NULL values if appointment details and prescription details are null.
     * @param apptID
     * @return
     */
    public ArrayList<String> getAppointmentDetails(String apptID){
        ArrayList<String> appointments = new ArrayList<>();
        try{
            String query = String.format("select t.ApptID, t.ApptDate, t.ApptTime, t.RoomNumber, t.ApptDetails, t.PatientID, t.PatientFirstName, t.PatientSurname, pr.PrescriptID, pr.PrescriptDetails from (select a.ApptID, a.ApptDate, a.ApptTime, a.RoomNumber, a.ApptDetails, a.PrescriptID, a.PatientID, p.PatientFirstName, p.PatientSurname from Appointment a, Patient p where a.PatientID = p.PatientID and a.ApptID = '%s') as t left join Prescription pr on t.PrescriptID = pr.PrescriptID;", apptID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                appointments.add(resultSet.getString("ApptID"));
                appointments.add(resultSet.getString("ApptDate"));
                appointments.add(resultSet.getString("ApptTime"));
                appointments.add(resultSet.getString("RoomNumber"));
                appointments.add(resultSet.getString("ApptDetails"));
                appointments.add(resultSet.getString("PrescriptID"));
                appointments.add(resultSet.getString("PrescriptDetails"));
                appointments.add(resultSet.getString("PatientID"));
                appointments.add(resultSet.getString("PatientFirstName"));
                appointments.add(resultSet.getString("PatientSurname"));
            }
            return appointments;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Gets the latest entry into the prescription table.
     * @return
     */
    public String getLastPrescriptionID(){
        try{
            String query = "SELECT PrescriptID FROM Prescription ORDER BY PrescriptID DESC LIMIT 1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            String id = resultSet.getString("PrescriptID");
            return id;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Add a log to the log table
     * @param logType type of log entry.
     * @param logDetails extra details for the log entry (can be null).
     * @param doctorID doctor log is associated with (can be null).
     */
    public void addLog(Log logType, String logDetails, String doctorID){
        String query = "";
        if(logDetails == null & doctorID == null){
            query = String.format("insert into Logs (LogType, LogDetails, DoctorID) values ('%s', null, null);", logType.type);
        }
        else if(logDetails == null){
            query = String.format("insert into Logs (LogType, LogDetails, DoctorID) values ('%s', null, '%s');", logType.type, doctorID);
        }
        else if(doctorID == null){
            query = String.format("insert into Logs (LogType, LogDetails, DoctorID) values ('%s', '%s', null);", logType.type, logDetails);
        }
        else{
            query = String.format("insert into Logs (LogType, LogDetails, DoctorID) values ('%s', '%s', '%s');", logType.type, logDetails, doctorID);
        }
        try{
            statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Close the connection to the server.
     */
    public void close(){
        try{
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Return all the logs from the database as strings in an array.
     * @return
     */
    public ArrayList<String> getLogs(){
        ArrayList<String> logs = new ArrayList<>();
        try{
            String query = "select * from Logs;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                logs.add(resultSet.getString("LogID"));
                logs.add(resultSet.getString("LogType"));
                logs.add(resultSet.getString("LogDetails"));
                logs.add(resultSet.getString("DoctorID"));
            }
            return logs;
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Return all the messages from the database as strings in an array.
     * @return
     */
    public ArrayList<String> getMessages(){
        ArrayList<String> messages = new ArrayList<>();
        try{
            String query = "select * from Messages;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                messages.add(resultSet.getString("MsgID"));
                messages.add(resultSet.getString("Message"));
                messages.add(resultSet.getString("MsgDate"));
                messages.add(resultSet.getString("MsgTime"));
                messages.add(resultSet.getString("ToPatient"));
                messages.add(resultSet.getString("ToDoctor"));
                messages.add(resultSet.getString("FromDoctor"));
                messages.add(resultSet.getString("MsgRead"));
            }
            return messages;
        }
        catch(Exception e){
            return null;
        }
    }
}