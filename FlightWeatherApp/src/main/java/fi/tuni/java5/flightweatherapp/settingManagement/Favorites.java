
package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Airport;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Flight;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Layover;
import java.util.TreeSet;

/**
 * stores favorite flights in TreeSets
 * @author Kalle Hirvijärvi
 */
public class Favorites {
    
    // flights in the order flightID (acsending)
    private TreeSet<SearchResultCard> flights_by_departure;
    
    // flights in the order of price (acsending)
    // secondarily in the order flightID (acsending)
    private TreeSet<SearchResultCard> flights_by_price;
    
    // flights in the order of number of layovers (acsending)
    // secondarily in the order flightID (acsending)
    private TreeSet<SearchResultCard> flights_by_duration;
    
    // defines comparison fuctions to the TreeSets
    public Favorites(){
        flights_by_departure = new TreeSet<>((left, right) -> {
            if (left.getFlights().get(0) .getDepartureAirport().getTime().compareTo(right.getFlights().get(0).getDepartureAirport().getTime())!= 0){
                return left.getFlights().get(0).getDepartureAirport().getTime().compareTo(right.getFlights().get(0).getDepartureAirport().getTime());
            }
            else {
                return left.getTotalDuration() - right.getTotalDuration();
            }
        });
    }
    /**
     * @return flights in the order of flightID
     */
    public TreeSet<SearchResultCard> get_favorite_flights_by_dep_time(){
        return flights_by_departure;
    }
    /**
     * @return flights in the order of price
     */
    public TreeSet<SearchResultCard> get_favorite_flights_by_price(){
        return flights_by_price;
    }
    /**
     * @return flights in the order of number of layovers
     */
    public TreeSet<SearchResultCard> get_favorite_flights_by_flight_duration(){
        return flights_by_duration;
    }
    /**
     * adds new flight to all TreeSets
     * @param new_flight new flight to be stored
     */
    public void set_new_favorite(SearchResultCard new_flight){
        flights_by_departure.add(new_flight);
        //flights_by_price.add(new_flight);
        //flights_by_duration.add(new_flight);
    }
}
