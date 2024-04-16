package controller;

/**
 * UpdateAppointmentController class UpdateAppointmentController.java
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

/** Class that controls the Update Appointment FXML page. */
public class UpdateAppointmentController implements Initializable {
    public TextField updateTitle;
    public TextField updateDescription;
    public TextField updateLocation;
    public TextField updateType;
    public ComboBox <Customers> updateCustomer;
    public ComboBox <Users> updateUser;
    public TextField updateId;
    public ComboBox <Contacts> updateContact;
    public ComboBox <LocalTime> updateStart;
    public ComboBox <LocalTime> updateEnd;
    public DatePicker updateDate;
    public Button updateSave;
    public Button updateCancel;
    public DialogPane updateDialog;

    /**
     @param appointmentToUpdate sets appointment from the appointment page */
    public void updateAppointment(Appointment appointmentToUpdate){
        updateId.setText(String.valueOf(appointmentToUpdate.getAppointmentId()));
        updateTitle.setText(appointmentToUpdate.getTitle());
        updateDescription.setText(appointmentToUpdate.getDescription());
        updateLocation.setText(appointmentToUpdate.getLocation());
        updateType.setText(appointmentToUpdate.getType());
        for(Customers customerToUpdate : CustomersDAO.getAllCustomers()){
            if(customerToUpdate.getCustomerId() == appointmentToUpdate.getCustomerId()){
                updateCustomer.setValue(customerToUpdate);
            }
        }
        for(Users userToUpdate : UsersDAO.getAllUsers()){
            if(userToUpdate.getUserId() == appointmentToUpdate.getUserId()){
                updateUser.setValue(userToUpdate);
            }
        }
        for(Contacts contactToUpdate : ContactsDAO.getAllContacts()){
            if(contactToUpdate.getContactName().contentEquals(appointmentToUpdate.getContact())){
                updateContact.setValue(contactToUpdate);
            }
        }
        updateDate.setValue(appointmentToUpdate.getStartTime().toLocalDate());
        updateStart.setValue(appointmentToUpdate.getStartTime().toLocalTime());
        updateEnd.setValue(appointmentToUpdate.getEndTime().toLocalTime());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateContact.setItems(ContactsDAO.getAllContacts());
        updateCustomer.setItems(CustomersDAO.getAllCustomers());
        updateUser.setItems(UsersDAO.getAllUsers());

    }

