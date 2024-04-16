package DAO;

/**
 * ContactsDAO class ContactsDAO.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** class that contains the Contacts database queries. */
public class ContactsDAO {

    /** Database query method
     @return queries the database and returns a list of contacts */
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactId =rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts createContacts = new Contacts(contactId, contactName, email);
                allContacts.add(createContacts);
            }
        }

        /** @throws SQLException throws a SQL exception. */
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    }
}
