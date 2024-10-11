
package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.Flight;
import java.util.TreeSet;

/**
 *
 * @author Kalle Hirvijärvi
 */
public class Favorites {
    
    public TreeSet<Flight> flights_by_recent;
    public TreeSet<Flight> flights_by_price;
    public TreeSet<Flight> flights_by_layover;
    
    public Favorites(){
        flights_by_recent = new TreeSet<Flight>((left, right) -> 
            left.get_flight_id() - right.get_flight_id()
        );
    }
    
    public TreeSet<Flight> get_favorite_flights_by_recent(){
        return flights_by_recent;
    }
    
    public void set_new_favorite(Flight new_flight){
        flights_by_recent.add(new_flight);
    }
}
