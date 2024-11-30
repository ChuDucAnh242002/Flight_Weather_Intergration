package fi.tuni.java5.flightweatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.transform.Scale;
import javafx.scene.paint.Color;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    // Adjust scale factor as needed
    public static double scaleFactor = 0.8;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("StartScreen.fxml"));
        
        Parent root = fxmlLoader.load();
        
        // Get the controller instance
        PrimaryController controller = fxmlLoader.getController();

        // Pass the stage to the controller
        controller.setStage(stage);
        
        // Get AnchorPane as the root in FXML
        AnchorPane anchorPane = (AnchorPane) root;

        // Get the preferred size of the AnchorPane
        double prefWidth = anchorPane.getPrefWidth() * scaleFactor;
        double prefHeight = anchorPane.getPrefHeight() * scaleFactor;
        
        // Create the scene
        scene = new Scene(root, prefWidth, prefHeight);
        
        // Set color of scene
        scene.setFill(Color.web("#F4F4F4"));
        
        // Create a scale transformation
        Scale scale = new Scale(scaleFactor, scaleFactor);
        
        // Apply the scale transformation to the root node
        root.getTransforms().add(scale);
        
        // Set name and icon of stage
        stage.setTitle("Flight Weather Search App");
        Image appLogo = new Image(getClass().getResourceAsStream("sprites/app_icon.png"));
        stage.getIcons().add(appLogo);
        
        stage.setScene(scene);
        stage.show();
    }
    
    static void setRoot(String fxml) throws IOException {
        // Load the new root node from FXML
        Parent root = loadFXML(fxml);
        
        // Apply the scale transformation to the new root node
        Scale scale = new Scale(scaleFactor, scaleFactor);
        root.getTransforms().add(scale);
        
        // Set the new root node in the scene
        scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}