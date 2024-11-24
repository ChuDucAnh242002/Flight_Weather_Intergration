package fi.tuni.java5.flightweatherapp;

import fi.tuni.java5.flightweatherapp.airportDataAPI.AirportDataAPICall;
import fi.tuni.java5.flightweatherapp.airportDataAPI.AirportDataResponse;
import fi.tuni.java5.flightweatherapp.flightDataAPI.FlightDataAPICall;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResult;
import fi.tuni.java5.flightweatherapp.flightDataAPI.FlightDataRequest;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import fi.tuni.java5.flightweatherapp.weatherAPI.CurrentAndForecastWeatherResponse;
import fi.tuni.java5.flightweatherapp.weatherAPI.WeatherAPICall;
import java.io.IOException;
import java.text.DecimalFormat;
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
import java.time.temporal.ChronoUnit;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


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
            SetDepartureAiportInfo(defaultAirportCode, errorAirportName);
            
            AirportDataAPICall.departureAirport = null;
            
            return;
        }
        
        AirportDataAPICall.departureAirport = searchData.get(0);
        
        SetDepartureAiportInfo(AirportDataAPICall.departureAirport.getIata(), AirportDataAPICall.departureAirport.getName());
        
        if (isWeatherDataOutboundValid()) {
            updateWeatherData(true);
        }
        
        if (isWeatherDataReturnValid()) {
            updateWeatherData(false);
        }
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
            SetArrivalAiportInfo(defaultAirportCode, errorAirportName);
            
            AirportDataAPICall.arrivalAirport = null;
            
            return;
        }
        
        AirportDataAPICall.arrivalAirport = searchData.get(0);
        
        SetArrivalAiportInfo(AirportDataAPICall.arrivalAirport.getIata(), AirportDataAPICall.arrivalAirport.getName());
        
        if (isWeatherDataOutboundValid()) {
            updateWeatherData(true);
        }
        
        if (isWeatherDataReturnValid()) {
            updateWeatherData(false);
        }
    }
    
    @FXML
    private void OnOutboundDatePicked() {
        if (isWeatherDataOutboundValid()) {
            updateWeatherData(true);
        }
    }
    
    @FXML
    private void OnReturnDatePicked() {
        if (isWeatherDataReturnValid()) {
            updateWeatherData(false);
        }
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
    
    
    // Outbound - Departure
    @FXML
    private Pane outboundDeparturePane;
    
    @FXML
    private HBox outboundDepartureHBox;
    
    @FXML
    private Label outboundDepartureDateLabel;
    
    @FXML
    private Label outboundDepartureCityLabel;
    
    @FXML
    private Label outboundDepartureAvgTempLabel;
    
    @FXML
    private Label outboundDepartureHighTempLabel;
    
    @FXML
    private Label outboundDepartureLowTempLabel;
    
    @FXML
    private Label outboundDepartureWeatherDescLabel;
    
    @FXML
    private Label outboundDepartureWindSpeedLabel;
    
    @FXML
    private Label outboundDepartureUVIndexLabel;
    
    @FXML
    private ImageView outboundDepartureWeatherImageView;
    
    @FXML
    private ImageView outboundDepartureBackgroundImageView;
    
    // Outbound - Arrival
    @FXML
    private Pane outboundArrivalPane;
    
    @FXML
    private HBox outboundArrivalHBox;
    
    @FXML
    private Label outboundArrivalDateLabel;
    
    @FXML
    private Label outboundArrivalCityLabel;
    
    @FXML
    private Label outboundArrivalAvgTempLabel;
    
    @FXML
    private Label outboundArrivalHighTempLabel;
    
    @FXML
    private Label outboundArrivalLowTempLabel;
    
    @FXML
    private Label outboundArrivalWeatherDescLabel;
    
    @FXML
    private Label outboundArrivalWindSpeedLabel;
    
    @FXML
    private Label outboundArrivalUVIndexLabel;
    
    @FXML
    private ImageView outboundArrivalWeatherImageView;
    
    @FXML
    private ImageView outboundArrivalBackgroundImageView;
    
    CurrentAndForecastWeatherResponse outboundDepartureWeatherResponse;
    
    CurrentAndForecastWeatherResponse outboundArrivalWeatherResponse;
    
    CurrentAndForecastWeatherResponse returnDepartureWeatherResponse;
    
    CurrentAndForecastWeatherResponse returnArrivalWeatherResponse;
    
    private boolean isWeatherDataOutboundValid() {
        return (!AirportDataAPICall.isAnyAirportNull() && !isDatePickerValueNull() && isDatePickerValueLegal());
    }
    
    private boolean isWeatherDataReturnValid() {
        return (!AirportDataAPICall.isAnyAirportNull() && !(returnDatePicker.getValue() == null) && isDatePickerValueLegal());
    }
    
    private boolean isForcastDateValid(DatePicker datePicker) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) {
            return false;
        }
        
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromToday = today.plusDays(7);
        
        return !selectedDate.isAfter(sevenDaysFromToday);
        
    }
    
    public int getDaysFromToday(DatePicker datePicker) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) {
            throw new IllegalArgumentException("No date selected in the DatePicker.");
        }
        
        LocalDate today = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(today, selectedDate);
    }
    
    public String getFormattedDate(DatePicker datePicker) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) {
            throw new IllegalArgumentException("No date selected in the DatePicker.");
        }

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        return selectedDate.format(formatter);
    }
    
    private CurrentAndForecastWeatherResponse getWeatherData(String airportCode) {
        return WeatherAPICall.RequestCurrentAndForecastWeatherDataByAirportCode(airportCode);
    }
    
    private String getDegreeUnit(){
        if (WeatherAPICall.chosenUnit == "metric") {
            return "C";
        }
        
        return "F";
    }
    
    private void updateWeatherData(boolean isOutbound) {
        
        if (isOutbound) {
            Boolean isDateValid = isForcastDateValid(outboundDatePicker);
            
            outboundDeparturePane.setVisible(isDateValid);
            outboundDepartureHBox.setVisible(isDateValid);
            outboundArrivalPane.setVisible(isDateValid);
            outboundArrivalHBox.setVisible(isDateValid);
            
            if (!isDateValid) {
                // error window
                
                return;
            }
            
            int dateIndex = getDaysFromToday(outboundDatePicker);
            
            // Departure
            outboundDepartureDateLabel.setText(getFormattedDate(outboundDatePicker));
            
            outboundDepartureWeatherResponse = getWeatherData(AirportDataAPICall.departureAirport.getIata());
            
            outboundDepartureCityLabel.setText(AirportDataAPICall.departureAirport.getCity());
            
            outboundDepartureAvgTempLabel.setText(String.valueOf((int)outboundDepartureWeatherResponse.daily[dateIndex].temp.day) + "°" + getDegreeUnit());
            
            outboundDepartureHighTempLabel.setText(String.valueOf((int)outboundDepartureWeatherResponse.daily[dateIndex].temp.max) + "°");
            
            outboundDepartureLowTempLabel.setText(String.valueOf((int)outboundDepartureWeatherResponse.daily[dateIndex].temp.min) + "°");
            
            outboundDepartureWeatherDescLabel.setText(outboundDepartureWeatherResponse.daily[dateIndex].weather[0].main);
            
            outboundDepartureWindSpeedLabel.setText(outboundDepartureWeatherResponse.daily[dateIndex].weather[0].main);
            
            outboundDepartureWindSpeedLabel.setText(DecimalToStringConverter(outboundDepartureWeatherResponse.daily[dateIndex].wind_speed) + " m/s");
            
            outboundDepartureUVIndexLabel.setText("UV " + (int)outboundDepartureWeatherResponse.daily[dateIndex].uvi);
            
            Image outboundDepartureWeatherIcon = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherIconImagePath(outboundDepartureWeatherResponse.daily[dateIndex].weather[0].id)));
            outboundDepartureWeatherImageView.setImage(outboundDepartureWeatherIcon);
            
            Image outboundDepartureWeatherBackgroundImage = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherBackgroundImagePath(outboundDepartureWeatherResponse.daily[dateIndex].weather[0].id)));
            outboundDepartureBackgroundImageView.setImage(outboundDepartureWeatherBackgroundImage);
            
            // Arrival
            outboundArrivalDateLabel.setText(getFormattedDate(outboundDatePicker));
            
            outboundArrivalWeatherResponse = getWeatherData(AirportDataAPICall.arrivalAirport.getIata());
            
            outboundArrivalCityLabel.setText(AirportDataAPICall.arrivalAirport.getCity());
            
            outboundArrivalAvgTempLabel.setText(String.valueOf((int)outboundArrivalWeatherResponse.daily[dateIndex].temp.day) + "°" + getDegreeUnit());
            
            outboundArrivalHighTempLabel.setText(String.valueOf((int)outboundArrivalWeatherResponse.daily[dateIndex].temp.max) + "°");
            
            outboundArrivalLowTempLabel.setText(String.valueOf((int)outboundArrivalWeatherResponse.daily[dateIndex].temp.min) + "°");
            
            outboundArrivalWeatherDescLabel.setText(outboundArrivalWeatherResponse.daily[dateIndex].weather[0].main);
            
            outboundArrivalWindSpeedLabel.setText(outboundArrivalWeatherResponse.daily[dateIndex].weather[0].main);
            
            outboundArrivalWindSpeedLabel.setText(DecimalToStringConverter(outboundArrivalWeatherResponse.daily[dateIndex].wind_speed) + " m/s");
            
            outboundArrivalUVIndexLabel.setText("UV " + (int)outboundArrivalWeatherResponse.daily[dateIndex].uvi);
            
            var outboundArrivalWeatherIcon = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherIconImagePath(outboundArrivalWeatherResponse.daily[dateIndex].weather[0].id)));
            outboundArrivalWeatherImageView.setImage(outboundArrivalWeatherIcon);
            
            var outboundArrivalWeatherBackgroundImage = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherBackgroundImagePath(outboundArrivalWeatherResponse.daily[dateIndex].weather[0].id)));
            outboundArrivalBackgroundImageView.setImage(outboundArrivalWeatherBackgroundImage);
           
            return;
        }
        
        
        if (!isForcastDateValid(returnDatePicker)) {
            // Turn off windows 

            // error window

            return;
        }
    }
    
    private String DecimalToStringConverter(double value)
    {
        DecimalFormat df = new DecimalFormat("#.#");
        double formattedValue = Double.parseDouble(df.format(value));
        
        return String.valueOf(formattedValue);
    }
}
