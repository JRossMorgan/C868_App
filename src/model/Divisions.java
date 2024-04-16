package model;

/**
 * Divisions class Divisions.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

/** class that creates the Divisions object. */
public class Divisions {
    /** Division ID value */
    public int divisionId;
    /** Division Name value*/
    public String name;
    /** Division's country ID value */
    public int countryId;

    /** Divisions class constructor
     * @param divisionId division ID
     * @param name Division's name
     * @param countryId Division's country ID */
    public Divisions(int divisionId, String name, int countryId){
        this.divisionId = divisionId;
        this.name = name;
        this.countryId = countryId;
    }

    /** getDivisionId method
     @return returns the division Id */
    public int getDivisionId() {
        return divisionId;
    }

    /** getName method
     @return returns the division name */
    public String getName() {
        return name;
    }

    /** getCountryId method
     @return returns the division's country Id */
    public int getCountryId() {
        return countryId;
    }

    @Override
    /**
     @return overrides the toString method and returns the division name */
    public String toString(){
        return name;
    }
}
