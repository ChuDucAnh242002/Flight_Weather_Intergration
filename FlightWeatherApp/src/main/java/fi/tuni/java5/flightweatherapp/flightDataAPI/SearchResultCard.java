package fi.tuni.java5.flightweatherapp.flightDataAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * The SearchResultCard will contains data about multiple flights
 *
 * @author Chu Duc Anh
 */
public class SearchResultCard {
    public List<Flight> flights = new ArrayList<>();;
    public List<Layover> layovers = new ArrayList<>();;
    public int total_duration;
    public int carbon_emission;
    public double price;
    public String type;
    public String airline_logo;
    public String departure_token;
    public boolean isSaved = false;
    public String currency;
    
    // Getters
    public List<Flight> getFlights() {
        return flights;
    }

    public List<Layover> getLayovers() {
        return layovers;
    }

    public int getTotalDuration() {
        return total_duration;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getAirlineLogo() {
        return airline_logo;
    }
    
    public String getDepartureToken() {
        return departure_token;
    }
    
    public void setCarbonEmission(int carbon_emission){
        this.carbon_emission = carbon_emission;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currencyCode){
        this.currency = currencyCode;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("FlightData Details: \n");

        // Append flights list
        sb.append("Flights: \n");
        if (flights != null && !flights.isEmpty()) {
            for (Flight flight : flights) {
                sb.append(flight.toString()).append("\n");
            }
        } else {
            sb.append("No flights available.\n");
        }

        // Append layovers list
        sb.append("Layovers: \n");
        if (layovers != null && !layovers.isEmpty()) {
            for (Layover layover : layovers) {
                sb.append(layover.toString()).append("\n");
            }
        } else {
            sb.append("No layovers available.\n");
        }

        // Append other attributes
        sb.append("Total Duration: ").append(total_duration).append(" minutes\n");
        sb.append("Carbon emission: ").append(carbon_emission).append(" grams\n");
        sb.append("Price: ").append(price).append("\n");
        sb.append("Type: ").append(type != null ? type : "N/A").append("\n");
        sb.append("Airline Logo: ").append(airline_logo != null ? airline_logo : "N/A").append("\n");
        sb.append("Departure Token: ").append(departure_token != null ? departure_token : "N/A").append("\n");
        sb.append("isSaved: ").append(isSaved).append("\n");
        return sb.toString();
    }
}
