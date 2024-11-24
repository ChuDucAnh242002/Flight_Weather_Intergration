package fi.tuni.java5.flightweatherapp;

import fi.tuni.java5.flightweatherapp.airportDataAPI.AirportDataAPICall;
import fi.tuni.java5.flightweatherapp.airportDataAPI.AirportDataResponse;
import fi.tuni.java5.flightweatherapp.flightDataAPI.FlightDataAPICall;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResult;
import fi.tuni.java5.flightweatherapp.flightDataAPI.FlightDataRequest;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class PrimaryController {
    private static String apiKey = "c37bc522ccf0e106cd0d1be7e1ed9655";
    
    public static String getApiKey(){
        return apiKey;
    }
    
    // Utils
    String emptyString = "";
    
    String defaultAirportCode = "--";
    
    String defaultAirportName = "Please enter airport code";
    
    String errorAirportName = "Airport code not found";
    
    int passengerLimitAmount = 99;
    
    // Departure    
    @FXML
    private Label departureAirportCodeLabel;
    
    @FXML
    private Label departureAirportNameLabel;
    
    @FXML
    private Button departureAirportEditButton;
    
    @FXML
    private Button departureAirportFindButton;
    
    @FXML
    private TextField departureAirportTextField;
    
    @FXML
    private DatePicker outboundDatePicker;
    
    // Arrival    
    @FXML
    private Label arrivalAirportCodeLabel;
    
    @FXML
    private Label arrivalAirportNameLabel;
    
    @FXML
    private Button arrivalAirportEditButton;
    
    @FXML
    private Button arrivalAirportFindButton;
    
    @FXML
    private TextField arrivalAirportTextField;
    
    @FXML
    private DatePicker returnDatePicker;
    
    // Search parameter
    private int adultPassengerAmount = 1;
    private int childPassengerAmount = 0;
    
    @FXML
    private Label adultAmountLabel;
    
    @FXML
    private Label childAmountLabel;
    
    @FXML
    private VBox fetchedFlightsContainer;
    
    @FXML
    private VBox searchNotification;
    
    @FXML
    private Label searchStatusLabel;
    
    @FXML
    private ImageView searchStatusImageView;
    
    // Search Parameter
    private Boolean IsDecreasePassengerLegal() {
        Boolean isLegal = (adultPassengerAmount == 1 && childPassengerAmount == 0)
        || (adultPassengerAmount == 0 && childPassengerAmount == 1);
                
        return isLegal;
    }
    
    private Boolean IsIncreasePassengerLegal() {
        Boolean isOverLimit = (adultPassengerAmount > passengerLimitAmount) || (childPassengerAmount > passengerLimitAmount);
        
        return isOverLimit;
    }
    
    @FXML
    private void OnAdultIncreaseButtonPressed() {
        if (IsIncreasePassengerLegal()) {
            return;
        }
        
        adultPassengerAmount++;
        
        adultAmountLabel.setText(String.valueOf(adultPassengerAmount));
    }
    
    @FXML
    private void OnAdultDecreaseButtonPressed() {
        if (IsDecreasePassengerLegal()) {
            return;
        }
        
        if (adultPassengerAmount == 0) {
            return;
        }
        
        adultPassengerAmount--;
        
        adultAmountLabel.setText(String.valueOf(adultPassengerAmount));
    }
    
    @FXML
    private void OnChildIncreaseButtonPressed() {
        if (IsIncreasePassengerLegal()) {
            return;
        }
        
        childPassengerAmount++;
        
        childAmountLabel.setText(String.valueOf(childPassengerAmount));
    }
    
    @FXML
    private void OnChildDecreaseButtonPressed() {
        if (IsDecreasePassengerLegal()) {
            return;
        }
        
        if (childPassengerAmount == 0) {
            return;
        }
        
        childPassengerAmount--;
        
        childAmountLabel.setText(String.valueOf(childPassengerAmount));
    }
    
    // Departure
    private void ToggleDepartureAirportInterface(Boolean toggleValue) {
        departureAirportCodeLabel.setVisible(toggleValue);
        departureAirportNameLabel.setVisible(toggleValue);
        departureAirportEditButton.setVisible(toggleValue);
        
        departureAirportTextField.setVisible(!toggleValue);
        departureAirportFindButton.setVisible(!toggleValue);
    }
    
    private void SetDepartureAiportInfo(String airportCode, String airportName) {
        departureAirportCodeLabel.setText(airportCode);
        departureAirportNameLabel.setText(airportName);
    }
    
    @FXML
    private void OnDepartureAirportEditButtonPressed() {
        ToggleDepartureAirportInterface(false);
        
        AirportDataAPICall.departureAirport = null;
        
        departureAirportTextField.setText(emptyString);
    }
    
    @FXML
    private void OnDepartureAirportFindButtonPressed() {
        String searchString = departureAirportTextField.getText();
        
        if (searchString == null || searchString == emptyString) {
            OpenErrorMessage(airportSearchMessage);
            return;
        }
        
        List<AirportDataResponse> searchData;
        
        if (searchString.length() == 3) {
            searchData = AirportDataAPICall.RequestAirportDataByAirportCode(searchString);
        } else {
            searchData = AirportDataAPICall.RequestAirportDataByAirportName(searchString);
        }
        
        ToggleDepartureAirportInterface(true);

        if (searchData == null || searchData.isEmpty()) {
            SetDepartureAiportInfo(defaultAirportCode, defaultAirportName);
            
            AirportDataAPICall.departureAirport = null;
            
            return;
        }
        
        AirportDataAPICall.departureAirport = searchData.get(0);
        
        SetDepartureAiportInfo(AirportDataAPICall.departureAirport.getIata(), AirportDataAPICall.departureAirport.getName());
    }
    
    // Arrival
    private void ToggleArrivalAirportInterface(Boolean toggleValue) {
        arrivalAirportCodeLabel.setVisible(toggleValue);
        arrivalAirportNameLabel.setVisible(toggleValue);
        arrivalAirportEditButton.setVisible(toggleValue);
        
        arrivalAirportTextField.setVisible(!toggleValue);
        arrivalAirportFindButton.setVisible(!toggleValue);
    }
    
    private void SetArrivalAiportInfo(String airportCode, String airportName) {
        arrivalAirportCodeLabel.setText(airportCode);
        arrivalAirportNameLabel.setText(airportName);
    }
    
    @FXML
    private void OnArrivalAirportEditButtonPressed() {
        ToggleArrivalAirportInterface(false);
        
        AirportDataAPICall.arrivalAirport = null;
        
        arrivalAirportTextField.setText(emptyString);
    }
    
    @FXML
    private void OnArrivalAirportFindButtonPressed() {
        String searchString = arrivalAirportTextField.getText();
        
        if (searchString == null || searchString == emptyString) {
            OpenErrorMessage(airportSearchMessage);
            return;
        }
        
        List<AirportDataResponse> searchData;
        
        if (searchString.length() == 3) {
            searchData = AirportDataAPICall.RequestAirportDataByAirportCode(searchString);
        } else {
            searchData = AirportDataAPICall.RequestAirportDataByAirportName(searchString);
        }
        
        ToggleArrivalAirportInterface(true);

        if (searchData == null || searchData.isEmpty()) {
            SetArrivalAiportInfo(defaultAirportCode, defaultAirportName);
            
            AirportDataAPICall.arrivalAirport = null;
            
            return;
        }
        
        AirportDataAPICall.arrivalAirport = searchData.get(0);
        
        SetArrivalAiportInfo(AirportDataAPICall.arrivalAirport.getIata(), AirportDataAPICall.arrivalAirport.getName());
    }
    
    // Search flight
    
    String missingAirportErrorMessage = "Please add both Departure and Arrival airports!";
    
    String missingDatePickerErrorMessage = "Please add at least Outbound date!";
    
    String illegalDatePickerErrorMessage = "The selected date is in the past, please change it!";
    
    String searchingMessage = "Please enter all information to start searching!";
    
    String airportSearchMessage = "Please enter Airport Code or City name!";
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
    private void OpenErrorMessage(String messageString) {
        searchNotification.setVisible(true);
        
        searchStatusLabel.setText(messageString);
            
        Image image = new Image(getClass().getResourceAsStream("icons/Attention Circle Icon.png"));
        searchStatusImageView.setImage(image);
    }
    
    private void OpenAndCloseSearchingMessage(boolean isOpen) {
        searchNotification.setVisible(isOpen);
        
        if (isOpen) {
            searchStatusLabel.setText(searchingMessage);
            
            Image image = new Image(getClass().getResourceAsStream("icons/Searching Icon.png"));
            searchStatusImageView.setImage(image);
        }
    }
    
    private boolean isDatePickerValueNull() {
        return outboundDatePicker.getValue() == null;
    }
    
    private boolean isDatePickerValueLegal() {
        LocalDate today = LocalDate.now();
        
        LocalDate outboundDate = outboundDatePicker.getValue();
        
        LocalDate returnDate = returnDatePicker.getValue();
        
        if (returnDate == null) {
            return !(outboundDate.isBefore(today));
        } else {
            return !(outboundDate.isBefore(today) || returnDate.isBefore(today));
        }
    }
    
    @FXML
    private void OnSearchFlightButtonPressed() {        
        if (AirportDataAPICall.isAnyAirportNull()) {
            OpenErrorMessage(missingAirportErrorMessage);
            
            return;
        }
                
        if (isDatePickerValueNull()) {
            OpenErrorMessage(missingDatePickerErrorMessage);
            
            return;
        }
                
        if (!isDatePickerValueLegal()) {
            OpenErrorMessage(illegalDatePickerErrorMessage);
            
            return;
        }
        
        OpenAndCloseSearchingMessage(false);
                    
        String outboundDateString = outboundDatePicker.getValue().format(formatter);
        
        if (returnDatePicker.getValue() == null) {
            FlightDataAPICall.flightDataRequest = new FlightDataRequest(
                                AirportDataAPICall.departureAirport.getIata(), 
                                AirportDataAPICall.arrivalAirport.getIata(),
                                outboundDateString,
                                adultPassengerAmount,
                                childPassengerAmount);
        } else {
            String returnDateString = returnDatePicker.getValue().format(formatter);
            
            FlightDataAPICall.flightDataRequest = new FlightDataRequest(
                                AirportDataAPICall.departureAirport.getIata(), 
                                AirportDataAPICall.arrivalAirport.getIata(),
                                outboundDateString,
                                returnDateString,
                                adultPassengerAmount,
                                childPassengerAmount);
        }
                
        SearchResult searchResult = FlightDataAPICall.RequestFlightDataAPI();
        
        System.out.println("_____________________________________");
        System.out.println("searchResult: " + searchResult);
        System.out.println("_____________________________________");
                
        loadFlights(searchResult);
                
        if (searchResult == null) {
            return;
        }
        
    }
    
    
    @FXML
    private void loadFlights(SearchResult searchResult) {
        fetchedFlightsContainer.getChildren().clear(); 
        
        List<SearchResultCard> bestFlights = searchResult.getBestFlights();
        List<SearchResultCard> otherFlights = searchResult.getOtherFlights();
        
        if (!bestFlights.isEmpty()) {
            for (SearchResultCard flightDetails : bestFlights) {
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightSearchCard.fxml"));
                    Node flightCardNode = loader.load();

                    FlightSearchCardController controller = loader.getController();
                          
                    controller.setSearchCardFlightDetails(flightDetails);

                    fetchedFlightsContainer.getChildren().add(flightCardNode);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (!otherFlights.isEmpty()) {
            for (SearchResultCard flightDetails : otherFlights) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightSearchCard.fxml"));
                    Node flightCardNode = loader.load();

                    FlightSearchCardController controller = loader.getController();
                    controller.setSearchCardFlightDetails(flightDetails);

                    fetchedFlightsContainer.getChildren().add(flightCardNode);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
