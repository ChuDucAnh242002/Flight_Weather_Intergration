package fi.tuni.java5.flightweatherapp.flightDataAPI;

import java.util.List;

/**
 *
 * @author Chu Duc Anh
 */
public class SearchResultCard {
    public List<Flight> flights;
    public List<Layover> layovers;
    public int total_duration;
    public CarbonEmissions carbon_emissions;
    public double price;
    public String type;
    public String airline_logo;
    public List<String> extensions;
    public String departure_token;
    
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

    public CarbonEmissions getCarbonEmissions() {
        return carbon_emissions;
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

    public List<String> getExtensions() {
        return extensions;
    }

    public String getDepartureToken() {
        return departure_token;
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
        sb.append("Carbon Emissions: ").append(carbon_emissions != null ? carbon_emissions.toString() : "N/A").append("\n");
        sb.append("Price: ").append(price).append("\n");
        sb.append("Type: ").append(type != null ? type : "N/A").append("\n");
        sb.append("Airline Logo: ").append(airline_logo != null ? airline_logo : "N/A").append("\n");
    
        // Append extensions list
        sb.append("Extensions: \n");
        if (extensions != null && !extensions.isEmpty()) {
            for (String extension : extensions) {
                sb.append(extension).append("\n");
            }
        } else {
            sb.append("No extensions available.\n");
        }

        sb.append("Departure Token: ").append(departure_token != null ? departure_token : "N/A").append("\n");
    
        return sb.toString();
    }
}
