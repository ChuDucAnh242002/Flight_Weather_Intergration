package fi.tuni.java5.flightweatherapp;

import fi.tuni.java5.flightweatherapp.flightDataAPI.Flight;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Layover;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    public void setSearchCardFlightDetails(SearchResultCard flightDetails) {

        this.price.setText("$" + String.valueOf(flightDetails.price));
        this.flightType.setText(flightDetails.getType());

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
                if (i < layovers.size()) {
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

}
