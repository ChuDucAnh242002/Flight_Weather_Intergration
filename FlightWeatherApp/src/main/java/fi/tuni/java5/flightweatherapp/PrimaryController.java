package fi.tuni.java5.flightweatherapp;

import fi.tuni.java5.flightweatherapp.airportDataAPI.AirportDataAPICall;
import fi.tuni.java5.flightweatherapp.airportDataAPI.AirportDataResponse;
import fi.tuni.java5.flightweatherapp.flightDataAPI.FlightDataAPICall;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResult;
import fi.tuni.java5.flightweatherapp.flightDataAPI.FlightDataRequest;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import fi.tuni.java5.flightweatherapp.settingManagement.InfoCardStorage;
import fi.tuni.java5.flightweatherapp.settingManagement.SaveData;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.time.temporal.ChronoUnit;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;


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
    private ToggleGroup currencyToggle;
    
    private String currency = "USD";
    
    @FXML
    private TextField maxPriceTextField;
    
    @FXML
    private VBox fetchedFlightsContainer;
    
    @FXML
    private Label stopsLabel;
    
    private int stops = 0; // default any number of stops
    
    private Boolean isStopsValueLegal() {
        /*
            0 - Any number of stops (default)
            1 - Nonstop only
            2 - 1 stop or fewer
            3 - 2 stops or fewer
        */
        return this.stops >= 0 && this.stops <= 3;
    }
    
    @FXML
    private void onStopsIncreaseButtonPressed() {
        if (!isStopsValueLegal()) {
            return;
        }
        
        if (this.stops == 3) {
            return;
        }
        
        this.stops++;
        
        stopsLabel.setText(String.valueOf(this.stops));
    }
    
    @FXML
    private void onStopsDecreaseButtonPressed() {
        if (!isStopsValueLegal()) {
            return;
        }
        
        if (this.stops == 0) {
            return;
        }
        
        this.stops--;
        
        stopsLabel.setText(String.valueOf(this.stops));
    } 
    private VBox searchNotification;
    
    @FXML
    private Label searchStatusLabel;
    
    @FXML
    private ImageView searchStatusImageView;
    
    @FXML
    private Button flightSearchButton;
    
    // Preference elements
    @FXML
    private Button seeMoreButton;
    
    @FXML
    private Button seeLessButton;
    
    @FXML
    private Button savedFlightButton;
    
    @FXML
    private Button goBackButton;
    
    @FXML
    private Button resetButton;
    
    @FXML
    private Pane maxPricePane;
    
    @FXML
    private Pane maxStopPane;
    
    @FXML
    private Line preferenceSeparateLine1;
    
    @FXML
    private Line preferenceSeparateLine2;
    
    @FXML
    public void initialize() {
        SetActiveAndInactiveAllWeatherData(false);
        
        ExpandOrCollapsePreference(false);
        
        goBackButton.setVisible(false);
        goBackButton.setManaged(false);
    }
    
    private void ExpandOrCollapsePreference(boolean isExpand) {
        seeLessButton.setVisible(isExpand);
        seeLessButton.setManaged(isExpand);
        
        seeMoreButton.setVisible(!isExpand);
        seeMoreButton.setManaged(!isExpand);
        
        maxPricePane.setVisible(isExpand);
        maxPricePane.setManaged(isExpand);
        
        maxStopPane.setVisible(isExpand);
        maxStopPane.setManaged(isExpand);
        
        resetButton.setVisible(isExpand);
        resetButton.setManaged(isExpand);
        
        preferenceSeparateLine1.setVisible(isExpand);
        preferenceSeparateLine1.setManaged(isExpand);
        
        preferenceSeparateLine2.setVisible(isExpand);
        preferenceSeparateLine2.setManaged(isExpand);
    }
    
    @FXML
    private void OnSeeMoreButtonPressed() {
        ExpandOrCollapsePreference(true);
    }
    
    @FXML
    private void OnSeeLessButtonPressed() {
        ExpandOrCollapsePreference(false);
    }
    
    private void OpenSavedFlight(boolean isOpen) {
        savedFlightButton.setVisible(!isOpen);
        savedFlightButton.setManaged(!isOpen);
        
        goBackButton.setVisible(isOpen);
        goBackButton.setManaged(isOpen);
        
        flightSearchButton.setDisable(isOpen);
    }
    
    @FXML
    private void OnSavedFlightButtonPressed() {
        OpenSavedFlight(true);
        
        fetchedFlightsContainer.getChildren().clear(); 
        
        SaveData savedFlights = new SaveData();
        InfoCardStorage favourites = savedFlights.get_favorites();
        List<SearchResultCard> searchResultCardList = favourites.get_by_flight_duration();
        
        if (!searchResultCardList.isEmpty()) {
            for (SearchResultCard flightDetails : searchResultCardList) {
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightSearchCard.fxml"));
                    Node flightCardNode = loader.load();

                    FlightSearchCardController controller = loader.getController();
                          
                    controller.setSearchCardFlightDetails(flightDetails, this.currency);

                    fetchedFlightsContainer.getChildren().add(flightCardNode);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    @FXML
    private void OnGoBackButtonPressed() {
        OpenSavedFlight(false);
    }
    
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
        
        if (!isDatePickerValueLegal()) {
            OpenErrorMessage(illegalDatePickerErrorMessage);
        }
        
        if (!isReturnDatePickerValueLegal()) {
            OpenErrorMessage(illegalReturnDatePickerErrorMessage);
        }
    }
    
    @FXML
    private void OnReturnDatePicked() {
        if (isWeatherDataReturnValid()) {
            updateWeatherData(false);
        }
        
        if (!isDatePickerValueLegal()) {
            OpenErrorMessage(illegalDatePickerErrorMessage);
        }
        
        if (!isReturnDatePickerValueLegal()) {
            OpenErrorMessage(illegalReturnDatePickerErrorMessage);
        }
    }
    
    // Search flight section
    
    String missingAirportErrorMessage = "Please add both Departure and Arrival airports!";
    
    String missingDatePickerErrorMessage = "Please add at least Outbound date!";
    
    String illegalDatePickerErrorMessage = "One or more selected date is in the past!";
    
    String illegalReturnDatePickerErrorMessage = "Return date is before Outbound date!";
    
    String searchingMessage = "Please enter all information to start searching!";
    
    String airportSearchMessage = "Please enter Airport Code or City name!";
    
    String emptySearchMessage = "Sorry, there is no available flight!";
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
    private void OpenErrorMessage(String messageString) {
        if (searchNotification != null) {
            searchNotification.setVisible(true);
        }
        
        searchStatusLabel.setText(messageString);
            
        Image image = new Image(getClass().getResourceAsStream("icons/Attention Circle Icon.png"));
        searchStatusImageView.setImage(image);
    }
    
    private void OpenAndCloseSearchingMessage(boolean isOpen) {
        if (searchNotification != null) {
            searchNotification.setVisible(isOpen);
        }
        
        if (isOpen) {
            searchStatusLabel.setText(searchingMessage);
            
            Image image = new Image(getClass().getResourceAsStream("icons/Searching Icon.png"));
            searchStatusImageView.setImage(image);
        }
    }
    
    private boolean isOutboundDatePickerValueNull() {
        return outboundDatePicker.getValue() == null;
    }
    
    private boolean isReturnDatePickerValueNull() {
        return returnDatePicker.getValue() == null;
    }
    
    private boolean isDatePickerValueLegal() {
        LocalDate today = LocalDate.now();
        
        LocalDate outboundDate = outboundDatePicker.getValue();
        
        LocalDate returnDate = returnDatePicker.getValue();
        
        if (returnDate == null) {
            return !(outboundDate.isBefore(today));
        } else if (outboundDate == null) {
            return !(returnDate.isBefore(today));
        } else {
            return !(outboundDate.isBefore(today) || returnDate.isBefore(today));
        }
    }
    
     @FXML
    public void currencyToggleListener() {
        
        // Add listener to detect currency changes
        currencyToggle.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            
            if (newToggle != null) {
                RadioButton selectedRadioButton = (RadioButton) newToggle;
                String selectedCurrencyCode = getSelectedCurrencyCode(selectedRadioButton);
                this.currency = selectedCurrencyCode;
            }
        });
    }

    private String getSelectedCurrencyCode(RadioButton selectedRadioButton) {
        // Retrieve the currency code stored in the userData of the selected radio button
        return (String) selectedRadioButton.getUserData();
    }
    
    private int maxPriceValue = Integer.MAX_VALUE;
    
    private void getMaxPriceFromTextField() {
        
        String maxPriceString = maxPriceTextField.getText();
        Integer maxPriceInput = this.maxPriceValue;
        if (!maxPriceString.equals("")) {
            maxPriceInput = Integer.parseInt(maxPriceTextField.getText());
            this.maxPriceValue = maxPriceInput;
        }
    }
    
    private boolean isReturnDatePickerValueLegal() {        
        LocalDate outboundDate = outboundDatePicker.getValue();
        
        LocalDate returnDate = returnDatePicker.getValue();
        
        if (returnDate == null || outboundDate == null) {
            return true;
        } else {
            return !(returnDate.isBefore(outboundDate));
        }
    }
    
    @FXML
    private void OnSearchFlightButtonPressed() {

        currencyToggleListener();
        getMaxPriceFromTextField();
        System.out.println("Currency: " + this.currency);
        System.out.println("Max Price: " + this.maxPriceValue);
        
        if (AirportDataAPICall.isAnyAirportNull()) {
            OpenErrorMessage(missingAirportErrorMessage);
            
            return;
        }
                
        if (isOutboundDatePickerValueNull()) {
            OpenErrorMessage(missingDatePickerErrorMessage);
            
            return;
        }
                
        if (!isDatePickerValueLegal()) {
            OpenErrorMessage(illegalDatePickerErrorMessage);
            
            return;
        }
        
        if (!isReturnDatePickerValueLegal()) {
            OpenErrorMessage(illegalReturnDatePickerErrorMessage);
            
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
                                childPassengerAmount,
                                currency,
                                maxPriceValue,
                                stops
                                );
        } else {
            String returnDateString = returnDatePicker.getValue().format(formatter);
            
            FlightDataAPICall.flightDataRequest = new FlightDataRequest(
                                AirportDataAPICall.departureAirport.getIata(), 
                                AirportDataAPICall.arrivalAirport.getIata(),
                                outboundDateString,
                                returnDateString,
                                adultPassengerAmount,
                                childPassengerAmount,
                                currency,
                                maxPriceValue,
                                stops
                                );
        }
                
        SearchResult searchResult = FlightDataAPICall.RequestFlightDataAPI();
        /*
        System.out.println("_____________________________________");
        System.out.println("searchResult: " + searchResult);
        System.out.println("_____________________________________");
          */      
                
        if (searchResult == null) {
            OpenErrorMessage(emptySearchMessage);
            
            return;
        }
        loadFlights(searchResult);
        
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
                          
                    controller.setSearchCardFlightDetails(flightDetails, this.currency);

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
                    controller.setSearchCardFlightDetails(flightDetails, this.currency);

                    fetchedFlightsContainer.getChildren().add(flightCardNode);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    // Weather update section
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
    
    // Return - Departure
    @FXML
    private Pane returnDeparturePane;
    
    @FXML
    private HBox returnDepartureHBox;
    
    @FXML
    private Label returnDepartureDateLabel;
    
    @FXML
    private Label returnDepartureCityLabel;
    
    @FXML
    private Label returnDepartureAvgTempLabel;
    
    @FXML
    private Label returnDepartureHighTempLabel;
    
    @FXML
    private Label returnDepartureLowTempLabel;
    
    @FXML
    private Label returnDepartureWeatherDescLabel;
    
    @FXML
    private Label returnDepartureWindSpeedLabel;
    
    @FXML
    private Label returnDepartureUVIndexLabel;
    
    @FXML
    private ImageView returnDepartureWeatherImageView;
    
    @FXML
    private ImageView returnDepartureBackgroundImageView;
    
    // Return - Arrival
    @FXML
    private Pane returnArrivalPane;
    
    @FXML
    private HBox returnArrivalHBox;
    
    @FXML
    private Label returnArrivalDateLabel;
    
    @FXML
    private Label returnArrivalCityLabel;
    
    @FXML
    private Label returnArrivalAvgTempLabel;
    
    @FXML
    private Label returnArrivalHighTempLabel;
    
    @FXML
    private Label returnArrivalLowTempLabel;
    
    @FXML
    private Label returnArrivalWeatherDescLabel;
    
    @FXML
    private Label returnArrivalWindSpeedLabel;
    
    @FXML
    private Label returnArrivalUVIndexLabel;
    
    @FXML
    private ImageView returnArrivalWeatherImageView;
    
    @FXML
    private ImageView returnArrivalBackgroundImageView;
    
    @FXML
    private VBox outboundWeatherErrorVBox;
    
    @FXML
    private VBox returnWeatherErrorVBox;
    
    @FXML
    private VBox weatherGuideVBox;
    
    @FXML
    private Line weatherSeperateLine;
    
    // Static responses
    CurrentAndForecastWeatherResponse outboundDepartureWeatherResponse;
    
    CurrentAndForecastWeatherResponse outboundArrivalWeatherResponse;
    
    CurrentAndForecastWeatherResponse returnDepartureWeatherResponse;
    
    CurrentAndForecastWeatherResponse returnArrivalWeatherResponse;
    
    private boolean isWeatherDataOutboundValid() {
        return (!AirportDataAPICall.isAnyAirportNull() && !isOutboundDatePickerValueNull() && isDatePickerValueLegal());
    }
    
    private boolean isWeatherDataReturnValid() {
        return (!AirportDataAPICall.isAnyAirportNull() && !isReturnDatePickerValueNull() && isDatePickerValueLegal());
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
        weatherGuideVBox.setVisible(false);
        
        if (isOutbound) {
            Boolean isDateValid = isForcastDateValid(outboundDatePicker);
            
            if (returnDeparturePane.isVisible()) {
                weatherSeperateLine.setVisible(true);
                weatherSeperateLine.setManaged(true);
            }
            
            outboundDeparturePane.setVisible(isDateValid);
            outboundDepartureHBox.setVisible(isDateValid);
            outboundArrivalPane.setVisible(isDateValid);
            outboundArrivalHBox.setVisible(isDateValid);
            
            outboundDeparturePane.setManaged(isDateValid);
            outboundDepartureHBox.setManaged(isDateValid);
            outboundArrivalPane.setManaged(isDateValid);
            outboundArrivalHBox.setManaged(isDateValid);
            
            outboundWeatherErrorVBox.setVisible(!isDateValid);
            outboundWeatherErrorVBox.setManaged(!isDateValid);
            
            if (!isDateValid) {
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
            
            Image outboundArrivalWeatherIcon = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherIconImagePath(outboundArrivalWeatherResponse.daily[dateIndex].weather[0].id)));
            outboundArrivalWeatherImageView.setImage(outboundArrivalWeatherIcon);
            
            Image outboundArrivalWeatherBackgroundImage = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherBackgroundImagePath(outboundArrivalWeatherResponse.daily[dateIndex].weather[0].id)));
            outboundArrivalBackgroundImageView.setImage(outboundArrivalWeatherBackgroundImage);
           
            return;
        }
        
        Boolean isDateValid = isForcastDateValid(returnDatePicker);
        
        if (outboundDeparturePane.isVisible()) {
            weatherSeperateLine.setVisible(true);
            weatherSeperateLine.setManaged(true);
        }
        
        returnDeparturePane.setVisible(isDateValid);
        returnDepartureHBox.setVisible(isDateValid);
        returnArrivalPane.setVisible(isDateValid);
        returnArrivalHBox.setVisible(isDateValid);
        
        returnDeparturePane.setManaged(isDateValid);
        returnDepartureHBox.setManaged(isDateValid);
        returnArrivalPane.setManaged(isDateValid);
        returnArrivalHBox.setManaged(isDateValid);
        
        returnWeatherErrorVBox.setVisible(!isDateValid);
        returnWeatherErrorVBox.setManaged(!isDateValid);
        
        if (!isDateValid) {
            return;
        }
        
        int dateIndex = getDaysFromToday(returnDatePicker);

        // Departure
        returnDepartureDateLabel.setText(getFormattedDate(returnDatePicker));

        returnDepartureWeatherResponse = getWeatherData(AirportDataAPICall.departureAirport.getIata());

        returnDepartureCityLabel.setText(AirportDataAPICall.departureAirport.getCity());

        returnDepartureAvgTempLabel.setText(String.valueOf((int)returnDepartureWeatherResponse.daily[dateIndex].temp.day) + "°" + getDegreeUnit());

        returnDepartureHighTempLabel.setText(String.valueOf((int)returnDepartureWeatherResponse.daily[dateIndex].temp.max) + "°");

        returnDepartureLowTempLabel.setText(String.valueOf((int)returnDepartureWeatherResponse.daily[dateIndex].temp.min) + "°");

        returnDepartureWeatherDescLabel.setText(returnDepartureWeatherResponse.daily[dateIndex].weather[0].main);

        returnDepartureWindSpeedLabel.setText(returnDepartureWeatherResponse.daily[dateIndex].weather[0].main);

        returnDepartureWindSpeedLabel.setText(DecimalToStringConverter(returnDepartureWeatherResponse.daily[dateIndex].wind_speed) + " m/s");

        returnDepartureUVIndexLabel.setText("UV " + (int)returnDepartureWeatherResponse.daily[dateIndex].uvi);

        Image returnDepartureWeatherIcon = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherIconImagePath(returnDepartureWeatherResponse.daily[dateIndex].weather[0].id)));
        returnDepartureWeatherImageView.setImage(returnDepartureWeatherIcon);

        Image returnDepartureWeatherBackgroundImage = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherBackgroundImagePath(returnDepartureWeatherResponse.daily[dateIndex].weather[0].id)));
        returnDepartureBackgroundImageView.setImage(returnDepartureWeatherBackgroundImage);

        // Arrival
        returnArrivalDateLabel.setText(getFormattedDate(returnDatePicker));

        returnArrivalWeatherResponse = getWeatherData(AirportDataAPICall.arrivalAirport.getIata());

        returnArrivalCityLabel.setText(AirportDataAPICall.arrivalAirport.getCity());

        returnArrivalAvgTempLabel.setText(String.valueOf((int)returnArrivalWeatherResponse.daily[dateIndex].temp.day) + "°" + getDegreeUnit());

        returnArrivalHighTempLabel.setText(String.valueOf((int)returnArrivalWeatherResponse.daily[dateIndex].temp.max) + "°");

        returnArrivalLowTempLabel.setText(String.valueOf((int)returnArrivalWeatherResponse.daily[dateIndex].temp.min) + "°");

        returnArrivalWeatherDescLabel.setText(returnArrivalWeatherResponse.daily[dateIndex].weather[0].main);

        returnArrivalWindSpeedLabel.setText(returnArrivalWeatherResponse.daily[dateIndex].weather[0].main);

        returnArrivalWindSpeedLabel.setText(DecimalToStringConverter(returnArrivalWeatherResponse.daily[dateIndex].wind_speed) + " m/s");

        returnArrivalUVIndexLabel.setText("UV " + (int)returnArrivalWeatherResponse.daily[dateIndex].uvi);

        Image returnArrivalWeatherIcon = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherIconImagePath(returnArrivalWeatherResponse.daily[dateIndex].weather[0].id)));
        returnArrivalWeatherImageView.setImage(returnArrivalWeatherIcon);

        Image returnArrivalWeatherBackgroundImage = new Image(getClass().getResourceAsStream(WeatherAPICall.WeatherBackgroundImagePath(returnArrivalWeatherResponse.daily[dateIndex].weather[0].id)));
        returnArrivalBackgroundImageView.setImage(returnArrivalWeatherBackgroundImage);
    }
    
    private void SetActiveAndInactiveAllWeatherData(boolean isActive) {
        outboundDeparturePane.setVisible(isActive);
        outboundDepartureHBox.setVisible(isActive);
        outboundArrivalPane.setVisible(isActive);
        outboundArrivalHBox.setVisible(isActive);

        outboundDeparturePane.setManaged(isActive);
        outboundDepartureHBox.setManaged(isActive);
        outboundArrivalPane.setManaged(isActive);
        outboundArrivalHBox.setManaged(isActive);

        outboundWeatherErrorVBox.setVisible(isActive);
        outboundWeatherErrorVBox.setManaged(isActive);
        
        returnDeparturePane.setVisible(isActive);
        returnDepartureHBox.setVisible(isActive);
        returnArrivalPane.setVisible(isActive);
        returnArrivalHBox.setVisible(isActive);
        
        returnDeparturePane.setManaged(isActive);
        returnDepartureHBox.setManaged(isActive);
        returnArrivalPane.setManaged(isActive);
        returnArrivalHBox.setManaged(isActive);
        
        returnWeatherErrorVBox.setVisible(isActive);
        returnWeatherErrorVBox.setManaged(isActive);
    }
    
    private String DecimalToStringConverter(double value)
    {
        DecimalFormat df = new DecimalFormat("#.#");
        double formattedValue = Double.parseDouble(df.format(value));
        
        return String.valueOf(formattedValue);
    }
}
