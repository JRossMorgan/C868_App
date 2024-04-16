package controller;

/**
 * AddAppointmentController class AddAppointmentController.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/** Class that controls the Add Appointment FXML page. */
public class AddAppointmentController implements Initializable {
    public TextField addTitle;
    public TextField addDescription;
    public TextField addLocation;
    public TextField addType;
    public ComboBox <Contacts> addContact;
    public ComboBox <LocalTime> addStart;
    public ComboBox <LocalTime> addEnd;
    public DatePicker addDate;
    public Button addSave;
    public Button addCancel;
    public DialogPane addAppDialog;
    public ComboBox <Customers> addCustomer;
    public ComboBox <Users> addUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addContact.setItems(ContactsDAO.getAllContacts());
        addCustomer.setItems(CustomersDAO.getAllCustomers());
        addUser.setItems(UsersDAO.getAllUsers());
    }

    /** @param actionEvent method to save the appointment.
     * @throws IOException navigates to the main page. */
    public void onAddSave(ActionEvent actionEvent) throws IOException{

        /** @return method that gets the title from the field and returns an error message if it's blank. */
        String title = addTitle.getText();
        if(title.isBlank()){
            addAppDialog.setContentText("Please Enter a Title");
            return;
        }

        /** @return method that gets the description from the field and returns an error message if it's blank. */
        String description= addDescription.getText();
        if(description.isBlank()){
            addAppDialog.setContentText("Please Enter a Description");
            return;
        }

        /** @return method that gets the location from the field and returns an error message if it's blank. */
        String location = addLocation.getText();
        if(location.isBlank()){
            addAppDialog.setContentText("Please Enter a Location");
            return;
        }

        /** @return method that gets the type from the field and returns an error message if it's blank. */
        String type =  addType.getText();
        if(type.isBlank()){
            addAppDialog.setContentText("Please Enter a Type");
            return;
        }

        /** @return method that gets the Customer ID from the field and returns an error message if it's blank.
         * @throws a number format exception. */
        int customerID = 0;
        try{
            Customers CU = addCustomer.getSelectionModel().getSelectedItem();
            if(CU == null){
                addAppDialog.setContentText("Please Select a Customer");
                return;
            }
            else{
                customerID = CU.getCustomerId();
            }
        }
        catch(NumberFormatException e){
           addAppDialog.setContentText("Error");
        }

        /** @return method that gets the User ID from the field and returns an error message if it's blank.
         * @throws a number format exception. */
        int userId = 0;
        try{
            Users US = addUser.getSelectionModel().getSelectedItem();
            if(US == null){
                addAppDialog.setContentText("Please Select a User");
                return;
            }
            else{
                userId = US.getUserId();
            }
        }
        catch (NumberFormatException e){
            addAppDialog.setContentText("Error");
        }

        /** @return selects the contact from a combo box. */
        String contact;
        Contacts PQ = (Contacts) addContact.getSelectionModel().getSelectedItem();
        if(PQ == null){
            addAppDialog.setContentText("Please Select a Contact");
            return;
        }
        else{
            contact = PQ.getContactName();
        }

        /** @return gets the contact ID based on the selected contact name. */
        int contactId = 0;
        for(Contacts contacts : ContactsDAO.getAllContacts()){
            if(contacts.getContactName().contentEquals(contact)){
                contactId = contacts.getContactId();
            }
        }

        /** @return sets the date from the date picker. */
        LocalDate appDate = addDate.getValue();
        if(appDate == null){
            addAppDialog.setContentText("Please Choose a Date");
            return;
        }

        /** @return sets the start time from the combo box of time values. */
        LocalTime SP = addStart.getSelectionModel().getSelectedItem();
        if(SP == null){
            addAppDialog.setContentText("Please Select an Appointment Start Time");
            return;
        }

        /** @return sets the start time from the combo box of time values. */
        LocalTime EZ = addEnd.getSelectionModel().getSelectedItem();
        if(EZ == null){
            addAppDialog.setContentText("Please Select an Appointment end time");
            return;
        }

        /** assembles a LocalDateTime from the date and time. */
        LocalDateTime appointmentStart = LocalDateTime.of(addDate.getValue(), SP);
        LocalDateTime appointmentEnd = LocalDateTime.of(addDate.getValue(), EZ);

        /** @return validates that the start time is before the end time. */
        if(appointmentStart.isAfter(appointmentEnd)){
            addAppDialog.setContentText("Appointment Start time must be before the end time.");
            return;
        }
        /** @return validates that the end time is after the start time. */
        if(appointmentEnd.isBefore(appointmentStart)){
            addAppDialog.setContentText("Appointment End time must be after the end time.");
            return;
        }

        /** @return checks if the customer has an overlapping appointment. */
        for(Appointment eA : AppointmentDAO.getAppointments()){
            if(customerID == eA.getCustomerId()){
                if((appointmentStart.isBefore(eA.getStartTime()) || appointmentStart.isEqual(eA.getStartTime())) && appointmentEnd.isAfter(eA.getStartTime())){
                    addAppDialog.setContentText("This Customer Already has an appointment scheduled for this time. Please choose a different time.");
                    return;
                }
                else if((appointmentStart.isAfter(eA.getStartTime()) || appointmentStart.isEqual(eA.getStartTime())) && appointmentStart.isBefore(eA.getEndTime())){
                    addAppDialog.setContentText("This Customer Already has an appointment scheduled for this time. Please choose a different time.");
                    return;
                }
                else if((appointmentStart.isBefore(eA.getStartTime()) || appointmentStart.isEqual(eA.getStartTime())) && (appointmentEnd.isAfter(eA.getEndTime()) || appointmentEnd.isEqual(eA.getEndTime()))){
                    addAppDialog.setContentText("This Customer Already has an appointment scheduled for this time. Please choose a different time.");
                    return;
                }
            }
        }
        /** calls the insertAppointment method with provided parameters.
         * @see java.AppointmentDAO */
        AppointmentDAO.insertAppointment(title, description, location, type, Timestamp.valueOf(appointmentStart), Timestamp.valueOf(appointmentEnd), customerID, userId, contactId);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** @throws IOException throws an exception as it navigates to the main page without saving.
     * @param actionEvent cancels without saving. */
    public void onAddCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler for setting the time selection combo boxes */
    public void onSelect(ActionEvent actionEvent) {
        ZonedDateTime s = ZonedDateTime.of(addDate.getValue(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime e = ZonedDateTime.of(addDate.getValue(), LocalTime.of(21, 30), ZoneId.of("America/New_York"));

        ZonedDateTime zonedS = s.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime zonedE = e.withZoneSameInstant(ZoneId.systemDefault());

        LocalTime localS = zonedS.toLocalTime();
        LocalTime localE = zonedE.toLocalTime();
        while(localS.isBefore(localE.plusSeconds(1))){
            addStart.getItems().add(localS);
            localS = localS.plusMinutes(30);
        }

        ZonedDateTime t = ZonedDateTime.of(addDate.getValue(), LocalTime.of(8, 30), ZoneId.of("America/New_York"));
        ZonedDateTime l = ZonedDateTime.of(addDate.getValue(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));

        ZonedDateTime zonedT = t.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime zonedL = l.withZoneSameInstant(ZoneId.systemDefault());

        LocalTime localT = zonedT.toLocalTime();
        LocalTime localL = zonedL.toLocalTime();
        while(localT.isBefore(localL.plusSeconds(1))){
            addEnd.getItems().add(localT);
            localT = localT.plusMinutes(30);
        }
    }
}
