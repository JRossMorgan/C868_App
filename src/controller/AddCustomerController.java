package controller;

/**
 * AddCustomerController class AddCustomerController.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DAO.CountryDAO;
import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Class that controls the Add Customer FXML page. */
public class AddCustomerController implements Initializable {
    public TextField addName;
    public TextField addAddress;
    public TextField addPostal;
    public TextField addPhone;
    public ComboBox <Country> addCountry;
    public ComboBox <Divisions> addDivision;
    public Button addSave;
    public Button addCancel;
    public DialogPane addDialog;

    public ObservableList<Divisions> countryDivision = FXCollections.observableArrayList();


    /**
     @param actionEvent the event handler for saving.
    * @throws IOException throws an exception*/
    public void onAdd(ActionEvent actionEvent) throws IOException{
        /** @return method that gets the name from the field and returns an error message if it's blank. */
        String name = addName.getText();
        if(name.isBlank()){
            addDialog.setContentText("Please Enter A Name");
            return;
        }
        /** @return method that gets the address from the field and returns an error message if it's blank. */
        String address = addAddress.getText();
        if(address.isBlank()){
            addDialog.setContentText("Please Enter An Address");
            return;
        }
        /** @return method that gets the postal code from the field and returns an error message if it's blank. */
        String postal = addPostal.getText();
        if(postal.isBlank()){
            addDialog.setContentText("Please Enter A Postal Code");
            return;
        }
        /** @return method that gets the phone number from the field and returns an error message if it's blank. */
        String phone = addPhone.getText();
        if(phone.isBlank()){
            addDialog.setContentText("Please Enter A Phone Number");
            return;
        }
        /** @return method that gets the division from the combo box and returns an error message if it's blank. */
        int division = 0;

        Divisions PQ = (Divisions) addDivision.getSelectionModel().getSelectedItem();
        if(PQ == null){
            addDialog.setContentText("Please Select a Division");
            return;
        }
        else{
            division = PQ.getDivisionId();
        }

        /** calls the insertCustomer method with provided parameters.
         * @see java.CustomersDAO */
        CustomersDAO.insertCustomer(name, address, postal, phone, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT"))), division);

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersPage.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param actionEvent the event handler for canceling.
    * @throws IOException throws an exception*/
    public void onAddCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersPage.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCountry.setItems(CountryDAO.getCountries());
        addDivision.setItems(countryDivision);

    }

    /**
     @param actionEvent the event handler for setting the division combo box */
    public void onSelect(ActionEvent actionEvent) {
        countryDivision.clear();
        Country SP = (Country) addCountry.getSelectionModel().getSelectedItem();
        if(SP == null){
            return;
        }
        else{
            for(Divisions division : DivisionsDAO.getDivisions()){
                if(division.getCountryId() == SP.getCountryId()){
                    countryDivision.add(division);
                }
            }
        }
    }
}
