package model;

/**
 * Appointment class Appointment.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @vesion 2
 */

import java.time.LocalDateTime;

/** class that creates the Appointment object. */
public class Appointment {
    /** appointmentId value */
    private int appointmentId;
    /** title value */
    private String title;
    /** description value */
    private String description;
    /** location value */
    private String location;
    /** type value */
    private String type;
    /** contact value */
    private String contact;
    /** startTime value */
    private LocalDateTime startTime;
    /** endTime value */
    private LocalDateTime endTime;
    /** customerId value */
    private int customerId;
    /** userId value */
    private int userId;
    /** contactId value */
    private int contactId;

    /** Appointment class constructor
     * @param appointmentId the appointment ID
     * @param title the appointment title
     * @param description a description of the appointment
     * @param location where the appointment is to happen
     * @param type what type of appointment is being held
     * @param contact who the contact is
     * @param startTime what time the appointment starts
     * @param endTime what time the appointment ends
     * @param customerId the ID of the customer attending the appointment
     * @param userId ID of the user controlling the appointment
     * @param contactId the ID of the contact */
    public Appointment(int appointmentId, String title, String description, String location, String type, String contact, LocalDateTime startTime, LocalDateTime endTime, int customerId, int userId, int contactId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.contact = contact;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** getAppointmentId method
     @return returns the appointment Id */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** getTitle method
     @return returns the title */
    public String getTitle() {
        return title;
    }

    /** getDescription method
     @return returns the description */
    public String getDescription() {
        return description;
    }

    /** getLocation method
     @return returns the location */
    public String getLocation() {
        return location;
    }

    /** getType method
     @return returns the type */
    public String getType() {
        return type;
    }

    /** getContact (name) method
     @return returns the contact */
    public String getContact() {
        return contact;
    }

    /** getStartTime method
     @return returns the start time */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** getEndTime method
     @return returns the end time */
    public LocalDateTime getEndTime() {return endTime;}

    /** getCustomerId method
     @return returns the customer Id */
    public int getCustomerId() {return customerId; }

    /** getUserId method
     @return returns the user Id */
    public int getUserId() {
        return userId;
    }
}
