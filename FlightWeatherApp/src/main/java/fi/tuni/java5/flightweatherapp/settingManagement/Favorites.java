
package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.Flight;
import java.util.TreeSet;

/**
 * stores favorite flights in TreeSets
 * @author Kalle Hirvijärvi
 */
public class Favorites {
    
    // flights in the order flightID (acsending)
    private TreeSet<Flight> flights_by_recent;
    
    // flights in the order of price (acsending)
    // secondarily in the order flightID (acsending)
    private TreeSet<Flight> flights_by_price;
    
    // flights in the order of number of layovers (acsending)
    // secondarily in the order flightID (acsending)
    private TreeSet<Flight> flights_by_layovers;
    
    // defines comparison fuctions to the TreeSets
    public Favorites(){
        
        flights_by_recent = new TreeSet<Flight>((left, right) -> 
            left.get_flight_id() - right.get_flight_id()
        );
        flights_by_price = new TreeSet<Flight>((left, right) -> {
            if (Double.compare(left.get_price(),right.get_price()) != 0){               
                return Double.compare(left.get_price(),right.get_price());
            }
            else {
                return left.get_flight_id() - right.get_flight_id();
            }
        });
        flights_by_layovers = new TreeSet<Flight>((left, right) -> {
            if (left.get_number_of_layovers() != right.get_number_of_layovers()){
                return left.get_number_of_layovers() - right.get_number_of_layovers();
            }
            else {
                return left.get_flight_id() - right.get_flight_id();
            }
        }); 
    }
    /**
     * @return flights in the order of flightID
     */
    public TreeSet<Flight> get_favorite_flights_by_recent(){
        return flights_by_recent;
    }
    /**
     * @return flights in the order of price
     */
    public TreeSet<Flight> get_favorite_flights_by_price(){
        return flights_by_price;
    }
    /**
     * @return flights in the order of number of layovers
     */
    public TreeSet<Flight> get_favorite_flights_by_layovers(){
        return flights_by_layovers;
    }
    /**
     * adds new flight to all TreeSets
     * @param new_flight new flight to be stored
     */
    public void set_new_favorite(Flight new_flight){
        flights_by_recent.add(new_flight);
        flights_by_price.add(new_flight);
        flights_by_layovers.add(new_flight);
    }
}
