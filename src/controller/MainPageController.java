package controller;

/**
 * MainPageController class MainPageController.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DAO.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import model.Appointment;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/** Class that controls the Main FXML page. */
public class MainPageController implements Initializable {
    public Button customers;
    public Button appointments;

    public ObservableList <Appointment> userAppointments = FXCollections.observableArrayList();
    public DialogPane mainDialog;
    public Button contact;

    /**
     @param userToSet sets user from the log-in page */
    public void setUser (Users userToSet){
        int loggedInUser = userToSet.getUserId();

        for(Appointment appointment : AppointmentDAO.getAppointments()){
            if(appointment.getUserId() == loggedInUser){
                userAppointments.add(appointment);
            }
        }

        /**
         @return method for alerting the user of an upcoming appointment */
        LocalDateTime logInTime = LocalDateTime.now();
        boolean appointmentAlert = false;
        for(Appointment timedAppointment : userAppointments){
            if(timedAppointment.getStartTime().isAfter(logInTime) && timedAppointment.getStartTime().isBefore(logInTime.plusMinutes(15))){
                appointmentAlert = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setContentText("Appointment ID: " + timedAppointment.getAppointmentId() + " Starting: " + timedAppointment.getStartTime().toLocalDate() + ", " + timedAppointment.getStartTime().toLocalTime());
                alert.showAndWait();

            }
        }
        if(! appointmentAlert){
            mainDialog.setContentText("No upcoming appointments scheduled.");
        }
    }

    /**
     @param actionEvent the event handler that navigates to the customer page.
    * @throws IOException throws an exception*/
    public void goToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersPage.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    /**
     @param actionEvent the event handler that navigates to the appointment page.
    * @throws IOException throws an exception*/
    public void onApp(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler that navigates to the contacts page.
    * @throws IOException throws an exception*/
    public void onContact(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Contacts.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
