/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.java5.flightweatherapp.flightDataAPI;

/**
 * The `FlightDataRequest` class represents a request for flight data
 * It contains departure_id, arrival_id, outbound Date, and more parameters for the request.
 * 
 * @author Chu Duc Anh
 */
public class FlightDataRequest {
    private String departureId;
    private String arrivalId;
    private String currency = "USD";
    private int type = 1;
    private String outboundDate;
    private String returnDate;
    private int adults = 1;
    private int children = 0;
    private int stops = 0;
    private double maxPrice = 0;
    private String engine = "google_flights";
    private String apiKey = "0c0008d1a14b7681f19f1f6154db1ecc9167ebe938399881a88f9fedc0a09790";
    
    public FlightDataRequest() {
        this.departureId = "PEK";
        this.arrivalId = "AUS";
        this.outboundDate = "2024-10-18";
        this.returnDate = "2024-10-20";
    }
    /**
     * Initialize the FlightDataRequest
     * Uses when the flight is one way trip (type is 1)
     * 
     * @param departureId Airport departure's IATA code (For example: "PEK", "HEL")
     * @param arrivalId Airport arrival's IATA code (For example: "AUS", "CDG")
     * @param outboundDate date type yyyy-mm-dd
     */
    public FlightDataRequest(String departureId, String arrivalId, String outboundDate) {
        this.departureId = departureId;
        this.arrivalId = arrivalId;
        this.type = 2;
        this.outboundDate = outboundDate;
    }
    
    /**
     * Initialize the FlightDataRequest
     * Uses when the flight is round trip (type is 1)
     * 
     * @param departureId Airport departure's IATA code (For example: "PEK", "HEL")
     * @param arrivalId Airport arrival's IATA code (For example: "AUS", "CDG")
     * @param outboundDate date type yyyy-mm-dd
     * @param returnDate date type yyyy-mm-dd
     */
    public FlightDataRequest(String departureId, String arrivalId, String outboundDate, String returnDate) {
        this.departureId = departureId;
        this.arrivalId = arrivalId;
        this.outboundDate = outboundDate;
        this.returnDate = returnDate;
    }
    
    /**
     * Initialize the FlightDataRequest with all attributes
     * 
     * @param departureId Airport departure's IATA code (For example: "PEK", "HEL")
     * @param arrivalId Airport arrival's IATA code (For example: "AUS", "CDG")
     * @param currency "USD" default, "EUR", "GBP"
     * @param type 1: Round-trip(default), 2: One way, 3: Multi-city
     * @param outboundDate date type yyyy-mm-dd
     * @param returnDate date type yyyy-mm-dd
     * @param adults integer, 1 default
     * @param children integer, 0 default
     * @param stops integer, 0: Any number of stops (default), 1: Nonstop only, 2: 1 stop or fewer, 3: 2 stops or fewer
     * @param maxPrice double, unlimited (default)
     */
    
    public FlightDataRequest(String departureId, String arrivalId, String currency, int type, 
                             String outboundDate, String returnDate, int adults, int children, 
                             int stops, double maxPrice) {
        this.departureId = departureId;
        this.arrivalId = arrivalId;
        this.currency = currency;
        this.type = type;
        this.outboundDate = outboundDate;
        this.returnDate = returnDate;
        this.adults = adults;
        this.children = children;
        this.stops = stops;
        this.maxPrice = maxPrice;
    }
    
    public String getDepartureId() {
        return departureId;
    }

    public String getArrivalId() {
        return arrivalId;
    }

    public String getCurrency() {
        return currency;
    }

    public int getType() {
        return type;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int getStops() {
        return stops;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public String getEngine() {
        return engine;
    }

    public String getApiKey() {
        return apiKey;
    }
    
    public void setDepartureId(String departureId) {
        this.departureId = departureId;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
}
