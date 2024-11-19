package fi.tuni.java5.flightweatherapp;

import fi.tuni.java5.flightweatherapp.flightDataAPI.Layover;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LayoverDetailsController {
    @FXML
    private Label layoverDuration;
    
    @FXML
    private Label layoverAirportCode;
    
    @FXML
    private Label layoverAirportLabel;

    public void setLayoverDetails(Layover layover) {
        
        String formattedDuration = FlightDetailsController.convertMinutesToHoursAndMinutes(layover.getDuration());
        layoverDuration.setText(formattedDuration);
        layoverAirportCode.setText(layover.getId());
        layoverAirportLabel.setText(layover.getName());
    }
}
