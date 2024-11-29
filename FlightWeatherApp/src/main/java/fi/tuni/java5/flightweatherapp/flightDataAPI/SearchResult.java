package fi.tuni.java5.flightweatherapp.flightDataAPI;

import fi.tuni.java5.flightweatherapp.interfaces.ResponseInterface;

import java.util.ArrayList;
import java.util.List;
/**
 * The SearchResult is created from the response of Google Flight API
 * The Search Result will contains best flights and other flights 
 * in the form of SearchResultCard
 * 
 * @author Chu Duc Anh
 */
public class SearchResult implements ResponseInterface {
    public List<SearchResultCard> best_flights;
    public List<SearchResultCard> other_flights;

    public SearchResult() {
        this.best_flights = new ArrayList<>();
        this.other_flights = new ArrayList<>();
    }
    // Getter
    public List<SearchResultCard> getBestFlights() {
        return best_flights;
    }
    
    public List<SearchResultCard> getOtherFlights() {
        return other_flights;
    }
    
    public void addBestFlight(SearchResultCard bestFlight) {
        best_flights.add(bestFlight);
    }
    
    public void addOtherFlight(SearchResultCard otherFlight) {
        other_flights.add(otherFlight);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("FlightDataResponse Details: \n");

        // Append best_flights list
        sb.append("Best Flights: \n");
        if (best_flights != null && !best_flights.isEmpty()) {
            for (SearchResultCard flightData : best_flights) {
                sb.append(flightData.toString()).append("\n");
            }
        } else {
            sb.append("No best flights available.\n");
        }

        // Append other_flights list
        sb.append("Other Flights: \n");
        if (other_flights != null && !other_flights.isEmpty()) {
            for (SearchResultCard flightData : other_flights) {
                sb.append(flightData.toString()).append("\n");
            }
        } else {
            sb.append("No other flights available.\n");
        }

        return sb.toString();
    }
    
}
