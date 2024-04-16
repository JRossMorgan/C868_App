package Main;

/**
 * Main class Main.java
 */
/**
 *
 * @author Jedediah R Morgan
 * @version 2
 */

import DBConnection.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** class that launches the application. */
public class Main extends Application {
    @Override
    /**
     @param start launches the gui */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Log_in.fxml"));
        stage.setTitle("Customer Relations Manager 2000");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();


    }
    /**
     @param args opens and closes the database connection and launches the application */
    public static void main(String[] args){
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
