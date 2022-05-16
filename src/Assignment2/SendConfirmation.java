package Assignment2;

import java.util.ArrayList;
/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * SendConfirmation class:
 *
 *           Send confirmation messages
 *
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public class SendConfirmation {

    public SendConfirmation(){
    }
    /**
     * Send confirmation messages to doctors and patients depending on the circumstance.
     * @param messageType Enum of type Confirmation. this is the scenario in which the method has been called.
     * @param toPatientID PatientID of patient receiving the message.
     * @param toDoctorID DoctorID of doctor receiving the message.
     * @param toDoctorSurname Receiving doctors surname.
     * @param fromDoctorID DoctorID of doctor sending the message.
     */
    public static void sendConfirmationMessages(Confirmation messageType, String toPatientID, String toDoctorID, String toDoctorSurname, String fromDoctorID, Database db){
        long millis = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(millis);
        java.sql.Time sqlTime = new java.sql.Time(millis);

        ArrayList<String> queries = new ArrayList<>();

        switch (messageType){
            case NEW_DOCTOR:
                String new1 = String.format("INSERT INTO Messages (Message, MsgDate, MsgTime, ToPatient, ToDoctor, FromDoctor, MsgRead) VALUES('Assigned to Dr.%s', '%s', '%s', '%s', null, '%s', False)", toDoctorSurname, sqlDate, sqlTime, toPatientID, fromDoctorID);
                String new2 = String.format("INSERT INTO Messages (Message, MsgDate, MsgTime, ToPatient, ToDoctor, FromDoctor, MsgRead) VALUES('New patient %s', '%s', '%s', null, '%s', '%s', False)", toPatientID, sqlDate, sqlTime, toDoctorID, fromDoctorID);
                String new3 = String.format("INSERT INTO Messages (Message, MsgDate, MsgTime, ToPatient, ToDoctor, FromDoctor, MsgRead) VALUES('Assigned patient %s to Dr. %s', '%s', '%s', null, '%s', '%s', False)", toPatientID, toDoctorSurname, sqlDate, sqlTime, fromDoctorID, fromDoctorID);
                queries.add(new1);
                queries.add(new2);
                queries.add(new3);
                break;
            case UPDATE_ENTRY:
                String update1 = String.format("INSERT INTO Messages (Message, MsgDate, MsgTime, ToPatient, ToDoctor, FromDoctor, MsgRead) VALUES('Updated previous appointment details', '%s', '%s', '%s', null, '%s', False)", sqlDate, sqlTime, toPatientID, fromDoctorID);
                String update2 = String.format("INSERT INTO Messages (Message, MsgDate, MsgTime, ToPatient, ToDoctor, FromDoctor, MsgRead) VALUES('Updated previous appointment details for %s', '%s', '%s', null, '%s', '%s', False)", toPatientID, sqlDate, sqlTime, fromDoctorID, fromDoctorID);
                queries.add(update1);
                queries.add(update2);
                break;
            case NEW_ENTRY:
                String entry1 = String.format("INSERT INTO Messages (Message, MsgDate, MsgTime, ToPatient, ToDoctor, FromDoctor, MsgRead) VALUES('New appointment details received', '%s', '%s', '%s', null, '%s', False)", sqlDate, sqlTime, toPatientID, fromDoctorID);
                String entry2 = String.format("INSERT INTO Messages (Message, MsgDate, MsgTime, ToPatient, ToDoctor, FromDoctor, MsgRead) VALUES('Added appointment details for %s', '%s', '%s', null, '%s', '%s', False)", toPatientID, sqlDate, sqlTime, fromDoctorID, fromDoctorID);
                queries.add(entry1);
                queries.add(entry2);
                break;
        }
        db.confirmationMessages(queries);
    }
}
