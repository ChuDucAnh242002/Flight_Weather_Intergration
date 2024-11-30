package fi.tuni.java5.flightweatherapp.flightDataAPI;

import java.util.List;
/**
 *
 * @author Chu Duc Anh
 */
public class Flight {
    public Airport departure_airport;
    public Airport arrival_airport;
    public int duration;
    public String airplane;
    public String airline;
    public String airline_logo;
    public String travel_class;
    public String flight_number;
    public List<String> ticket_also_sold_by;
    public String legroom;
    public List<String> extensions;
    public boolean overnight = false;
    public boolean often_delayed_by_over_30_min = false;

    // Getters
    public Airport getDepartureAirport() {
        return departure_airport;
    }

    public Airport getArrivalAirport() {
        return arrival_airport;
    }

    public int getDuration() {
        return duration;
    }

    public String getAirplane() {
        return airplane;
    }

    public String getAirline() {
        return airline;
    }

    public String getAirlineLogo() {
        return airline_logo;
    }

    public String getTravelClass() {
        return travel_class;
    }

    public String getFlightNumber() {
        return flight_number;
    }

    public List<String> getTicketAlsoSoldBy() {
        return ticket_also_sold_by;
    }

    public String getLegroom() {
        return legroom;
    }
    
    public boolean getOvernight() {
        return overnight;
    }
    
    public boolean getDelayed() {
        return often_delayed_by_over_30_min;
    }

    public List<String> getExtensions() {
        return extensions;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("Flight Details: \n");
    
        sb.append("Departure Airport: ").append(departure_airport != null ? departure_airport.toString() : "N/A").append("\n");
        sb.append("Arrival Airport: ").append(arrival_airport != null ? arrival_airport.toString() : "N/A").append("\n");
        sb.append("Duration: ").append(duration).append(" minutes\n");
        sb.append("Airplane: ").append(airplane != null ? airplane : "N/A").append("\n");
        sb.append("Airline: ").append(airline != null ? airline : "N/A").append("\n");
        sb.append("Airline Logo: ").append(airline_logo != null ? airline_logo : "N/A").append("\n");
        sb.append("Travel Class: ").append(travel_class != null ? travel_class : "N/A").append("\n");
        sb.append("Flight Number: ").append(flight_number != null ? flight_number : "N/A").append("\n");

        // Handling List<String> ticket_also_sold_by
        if (ticket_also_sold_by != null && !ticket_also_sold_by.isEmpty()) {
            sb.append("Ticket Also Sold By: ").append(String.join(", ", ticket_also_sold_by)).append("\n");
        } else {
            sb.append("Ticket Also Sold By: N/A\n");
        }

        sb.append("Legroom: ").append(legroom != null ? legroom : "N/A").append("\n");

        // Handling List<String> extensions
        if (extensions != null && !extensions.isEmpty()) {
            sb.append("Extensions: ").append(String.join(", ", extensions)).append("\n");
        } else {
            sb.append("Extensions: N/A\n");
        }

        return sb.toString();
    }
}
