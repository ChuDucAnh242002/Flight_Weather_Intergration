package fi.tuni.java5.flightweatherapp.airportDataAPI;

/**
 *
 * @author ASUS
 */
public class AirportDataResponse {
    private String iata_code;
    private double lat;
    private double lon;
    
    public AirportDataResponse(){
        this.iata_code = "HEL";
        this.lat = 60.318363;
        this.lon = 24.963341;
    }
    
    public AirportDataResponse(String iata_code, double lat, double lon) {
        this.iata_code = iata_code;
        this.lat = lat;
        this.lon = lon;
    }
    
    public String getIataCode(){
        return this.iata_code;
    }
    
    public double getLat(){
        return this.lat;
    }
    
    public double getLon(){
        return this.lon;
    }
}
