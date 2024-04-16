package DAO;

/**
 * AppointmentDAO class AppointmentDAO.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** class that contains the Appointment database queries. */
public class AppointmentDAO {
    /** Database query method
     @return queries the database and returns appointments in a list. */
    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Contact_Name, Start, End, Customer_ID, User_ID, appointments.Contact_ID FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String contact = rs.getString("Contact_Name");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment createAppointment = new Appointment(appointmentId, title, description, location, type, contact, start, end, customerId, userId, contactId);
                allAppointments.add(createAppointment);

            }
            /** @throws SQLException throws a SQL exception.*/
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointments;
    }

    /** insertAppointment method
     @param title inserts a new title
     @param description inserts a new description
     @param location inserts a new location
     @param type inserts a new type
     @param start inserts a new start time
     @param end inserts a new end time
     @param customerId inserts a new customer Id
     @param userId inserts a new user Id
     @param contactId inserts a new contact Id*/
    public static void insertAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId){
        String sql = "insert into appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.executeUpdate();

        }
        /** @throws SQLException throws a SQL exception.*/
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

    }

    /** updateAppointment method
     * @param id updates Id
     @param title updates a new title
     @param description updates a new description
     @param location updates a new location
     @param type updates a new type
     @param startTime updates a new start time
     @param endTime updates a new end time
     @param customerId updates a new customer Id
     @param userId updates a new user Id
     @param contactId updates a new contact Id*/
    public static void updateAppointment(int id, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, int customerId, int userId, int contactId){
        String sql = "update appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? where Appointment_ID = ?";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1,title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, startTime);
            ps.setTimestamp(6, endTime);
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10, id);

            ps.executeUpdate();
        }
        /** @throws SQLException throws a SQL exception. */
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /** deleteAppointment method
     * @param id method to delete appointments. */
    public static void deleteAppointment(int id){
        String sql = "delete from Appointments where Appointment_ID = ?";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        /** @throws SQLException throws a SQL exception. */
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}

