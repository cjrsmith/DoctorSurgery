package Assignment2;
/**
 * CO559 - Assignment 2 - Group 4B:
 * -------------------------------------------------
 * Enum class:
 * Contains different log types used in situations for logging user interaction.
 * -------------------------------------------------
 * @authors Connor (cs878), James (jv264), Kervin (kjb48), Matt (mc974)
 * @version 5 - 26/03/2021
 */
public enum Log {
    LOGGED_IN("Login successful"),
    LOG_IN_ATTEMPT("Unsuccessful login attempt"),
    LOGOUT("User logged out successfully"),
    BACK("Back button clicked"),
    CONFIRM("Confirm changes button clicked"),
    READ("Mark message as read"),
    PATIENTS("View list of patients"),
    VIEW_PATIENT("View patient details"),
    DOCTORS("View list of doctors"),
    SELECT_DOCTOR("Doctor selected"),
    NEW_DOCTOR("Changed doctor"),
    APPOINTMENTS("View appointments"),
    FILTER("Filter results"),
    TODAY("View todays appointments"), //DO NOT CORRECT PUNCTUATION!!
    ALL_APPTS("View all appointments"),
    VIEW_APPT("View appointment details"),
    EDIT_APPT("Edit appointment details"),
    ADD_PRES_DET("Added prescription details"),
    EDIT_APPT_DET("Edited appointment details"),
    EDIT_PRES_DET("Edited prescription details");

    public final String type;

    Log(String type){
        this.type = type;
    }
}
