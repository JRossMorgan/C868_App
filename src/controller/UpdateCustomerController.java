package controller;

/**
 * UpdateCustomerController class UpdateCustomerController.java
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
import model.Customers;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

/** Class that controls the Update Customer FXML page. */
public class UpdateCustomerController implements Initializable {
    public TextField updateID;
    public TextField updateName;
    public TextField updateAddress;
    public TextField updatePostal;
    public TextField updatePhone;
    public ComboBox <Country> updateCountry;
    public ComboBox <Divisions> updateDivision;
    public Button updateSave;
    public Button updateCancel;
    public DialogPane updateDialog;

    public ObservableList<Divisions> updatableDivisions = FXCollections.observableArrayList();

    /**
     @param actionEvent sets the division from the country combo box */
    public void onSelect(ActionEvent actionEvent) {
        updatableDivisions.clear();
        Country SP = (Country) updateCountry.getSelectionModel().getSelectedItem();
        if(SP == null){
            return;
        }
        else{
            for(Divisions division : DivisionsDAO.getDivisions()){
                if(division.getCountryId() == SP.getCountryId()){
                    updatableDivisions.add(division);
                }
            }
        }
    }

    /**
     @param actionEvent the event handler that saves changes and navigates back to the customers page.
    * @throws IOException throws an exception*/
    public void onUpdate(ActionEvent actionEvent) throws IOException{
        /** @return method that gets the customer ID from the field and returns an error message if it's blank. */
        int id = Integer.parseInt(updateID.getText());
        String name = updateName.getText();
        if(name.isBlank()){
            updateDialog.setContentText("Please Enter A Name");
            return;
        }
        /** @return method that gets the address from the field and returns an error message if it's blank. */
        String adress = updateAddress.getText();
        if(adress.isBlank()){
            updateDialog.setContentText("Please Enter An Address");
            return;
        }
        /** @return method that gets the postal code from the field and returns an error message if it's blank. */
        String postal = updatePostal.getText();
        if(postal.isBlank()){
            updateDialog.setContentText("Please Enter A Postal Code");
            return;
        }
        /** @return method that gets the phone number from the field and returns an error message if it's blank. */
        String phone = updatePhone.getText();
        if (phone.isBlank()){
            updateDialog.setContentText("Please Enter A Phone Number");
            return;
        }
        /** @return method that gets the division ID from the combo box and returns an error message if it's blank. */
        int division = 0;
        Divisions PQ = (Divisions) updateDivision.getSelectionModel().getSelectedItem();
        if(PQ == null){
            updateDialog.setContentText("Please Select a Division");
            return;
        }
        else{
            division = PQ.getDivisionId();
        }
        /** calls the updateCustomer method with provided parameters.
         * @see java.CustomersDAO */
        CustomersDAO.updateCustomer(id, name, adress, postal, phone, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT"))), division );

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersPage.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     @param actionEvent the event handler that navigates back to the customers page without saving.
    * @throws IOException throws an exception*/
    public void onUpdateCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersPage.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     @param customerToUpdate sets customer from the customers page */
    public void updateCustomer(Customers customerToUpdate){
        updateID.setText(String.valueOf(customerToUpdate.getCustomerId()));
        updateName.setText(customerToUpdate.getName());
        updateAddress.setText(customerToUpdate.getAddress());
        updatePostal.setText(customerToUpdate.getPostalCode());
        updatePhone.setText(customerToUpdate.getPhone());
        for(Country customerCountry : CountryDAO.getCountries()) {
            if(customerCountry.name.equalsIgnoreCase(customerToUpdate.getCountry())){
                updateCountry.setValue(customerCountry);
            }
        }

        /** @return sets the division combo box from the country selected. */
        for(Divisions customerDivision : DivisionsDAO.getDivisions()){
            if(customerDivision.name.equalsIgnoreCase(customerToUpdate.getDivision())){
                updateDivision.setValue(customerDivision);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        updateCountry.setItems(CountryDAO.getCountries());

        updateDivision.setItems(updatableDivisions);
    }
}
