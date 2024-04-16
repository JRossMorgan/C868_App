package controller;

/**
 * AppointmentsController class AppointmentsController.java
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/** Class that controls the Appointments FXML page. */
public class AppointmentsController implements Initializable {

    public Tab byMonth;
    public TableView <Appointment> appMonth;
    public TableColumn <Appointment, Integer> idByMonth;
    public TableColumn <Appointment, String> titleByMonth;
    public TableColumn <Appointment, String> descByMonth;
    public TableColumn <Appointment, String> locByMonth;
    public TableColumn <Appointment, String> contactByMonth;
    public TableColumn <Appointment, String> typeByMonth;
    public TableColumn <Appointment, Timestamp> startByMonth;
    public TableColumn <Appointment, Timestamp> endByMonth;
    public TableColumn <Appointment, Integer> customerByMonth;
    public TableColumn <Appointment, Integer> userByMonth;
    public Tab byWeek;
    public TableView <Appointment> appWeek;
    public TableColumn <Appointment, Integer> idByWeek;
    public TableColumn <Appointment, String> titleByWeek;
    public TableColumn <Appointment, String> descByWeek;
    public TableColumn <Appointment, String> locByWeek;
    public TableColumn <Appointment, String> contactByWeek;
    public TableColumn <Appointment, String> typeByWeek;
    public TableColumn <Appointment, Timestamp> startByWeek;
    public TableColumn <Appointment, Timestamp> endByWeek;
    public TableColumn <Appointment, Integer> customerByWeek;
    public TableColumn <Appointment, Integer> userByWeek;
    public ComboBox <Month> monthBox;
    public DatePicker chooseWeek;

    public ObservableList<Appointment> monthlyAppointment = FXCollections.observableArrayList();
    public ObservableList<Appointment> weeklyAppointment = FXCollections.observableArrayList();
    public ObservableList <Month> monthList = FXCollections.observableArrayList();
    public Button addMonth;
    public Button updateMonth;
    public Button deleteMonth;
    public Button addWeek;
    public Button updateWeek;
    public Button deleteWeek;
    public DialogPane appDialog;
    public Button appHome;
    public ComboBox <Month> reportMonth;
    public ComboBox <String> reportType;
    public Button generateReport;
    public ObservableList <Month> reportMonths = FXCollections.observableArrayList();
    public ObservableList <String> typeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /** @return sets the values in the month list. */
        for(Month month : Month.values()){
            monthList.add(month);
            monthBox.setItems(monthList);
        }
        appMonth.setItems(monthlyAppointment);
        idByMonth.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleByMonth.setCellValueFactory(new PropertyValueFactory<>("title"));
        descByMonth.setCellValueFactory(new PropertyValueFactory<>("description"));
        locByMonth.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactByMonth.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeByMonth.setCellValueFactory(new PropertyValueFactory<>("type"));
        startByMonth.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endByMonth.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerByMonth.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userByMonth.setCellValueFactory(new PropertyValueFactory<>("userId"));

        appWeek.setItems(weeklyAppointment);
        idByWeek.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleByWeek.setCellValueFactory(new PropertyValueFactory<>("title"));
        descByWeek.setCellValueFactory(new PropertyValueFactory<>("description"));
        locByWeek.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactByWeek.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeByWeek.setCellValueFactory(new PropertyValueFactory<>("type"));
        startByWeek.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endByWeek.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerByWeek.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userByWeek.setCellValueFactory(new PropertyValueFactory<>("userId"));

        appMonth.refresh();
        appWeek.refresh();


        /**@return sets unique month values for the report combo box. */
        for(Appointment appointment : AppointmentDAO.getAppointments()){
            if(!reportMonths.contains(appointment.getStartTime().getMonth())){
                reportMonths.add(appointment.getStartTime().getMonth());
            }
            reportMonth.setItems(reportMonths);
        }

