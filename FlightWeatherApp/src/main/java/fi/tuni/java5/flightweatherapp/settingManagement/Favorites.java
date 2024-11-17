
package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * stores favorite flights in three different TreeSets
 * for different orders
 * @author Kalle Hirvijärvi
 */
public class Favorites {
    
    private TreeSet<SearchResultCard> flights_by_departure;
    
    private TreeSet<SearchResultCard> flights_by_price;
    
    private TreeSet<SearchResultCard> flights_by_duration;
    
    // constructor defines comparison fuctions to the TreeSets
    public Favorites(){
        // primarily sort by departure time
        // secondarily sort by total duartion
        flights_by_departure = new TreeSet<>((left, right) -> {
            if (left.getFlights().get(0) .getDepartureAirport().getTime().compareTo(right.getFlights().get(0).getDepartureAirport().getTime())!= 0){
                return left.getFlights().get(0).getDepartureAirport().getTime().compareTo(right.getFlights().get(0).getDepartureAirport().getTime());
            }
            else {
                return left.getTotalDuration() - right.getTotalDuration();
            }
        });
        // primarily sort by price
        // secondarily sort by total duartion
        flights_by_price = new TreeSet<>((left, right) -> {
            if (Double.compare(left.getPrice(), right.getPrice()) != 0){
                if (left.getPrice() < right.getPrice()){
                    return -1;
                }
                else {
                    return 1;
                }
            }
            else {
                return left.getTotalDuration() - right.getTotalDuration();
            }
        });
        // primarily sort by total duration
        // secondarily sort by departure time
        flights_by_duration = new TreeSet<>((left, right) -> {
           if (left.getTotalDuration() == right.getTotalDuration()){
               return left.getFlights().get(0).getDepartureAirport().getTime().compareTo(right.getFlights().get(0).getDepartureAirport().getTime());
           }
           else {
               return left.getTotalDuration() - right.getTotalDuration();
           }
        });
    }
    /**
     * @return favorites by departure as List
     */
    public List<SearchResultCard> get_favorite_flights_by_dep_time(){
        return flights_by_departure.stream().collect(Collectors.toList());
    }
    /**
     * @return avorites by price as List
     */
    public List<SearchResultCard> get_favorite_flights_by_price(){
        return flights_by_price.stream().collect(Collectors.toList());
    }
    /**
     * @return avorites by duratino as List
     */
    public List<SearchResultCard> get_favorite_flights_by_flight_duration(){
        return flights_by_duration.stream().collect(Collectors.toList());
    }
    /**
     * adds new flight to all TreeSets
     * @param new_flight new flight to be stored
     */
    public void set_new_favorite(SearchResultCard new_flight){
        flights_by_departure.add(new_flight);
        flights_by_price.add(new_flight);
        flights_by_duration.add(new_flight);
    }
}
