package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.flightDataAPI.Airport;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Flight;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * tests that InfoCardStorage orders flights correctly
 * @author Hirvijärvi
 */
public class InfoCardStorageTest {
    
    private InfoCardStorage fav;
    
    private double[] prices = {99.95, 449.99, 220.50, 309.90, 66.80};
    private int[] durations = {150, 459, 105, 140, 60};
    private String[] dep_times  = {"2024-09-18 14:20", "2024-09-18 14:59", "2024-09-18 12:59", "2024-09-23 12:59", "2024-05-23 12:59" };
    
    public InfoCardStorageTest() {
        fav = new InfoCardStorage();
        
        for (int i = 0 ; i < 5 ; i++){
            SearchResultCard new_card  = new SearchResultCard();
            Flight new_flight = new Flight();
            Airport new_airport = new Airport();
            
            new_airport.time = dep_times[i];
            
            new_flight.departure_airport = new_airport;
            List<Flight> flights = new ArrayList<>();
            flights.add(new_flight);
            
            new_card.flights = flights;
            new_card.price = prices[i];
            new_card.total_duration = durations[i];
            
            fav.set_new_element(new_card);
        }
    }

    @Test
    public void testGet_by_dep_time() {
        int[] answers = {4, 2, 0, 1, 3};
        List<SearchResultCard> result = fav.get_by_dep_time();
        for (int i = 0 ; i < 5 ; i++){
            assertEquals(dep_times[answers[i]], result.get(i).getFlights().get(0).getDepartureAirport().time);
        }
    }

    @Test
    public void testGet_by_price() {
        int[] answers = {4, 0, 2, 3, 1};
        List<SearchResultCard> result = fav.get_by_price();
        for (int i = 0 ; i < 5 ; i++){
            assertEquals(prices[answers[i]], result.get(i).getPrice());
        }
    }

    @Test
    public void testGet_by_flight_duration() {
        int[] answers = {4, 2, 3, 0, 1};
        List<SearchResultCard> result = fav.get_by_flight_duration();
        for (int i = 0 ; i < 5 ; i++){
            assertEquals(durations[answers[i]], result.get(i).getTotalDuration());
        }
    }
}