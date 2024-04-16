package controller;

/**
 * ContactsController class ContactsController.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DAO.AppointmentDAO;
import DAO.ContactsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Class that controls the Contacts FXML page. */
public class ContactsController implements Initializable {
    public TableView <Contacts> contactsTable;
    public TableColumn <Contacts, Integer> contactId;
    public TableColumn <Contacts, String> contactName;
    public TableColumn <Contacts, String> contactEmail;
    public Button home;
    public DialogPane contactDialog;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsTable.setItems(ContactsDAO.getAllContacts());
        contactId.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        contactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        contactDialog.setContentText("Please Select a Contact to see Schedule");
        /**
         @return lambda expression for generating the schedule report */
        contactsTable.getSelectionModel().selectedItemProperty().addListener((obs, previous, current) -> {
            if(current != null){
                StringBuilder report = new StringBuilder();
                for(Appointment appointment : AppointmentDAO.getAppointments()){
                    if(appointment.getContact().equals(current.getContactName())){
                        report.append("-");
                        report.append(appointment.getAppointmentId());
                        report.append(" ");
                        report.append(appointment.getTitle());
                        report.append(" ");
                        report.append(appointment.getType());
                        report.append(" ");
                        report.append(appointment.getDescription());
                        report.append(" ");
                        report.append(appointment.getStartTime());
                        report.append(" ");
                        report.append(appointment.getEndTime());
                        report.append(" ");
                        report.append(appointment.getCustomerId());
                        report.append("\n");
                    }
                    contactDialog.setContentText(report.toString());
                }
            }
        });
    }


    /**
     @param actionEvent the event handler for navigating home.
    * @throws IOException throws an exception*/
    public void onHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Page.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