        /**@return sets unique type values for the report combo box. */
        for(Appointment type : AppointmentDAO.getAppointments()){
            if(!typeList.contains(type.getType())){
                typeList.add(type.getType());
            }
            reportType.setItems(typeList);
        }
    }

    /**
     @param actionEvent the event handler for setting the month table */
    public void onChooseMonth(ActionEvent actionEvent) {
        monthlyAppointment.clear();
        Month SP = monthBox.getSelectionModel().getSelectedItem();
        if(SP == null){
            return;
        }
        else{
            for(Appointment appointment : AppointmentDAO.getAppointments()){
                if(appointment.getStartTime().getMonth() ==SP){
                    monthlyAppointment.add(appointment);
                }
            }
        }
    }

    /**
     @param actionEvent the event handler for setting the week week table */
    public void onChooseWeek(ActionEvent actionEvent) {
        weeklyAppointment.clear();
        LocalDate SP = chooseWeek.getValue();
        if(SP == null){
            return;
        }
        else{
            for(Appointment appointment : AppointmentDAO.getAppointments()){
                int startWeek = SP.minusDays(3).getDayOfMonth();
                int endWeek = SP.plusDays(3).getDayOfMonth();
                if(appointment.getStartTime().getDayOfMonth() >= startWeek && appointment.getStartTime().getDayOfMonth() <= endWeek){
                    weeklyAppointment.add(appointment);
                }
            }
        }
    }

    /**
     @param actionEvent the event handler for navigating to the Add Appointment page from the month tab.
    * @throws IOException throws an exception*/
    public void onAddMonth(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler for navigating to the update appointment page from the month tab.
    * @throws IOException throws an exception*/
    public void onUpdateMonth(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
        loader.load();

        UpdateAppointmentController updatingAppointment = loader.getController();
        updatingAppointment.updateAppointment((Appointment) appMonth.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     @param actionEvent the event handler for deleting an appointment from the month tab */
    public void onDeleteMonth(ActionEvent actionEvent) {
        Appointment SP = (Appointment) appMonth.getSelectionModel().getSelectedItem();
        if(SP == null){
            return;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Are you sure you want to delete appointment?");
            Optional<ButtonType> pressed = alert.showAndWait();
            if(pressed.isPresent() && pressed.get() == ButtonType.OK){
                AppointmentDAO.deleteAppointment(SP.getAppointmentId());
                appDialog.setContentText("Appointment " + SP.getAppointmentId() + " " + SP.getType() + " has been canceled.");
                appMonth.refresh();
                return;
            }
        }
    }

/**
 @param actionEvent the event handler for navigating to the add appointment page from the week tab.
    * @throws IOException throws an exception*/
    public void onAddWeek(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler for navigating to the update appointment page from the week tab.
    * @throws IOException throws an exception*/
    public void onUpdateWeek(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
        loader.load();

        UpdateAppointmentController updatingAppointment = loader.getController();
        updatingAppointment.updateAppointment((Appointment) appWeek.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     @param actionEvent the event handler for deleting an appointment from the week tab */
    public void onDeleteWeek(ActionEvent actionEvent) {
        Appointment SP = (Appointment) appWeek.getSelectionModel().getSelectedItem();
        if(SP == null){
            return;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Are you sure you want to delete appointment?");
            Optional<ButtonType> pressed = alert.showAndWait();
            if(pressed.isPresent() && pressed.get() == ButtonType.OK){
                AppointmentDAO.deleteAppointment(SP.getAppointmentId());
                appDialog.setContentText("Appointment " + SP.getAppointmentId() + " " + SP.getType() + " has been canceled.");
                appWeek.refresh();
                return;
            }
        }
    }

    /**
     @param actionEvent the event handler for navigating to the home page.
    * @throws IOException throws an exception*/
    public void onHome(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Page.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler for generating a report on appointment types by month */
    public void onGenerate(ActionEvent actionEvent) {
        Month getMonth = reportMonth.getSelectionModel().getSelectedItem();
        if(getMonth == null){
            appDialog.setContentText("Please choose a month");
            return;
        }
        String chooseType = reportType.getSelectionModel().getSelectedItem();
        if(chooseType == null){
            appDialog.setContentText("Please choose a type");
            return;
        }

        int numAppointments = 0;
        for(Appointment appointment : AppointmentDAO.getAppointments()){
            if(appointment.getStartTime().getMonth().equals(getMonth) && appointment.getType().equalsIgnoreCase(chooseType)){
                numAppointments = numAppointments + 1;
            }
            appDialog.setContentText("There are " + numAppointments + " " + chooseType + " appointments in " + getMonth);
        }
    }
}
