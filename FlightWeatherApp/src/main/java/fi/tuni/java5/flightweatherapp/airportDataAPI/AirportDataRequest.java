
package fi.tuni.java5.flightweatherapp.airportDataAPI;

import fi.tuni.java5.flightweatherapp.interfaces.RequestInterface;

/**
 *
 * The AirportDataRequest class contains the attributes
 * to request data from AirportDataAPICall class
 * 
 * @author Chu Duc Anh
 */
public class AirportDataRequest implements RequestInterface {
    private final String name;
    private final String iata;
    
    public AirportDataRequest() {
        this.name = "Helsinki";
        this.iata = "HEL";
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getIata() {
        return this.iata;
    }
}
