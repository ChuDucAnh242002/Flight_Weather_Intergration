package fi.tuni.java5.flightweatherapp.flightDataAPI;

import fi.tuni.java5.flightweatherapp.interfaces.ResponseInterface;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Chu Duc Anh
 */
public class FlightDataResponse implements ResponseInterface {
    public List<FlightData> best_flights;
    public List<FlightData> other_flights;

    public FlightDataResponse() {
        this.best_flights = new ArrayList<>();
        this.other_flights = new ArrayList<>();
    }
    // Getter
    public List<FlightData> getBestFlights() {
        return best_flights;
    }
    
    public List<FlightData> getOtherFlights() {
        return other_flights;
    }
    
    public void addBestFlight(FlightData bestFlight) {
        best_flights.add(bestFlight);
    }
    
    public void addOtherFlight(FlightData otherFlight) {
        other_flights.add(otherFlight);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("FlightDataResponse Details: \n");

        // Append best_flights list
        sb.append("Best Flights: \n");
        if (best_flights != null && !best_flights.isEmpty()) {
            for (FlightData flightData : best_flights) {
                sb.append(flightData.toString()).append("\n");
            }
        } else {
            sb.append("No best flights available.\n");
        }

        // Append other_flights list
        sb.append("Other Flights: \n");
        if (other_flights != null && !other_flights.isEmpty()) {
            for (FlightData flightData : other_flights) {
                sb.append(flightData.toString()).append("\n");
            }
        } else {
            sb.append("No other flights available.\n");
        }

        return sb.toString();
    }
    
}
