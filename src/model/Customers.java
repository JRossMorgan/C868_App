package model;

/**
 * Customers class Customers.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */


import java.time.LocalDateTime;

/** class that creates the Customers object. */
public class Customers {
    /** customer ID value */
    private int customerId;
    /** customer name value */
    private String name;
    /** customer address value */
    private String address;
    /** customer postal code value */
    private String postalCode;
    /** customer phone number value */
    private String phone;
    /** customer created date value */
    private LocalDateTime createDate;
    /** customer division value */
    private String division;
    /** customer country value */
    private String country;

    /** Customer class constructor
     * @param customerId customer ID
     * @param name customer name
     * @param address customer's address
     * @param postalCode customer's postal code
     * @param phone customer's phone number
     * @param createDate date customer's profile was created
     * @param division customer's division
     * @param country customer's country */
    public Customers (int customerId, String name, String address, String postalCode, String phone, LocalDateTime createDate, String division, String country){
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.division = division;
        this.country = country;
    }

    /** getCustomerId method
     @return returns the customer Id */
    public int getCustomerId() {
        return customerId;
    }

    /** getName method
     @return returns the customer name */
    public String getName() {
        return name;
    }

    /** getAddress method
     @return returns the customer address */
    public String getAddress() {
        return address;
    }

    /** getPostalCode method
     @return returns the customer postal code */
    public String getPostalCode() {
        return postalCode;
    }

    /** getPhone method
     @return returns the customer phone */
    public String getPhone() {
        return phone;
    }

    /** getCreateDate method
     @return returns the create date */
    public LocalDateTime getCreateDate() {return createDate;}

    /** getDivision method
     @return returns the customer division */
    public String getDivision() {
        return division;
    }

    /** getCountry method
     @return returns the customer country */
    public String getCountry(){
        return country;
    }

    @Override
    /**
     @return overrides the toString method and returns the customer Id as a string */
    public String toString(){
        return String.valueOf(customerId);
    }
}
