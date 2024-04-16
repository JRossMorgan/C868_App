package controller;

/**
 * LogIn class LogIn.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;
import DAO.UsersDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/** Class that controls the Log In FXML page. */
public class LogIn implements Initializable {
    public TextField userNameTF;
    public TextField passwordTF;
    public Button logIn;
    public DialogPane logInDialog;
    public Label headLine;
    public Label userName;
    public Label password;
    String country = Locale.getDefault().getCountry();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         @return method that sets the labels based on language settings */
        if(Locale.getDefault().getLanguage().equals("fr")){
            logInDialog.setContentText("Votre Emplacment: " + ZoneId.systemDefault());
            logIn.setText("Se Connecter");
            headLine.setText("Se Conncter");
            userName.setText("Nom d'utilisateur");
            password.setText("Mot de passe");

        }
        else{
            logInDialog.setContentText("Your Location: " + ZoneId.systemDefault());
        }

    }

    /**
     @param actionEvent the event handler that authenticates log-in credentials and navigates to the main page.
    * @throws IOException throws an exception*/
    public void onLogIn(ActionEvent actionEvent) throws IOException {
        /** @return gets user name, returns a message if blank. */
        String userName = userNameTF.getText();
        if(userName.isBlank()){
            if(Locale.getDefault().getLanguage().equals("fr")){
                logInDialog.setContentText("Entrez le nom d'utilisateur, si vous plait");
            }
            else{
                logInDialog.setContentText("Please Enter Username");
            }
            return;
        }
        /** @return gets password, returns a message if blank. */
        String password = passwordTF.getText();
        if(password.isBlank()){
            if(Locale.getDefault().getLanguage().equals("fr")){
                logInDialog.setContentText("Entrez le mot de passe, si vous plait");
            }
            else{
                logInDialog.setContentText("Please Enter Password");
            }
            return;
        }
        /** implements a string for the activity log. */
        String loginAttempt = "login_activity.txt", attempt;

        /** creates a new FileWriter with the loginAttempt string. */
        FileWriter file = new FileWriter(loginAttempt, true);
        /** creates a new PrintWriter with the file PrintWriter. */
        PrintWriter loginReport = new PrintWriter(file);

        boolean logIn = false;

        /**
         @return method that checks credentials and appends the log-in activity file */
        for(Users user : UsersDAO.getAllUsers()){
            //System.out.println(user.getUserName() + " " + user.getPassword());
            if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                logIn = true;
                attempt = userName + " " + LocalDateTime.ofInstant(Instant.now(), ZoneId.of("GMT")) + " Attempt: Successful";
                loginReport.println(attempt);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/Main_Page.fxml"));
                loader.load();

                MainPageController settingUser = loader.getController();
                settingUser.setUser((Users) user);
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else {
                continue;
            }
            loginReport.close();
        }
        if(! logIn){
            attempt = userName + " " + LocalDateTime.ofInstant(Instant.now(), ZoneId.of("GMT")) + " " + "Attempt: Failed";
            loginReport.println(attempt);
            if(Locale.getDefault().getLanguage().equals("fr")){
                logInDialog.setContentText("La combinaison des nom d'utilisateur et mot de passe est invalide");
            }
            else{
                logInDialog.setContentText("Invalid Username and Password Combination");
            }
            loginReport.close();
        }
    }


}
