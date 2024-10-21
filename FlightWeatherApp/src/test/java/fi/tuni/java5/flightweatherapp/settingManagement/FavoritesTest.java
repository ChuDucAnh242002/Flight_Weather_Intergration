
package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.Flight;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for Favorites class
 * @author Kalle Hirvijärvi
 */
public class FavoritesTest {
    
    private Favorites fav;
    
    /**
     * creates favorites object with five flights
     */
    public FavoritesTest() {
        
        List<String> lay1 = Arrays.asList("pariisi", "lontoo");
        List<String> lay2 = Arrays.asList("pariisi", "moskova");
        List<String> lay3 = Arrays.asList();
        List<String> lay4 = Arrays.asList("tampere");
        List<String> lay5 = Arrays.asList("houston", "atlanta", "kansas city", "philadelphia");
        
        Flight flight1 = new Flight(2, null, null, 349.49, new ArrayList<String>(lay1), null, null, false, false);
        Flight flight2 = new Flight(3, null, null, 209.79, new ArrayList<String>(lay2), null, null, false, false);
        Flight flight3 = new Flight(5, null, null, 189.99, new ArrayList<String>(lay3), null, null, false, false);
        Flight flight4 = new Flight(1, null, null, 576.39, new ArrayList<String>(lay4), null, null, false, false);
        Flight flight5 = new Flight(4, null, null, 209.79, new ArrayList<String>(lay5), null, null, false, false);
        
        fav = new Favorites();
        
        fav.set_new_favorite(flight1);
        fav.set_new_favorite(flight2);
        fav.set_new_favorite(flight3);
        fav.set_new_favorite(flight4);
        fav.set_new_favorite(flight5);
    }

    @Test
    public void testGet_favorite_flights_by_recent() {
        
        TreeSet<Flight> flights = fav.get_favorite_flights_by_recent();
        int counter = 1;
        for (Flight flight : flights){
            assertEquals(counter, flight.get_flight_id());
            counter++;
        }
        
    }
    @Test
    public void testGet_favorite_by_price(){
        TreeSet<Flight> flights = fav.get_favorite_flights_by_price();
        int[] answers = {5, 3, 4, 2, 1};
        int counter = 0;
        for (Flight flight : flights){
            assertEquals(answers[counter], flight.get_flight_id());
            counter++;
        }
        
    }
    @Test
    public void testGet_favorites_by_layovers(){
        TreeSet<Flight> flights = fav.get_favorite_flights_by_layovers();
        int[] answers = {5, 1, 2, 3, 4};
        int counter = 0;
        for (Flight flight : flights){
            assertEquals(answers[counter], flight.get_flight_id());
            counter++;
        }
    }
}
