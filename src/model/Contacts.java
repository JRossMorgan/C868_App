package model;

/**
 * Contacts class Contacts.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

/** class that creates the Contacts object. */
public class Contacts {
    /** Contact ID value */
    private int contactId;
    /** Contact name value */
    private String contactName;
    /** Contact email value */
    private String email;

    /** Contacts class constructor
     * @param contactId ID of the contact
     * @param contactName name of the contact
     * @param email the contacts email. */
    public Contacts(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /** getContactId method
     @return returns the contact Id */
    public int getContactId(){return contactId;}

    /** getContactName method
     @return returns the contact name */
    public String getContactName() {
        return contactName;
    }

    /** getEmail method
     @return returns the contact email */
    public String getEmail() {
        return email;
    }

    @Override

    /**
     @return overrides the toString method and returns the contact name */
    public String toString(){
        return contactName;
    }
}
