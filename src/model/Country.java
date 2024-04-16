package model;

/**
 * Country class Country.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

/** class that creates the Country object. */
public class Country {
    /** countryId value */
    public int countryId;
    /** country name value */
    public String name;

    /** Country class constructor
     * @param countryId ID of the country
     * @param name name of the country. */
    public Country (int countryId, String name){
        this.countryId = countryId;
        this.name = name;
    }

    /** getCountryId method that returns the country ID
     @return returns the country Id */
    public int getCountryId() {
        return countryId;
    }

    /** getName method that returns the name
     @return returns the country name */
    public String getName() {
        return name;
    }
    @Override
    /** method to override toString for the combo boxes
     @return overrides the toString method and returns the country name */
    public String toString(){
        return name;
    }
}
