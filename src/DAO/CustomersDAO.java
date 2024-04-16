package DAO;

/**
 * CustomersDAO class CustomersDAO.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** class that contains the Customers database queries. */
public class CustomersDAO {

    /** Database query method
     @return queries the database and returns customers in a list */
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
        String sql = "select Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Create_Date, Division, Country from customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime createDate= rs.getTimestamp("Create_Date").toLocalDateTime();
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                Customers createCustomer = new Customers(customerId, name, address, postalCode, phone, createDate, division, country);
                allCustomers.add(createCustomer);
            }
        }
        /** @throws SQLException throws a SQL exception.*/
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return allCustomers;
    }

    /** insertCustomer method
     @param custName inserts customer name
     @param address inserts customer address
     @param postalCode inserts customer postal code
     @param phone inserts customer phone number
     @param timestamp inserts customer create date
     @param divisionId inserts customer division Id. */

    public static void insertCustomer(String custName, String address, String postalCode, String phone, Timestamp timestamp, int divisionId){
        String sql = "insert into customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Division_ID) values (?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, custName);
            ps.setString(2,address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, divisionId);
            ps.executeUpdate();

        }
        /** @throws SQLException throws a SQL exception.*/
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /** updateCustomer method
     * @param customerId updates customer Id
    @param custName updates customer name
    @param address updates customer address
    @param postalCode upodates customer postal code
    @param phone updates customer phone number
    @param created updates customer create date
    @param divisionId updates customer division Id. */
    public static void updateCustomer(int customerId, String custName, String address, String postalCode, String phone, Timestamp created, int divisionId){
        String sql ="update customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Division_ID = ? where Customer_ID = ?";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, custName);
            ps.setString(2,address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT"))));
            ps.setInt(6, divisionId);
            ps.setInt(7, customerId);
            ps.executeUpdate();
        }
        /** @throws SQLException throws a SQL exception.*/
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
    /** deleteCustomer method
     * @param custId method to delete a customer. */
    public static void deleteCustomer(int custId){
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, custId);
            ps.executeUpdate();
        }
        /** @throws SQLException throws a SQL exception.*/
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
