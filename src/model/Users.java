package model;

/**
 * Users class Users.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

/** class that creates the Users object. */
public class Users {
    /** User ID value*/
    private int userId;
    /** user's name value */
    private String userName;
    /** user's password */
    private String password;

    /** Users class constructor
     * @param userId user's ID
     * @param userName user's username
     * @param password user's password */
    public Users(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** getUserId method
     @return returns the user Id */
    public int getUserId(){return userId;}

    /** getUserName method
     @return returns the user name */
    public String getUserName() {
        return userName;
    }

    /** getPassword method
     @return returns the division Id */
    public String getPassword() {
        return password;
    }

    @Override
    /**
     * @return overrides the toString method and returns the user Id as a string */
    public String toString(){
        return String.valueOf(userId);
    }

}
