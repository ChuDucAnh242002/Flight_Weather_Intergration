package fi.tuni.java5.flightweatherapp;

import fi.tuni.java5.flightweatherapp.flightDataAPI.Flight;
import fi.tuni.java5.flightweatherapp.image.ImageRetriver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class FlightDetailsController {
    
    @FXML
    private Label airline;
    
    @FXML
    private Label departureDate;
    
    @FXML
    private Label departureTime;
    
    @FXML
    private Label departureAirportCode;
    
    @FXML
    private Label departureAirportLabel;
    
    @FXML
    private Label arrivalTime;
    
    @FXML
    private Label arrivalAirportCode;
    
    @FXML
    private Label arrivalAirportLabel;
    
    @FXML
    private Label airplane;
    
    @FXML
    private Label flightDuration;
    
    @FXML
    private Label extraDetails;
    
    @FXML
    private ImageView airlineLogo;

    private String formatDate(String departureDate) {
        
        LocalDate date = LocalDate.parse(departureDate);

        // Define the output format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d");

        // Format the date
        String formattedDate = date.format(formatter);
        return formattedDate;
    }
    
    public static String convertMinutesToHoursAndMinutes(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        return hours + " hr " + minutes + " min";
    }
      
    public void setFlightDetails(Flight flight) 
    {
        String departureSchedule = flight.getDepartureAirport().getTime();
        String[] departureDateAndTime = departureSchedule.split(" ");
        String formattedDepartureDate = formatDate(departureDateAndTime[0]);
        int extensionsLastIndex = flight.getExtensions().size() - 1;
        String carbonEmission = flight.getExtensions().get(extensionsLastIndex);
        String [] carbonEmissionValue = carbonEmission.split(":");
        String extraDetails = flight.getTravelClass() + " . Legroom " + flight.getLegroom() + " in . " + "Carbon footprint" + carbonEmissionValue[1];
        String convertedDuration = convertMinutesToHoursAndMinutes(flight.getDuration());
        
        String arrivalSchedule = flight.getArrivalAirport().getTime();
        String[] arrivalDateAndTime = arrivalSchedule.split(" ");
        Image airlineLogo = ImageRetriver.retrieveImage(flight.airline_logo);
        
        this.airline.setText(flight.getAirline());
        this.departureDate.setText(formattedDepartureDate);
        this.departureTime.setText(departureDateAndTime[1]);
        this.departureAirportCode.setText(flight.getDepartureAirport().getId());
        this.departureAirportLabel.setText(flight.getDepartureAirport().getName());
        this.arrivalTime.setText(arrivalDateAndTime[1]);
        this.arrivalAirportCode.setText(flight.getArrivalAirport().getId());
        this.arrivalAirportLabel.setText(flight.getArrivalAirport().getName());
        this.airplane.setText(flight.getAirplane());
        this.flightDuration.setText(convertedDuration);
        this.extraDetails.setText(extraDetails);
        this.airlineLogo.setImage(airlineLogo);
       
       
        
    }
}
