package DAO;

/**
 * CountryDAO class CountryDAO.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** class that contains the Country database queries. */
public class CountryDAO {

    /** Database query method
     @return queries the database and returns countries in a list */
    public static ObservableList<Country> getCountries(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM countries";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Country createCountry = new Country(countryId, name);
                allCountries.add(createCountry);
            }
        }
        /** @throws SQLException throws a SQL exception.*/
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return allCountries;
    }
}