    /**
     @param actionEvent the event handler that saves changes and navigates back to the appointment page.
    * @throws IOException throws an exception*/
    public void onUpdateSave(ActionEvent actionEvent) throws IOException{
        /** @return method that gets the Appointment ID from the field and returns an error message if it's blank.
         * @throws a number format exception. */
        int updatedId = 0;
        try{
            updatedId = Integer.parseInt(updateId.getText());
        }
        catch(NumberFormatException e){
            updateDialog.setContentText("It's automatically filled, so this shouldn't appear.");
            return;
        }

        /** @return method that gets the title from the field and returns an error message if it's blank. */
        String title = updateTitle.getText();
        if(title == null){
            updateDialog.setContentText("Please enter a title.");
            return;
        }

        /** @return method that gets the description from the field and returns an error message if it's blank. */
        String description = updateDescription.getText();
        if(description == null){
            updateDialog.setContentText("Please enter a description.");
            return;
        }

        /** @return method that gets the location from the field and returns an error message if it's blank. */
        String location = updateLocation.getText();
        if(location == null){
            updateDialog.setContentText("Please enter a location.");
            return;
        }

        /** @return method that gets the type from the field and returns an error message if it's blank. */
        String type = updateType.getText();
        if(type == null){
            updateDialog.setContentText("Please enter a type.");
            return;
        }

        /** @return method that gets the Customer ID from the combo box and returns an error message if it's blank. */
        int customer = 0;
        Customers CU = updateCustomer.getSelectionModel().getSelectedItem();
        if(CU == null){
            updateDialog.setContentText("Please select a customer");
            return;
        }
        else{
            customer = CU.getCustomerId();
        }

        /** @return method that gets the User ID from the combo box and returns an error message if it's blank.*/
        int user = 0;
        Users US = updateUser.getSelectionModel().getSelectedItem();
        if(US == null){
            updateDialog.setContentText("Please select a user");
            return;
        }
        else{
            user = US.getUserId();
        }

        /** @return method that gets the contact name from the combo box and returns an error message if it's blank.*/
        String contact;
        Contacts PQ = (Contacts) updateContact.getSelectionModel().getSelectedItem();
        if(PQ == null){
            updateDialog.setContentText("Please Select a Contact");
            return;
        }
        else{
            contact = PQ.getContactName();
        }

        /** @return gets a contact ID from the contact name. */
        int contactId = 0;
        for(Contacts contacts : ContactsDAO.getAllContacts()){
            if(contacts.getContactName().contentEquals(contact)){
                contactId = contacts.getContactId();
            }
        }

        /** @return sets the date from the date picker. */
        LocalDate date = updateDate.getValue();
        if(date == null){
            updateDialog.setContentText("Please select a date");
            return;
        }

        /** @return sets the start time from the combo box of time values. */
        LocalTime SP = updateStart.getSelectionModel().getSelectedItem();
        if(SP == null){
            updateDialog.setContentText("Please Select an Appointment Start Time");
            return;
        }

        /** @return sets the end time from the combo box of time values. */
        LocalTime EZ = updateEnd.getSelectionModel().getSelectedItem();
        if(EZ == null){
            updateDialog.setContentText("Please Select an Appointment end time");
            return;
        }

        /** assembles a LocalDateTime from the date and time. */
        LocalDateTime appointmentStart = LocalDateTime.of(updateDate.getValue(), SP);
        LocalDateTime appointmentEnd = LocalDateTime.of(updateDate.getValue(), EZ);

        /** @return validates that the start time is before the end time. */
        if(appointmentStart.isAfter(appointmentEnd)){
            updateDialog.setContentText("Appointment start time must be before the end.");
            return;
        }

        /** @return validates that the end time is after the start time. */
        if(appointmentEnd.isBefore(appointmentStart)){
            updateDialog.setContentText("Appointment end time must be after the start.");
            return;
        }

        /** @return checks if the customer has an overlapping appointment. */
        for(Appointment eA : AppointmentDAO.getAppointments()){
            if(updatedId == eA.getAppointmentId()){
                continue;
            }

            else if(customer == eA.getCustomerId()){
                if((appointmentStart.isBefore(eA.getStartTime()) || appointmentStart.isEqual(eA.getStartTime())) && appointmentEnd.isAfter(eA.getStartTime())){
                    updateDialog.setContentText("This Customer Already has an appointment scheduled for this time. Please choose a different time.");
                    return;
                }
                else if((appointmentStart.isAfter(eA.getStartTime()) || appointmentStart.isEqual(eA.getStartTime())) && appointmentStart.isBefore(eA.getEndTime())){
                    updateDialog.setContentText("This Customer Already has an appointment scheduled for this time. Please choose a different time.");
                    return;
                }
                else if((appointmentStart.isBefore(eA.getStartTime()) || appointmentStart.isEqual(eA.getStartTime())) && (appointmentEnd.isAfter(eA.getEndTime()) || appointmentEnd.isEqual(eA.getEndTime()))){
                    updateDialog.setContentText("This Customer Already has an appointment scheduled for this time. Please choose a different time.");
                    return;
                }
            }
        }
        /** calls the updateAppointment method with provided parameters.
         * @see java.AppointmentDAO */
        AppointmentDAO.updateAppointment(updatedId, title, description, location, type, Timestamp.valueOf(appointmentStart), Timestamp.valueOf(appointmentEnd), customer, user, contactId);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler that navigates back to the appointment page without saving.
    * @throws IOException throws an exception*/
    public void onUpdateCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler that sets the time choice combo boxes */
    public void onSelect(ActionEvent actionEvent) {
        ZonedDateTime s = ZonedDateTime.of(updateDate.getValue(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
        ZonedDateTime e = ZonedDateTime.of(updateDate.getValue(), LocalTime.of(21, 30), ZoneId.of("America/New_York"));

        ZonedDateTime zonedS = s.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime zonedE = e.withZoneSameInstant(ZoneId.systemDefault());

        LocalTime localS = zonedS.toLocalTime();
        LocalTime localE = zonedE.toLocalTime();
        while(localS.isBefore(localE.plusSeconds(1))){
            updateStart.getItems().add(localS);
            localS = localS.plusMinutes(30);
        }

        ZonedDateTime t = ZonedDateTime.of(updateDate.getValue(), LocalTime.of(8, 30), ZoneId.of("America/New_York"));
        ZonedDateTime l = ZonedDateTime.of(updateDate.getValue(), LocalTime.of(22, 0), ZoneId.of("America/New_York"));

        ZonedDateTime zonedT = t.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime zonedL = l.withZoneSameInstant(ZoneId.systemDefault());

        LocalTime localT = zonedT.toLocalTime();
        LocalTime localL = zonedL.toLocalTime();
        while(localT.isBefore(localL.plusSeconds(1))){
            updateEnd.getItems().add(localT);
            localT = localT.plusMinutes(30);
        }
    }
}
