package fi.tuni.java5.flightweatherapp;

import fi.tuni.java5.flightweatherapp.flightDataAPI.Flight;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Layover;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import fi.tuni.java5.flightweatherapp.settingManagement.InfoCardStorage;
import fi.tuni.java5.flightweatherapp.settingManagement.Preferences;
import fi.tuni.java5.flightweatherapp.settingManagement.SaveData;
import fi.tuni.java5.flightweatherapp.weatherAPI.WeatherAPICall;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author hamza
 */
public class FlightSearchCardController {

    @FXML
    private Label flightType;

    @FXML
    private Label price;

    @FXML
    private VBox flightDetailsContainer;
    
    @FXML
    private ImageView saveFlightButtonIcon;
    
    // Path to icons
    private final String SAVE_FILLED_ICON = "icons/Love icon filled.png";
    private final String SAVE_HOLLOW_ICON = "icons/Love icon hollow.png";

    private SearchResultCard flightDetails;
    private String currency;

    private void updateSaveButtonIcon() {
        Image image;
        if (flightDetails.isSaved) {
            image = new Image(getClass().getResourceAsStream("icons/Love icon filled.png"));
        }
        else {
            image = new Image(getClass().getResourceAsStream("icons/Love icon hollow.png"));
        }
        saveFlightButtonIcon.setImage(image);
    }
    
    public void setSearchCardFlightDetails(SearchResultCard flightDetails) {
        
        // Saving to use later when saving flight
        this.flightDetails = flightDetails;
        String currency = flightDetails.getCurrency();
        
        String currencySymbol;
        
        if (currency.equals("USD")) {
            currencySymbol = "$";
        }
        else if (currency.equals("EUR")) {
            currencySymbol = "€";
        }
        else {
            currencySymbol = "£";
        }
        
        this.price.setText(currencySymbol + String.valueOf(flightDetails.price));
        this.flightType.setText(flightDetails.getType());
        updateSaveButtonIcon();
        List<Flight> flights = flightDetails.getFlights();
        List<Layover> layovers = flightDetails.getLayovers();
        
        flightDetailsContainer.getChildren().clear();
        
        for (int i = 0; i < flights.size(); i++) {
            try {
                // Load the FlightDetails FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightDetails.fxml"));
                Node flightNode = loader.load();

                // Set flight data
                FlightDetailsController controller = loader.getController();
                controller.setFlightDetails(flights.get(i));
                flightDetailsContainer.getChildren().add(flightNode);

                // If there is a layover and it's not the last flight, display the layover details
                if (layovers != null && i < layovers.size()) {
                    Layover layover = layovers.get(i);

                    FXMLLoader layoverLoader = new FXMLLoader(getClass().getResource("LayoverDetails.fxml"));
                    Node layoverNode = layoverLoader.load();

                    LayoverDetailsController layoverController = layoverLoader.getController();
                    layoverController.setLayoverDetails(layover);

                    flightDetailsContainer.getChildren().add(layoverNode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onSaveFlightDetailsButtonPressed() {
        
        SaveData flightSaveObj = new SaveData();
        InfoCardStorage infoCard = flightSaveObj.get_favorites();
        if (flightDetails.isSaved) {
            flightDetails.isSaved = false;
            System.out.println("____________________________________________");
            System.out.println("Flight already saved! Removing from saved list!");
            System.out.println("____________________________________________");
            infoCard.delete_element(flightDetails);
        }
        else {
            flightDetails.isSaved = true;
            infoCard.set_new_element(flightDetails);
        }
        
        flightSaveObj.write_data(infoCard, PrimaryController.userPreference);
        updateSaveButtonIcon();
    };

}
