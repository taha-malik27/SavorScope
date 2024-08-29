//TAHA MALIK - taha.malik2@ucalgary.ca


package rw.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class TrackerStart extends Application {
    /**
     * Initializes and starts the JavaFX application.
     * This method sets up the main stage with a specified scene based on an FXML layout.
     * It also initializes the associated controller with parameters.
     *
     * @param stage the primary stage for this application, onto which the scene is set.
     * @throws IOException if loading the FXML file fails.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML layout for the Tracker using FXMLLoader.
        FXMLLoader fxmlLoader = new FXMLLoader(TrackerStart.class.getResource("TrackerView.fxml"));

        // Create a Scene by loading the FXMLLoader. Set the preferred size of the scene.
        Scene scene = new Scene(fxmlLoader.load(), 881, 575);

        // Retrieve the controller associated with the FXML loaded by the FXMLLoader.
        TrackerController TrackerController = fxmlLoader.getController();

        // Extract raw program arguments to pass to the controller for initialization.
        Object[] args = getParameters().getRaw().toArray();

        // Initialize the controller with the provided arguments.
        TrackerController.initialize(args);

        // Set the title of the stage.
        stage.setTitle("SavorScope");

        // Set the application icon.
        Image icon = new Image("SavorScope.png");
        stage.getIcons().add(icon);

        // Set the scene on the stage and then show the stage.
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method to launch the JavaFX application.
     * It serves as the entry point for the application.
     *
     * @param args command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application with the given arguments.
        launch(args);
    }
}