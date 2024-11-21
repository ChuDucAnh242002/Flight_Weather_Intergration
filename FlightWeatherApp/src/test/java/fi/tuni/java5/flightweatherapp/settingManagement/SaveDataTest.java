
package fi.tuni.java5.flightweatherapp.settingManagement;


import fi.tuni.java5.flightweatherapp.flightDataAPI.Airport;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Flight;
import fi.tuni.java5.flightweatherapp.flightDataAPI.Layover;
import fi.tuni.java5.flightweatherapp.flightDataAPI.SearchResultCard;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * test for SaveData Class
 * @author Kalle Hirvijärvi
 */
public class SaveDataTest {
    
    // used to save JSON file during testing so its contents aren't changed
    private InfoCardStorage saved_data_f;
    private Preferences saved_data_p;
    private SaveData old_data;
    
    public SaveDataTest() {
    }
    
    @BeforeEach
    public void save_existing_data(){
        old_data = new SaveData();
        saved_data_f = old_data.get_favorites();
        saved_data_p = old_data.get_preferences();
    }
    
    @AfterEach
    public void put_back_existing_data(){
        old_data.write_data(saved_data_f, saved_data_p);
    }

    @Test
    /**
     * creates new flight, writes it to JSON and reads it back to check
     * if content remains the same
     * note: for some reason all dates are automaticly converted to EEST (curent timezone in finland)
     * for this reason this test might break when clocks are moved
     */
    public void testReadAndWrite() {
        
        List<Layover> layover_list = new ArrayList<>();
        
        int l_duration = 20;
        String layover_name = "layover1";
        String layover_id = "456";
                
        Layover new_layover = new Layover();
        new_layover.duration = l_duration;
        new_layover.name = layover_name;
        new_layover.id = layover_id;
                
        layover_list.add(new_layover);
        
        String dep_airport_name = "airport1";
        String dep_airport_id = "123";
        String dep_airport_time = "2024-09-18 14:20";
        String arr_airport_name = "airport2";
        String arr_airport_id = "124";
        String arr_airport_time = "2024-09-18 15:30";
        int flight_duration = 130;
        String airplane = "boeing 737";
        String airline = "finnair";
        String airline_logo = "https//:someurlforimnage.something";
        String travel_class = "economy";
        String flight_number = "123ABC";
        List<String> tickets_sold_by = new ArrayList<>();
        List<String> extentions = new ArrayList<>();
            
        Flight new_flight = new Flight();
        Airport dep_airport = new Airport();
        Airport arr_airport = new Airport();
        
        dep_airport.name = dep_airport_name;
        dep_airport.id = dep_airport_id;
        dep_airport.time = dep_airport_time;
        new_flight.departure_airport = dep_airport;
            
        arr_airport.name = arr_airport_name;
        arr_airport.id = arr_airport_id;
        arr_airport.time = arr_airport_time;
        new_flight.arrival_airport = arr_airport;
                
        new_flight.duration = flight_duration;
        new_flight.airplane = airplane;
        new_flight.airline = airline;
        new_flight.airline_logo = airline_logo;
        new_flight.travel_class = travel_class;
        new_flight.flight_number = flight_number;
        new_flight.ticket_also_sold_by = tickets_sold_by;
        new_flight.extensions = extentions;
        
        List<Flight> flight_list = new ArrayList<>();
        flight_list.add(new_flight);
        
        int total_duration = 150;
        int carbon_emissions = 1000;
        double  price = 199.95;
        String type = "sometype";
        String airline_logo_again = "https//:someurlforimnage.something";
        String departure_token = "6789";
            
            
        // create Flight obejct
        SearchResultCard new_flight_result = new SearchResultCard();
            
        new_flight_result.flights = flight_list;
        new_flight_result.layovers = layover_list;
            
        new_flight_result.total_duration = total_duration;
        new_flight_result.carbon_emission = carbon_emissions;
        new_flight_result.price = price;
        new_flight_result.type = type;
        new_flight_result.airline_logo = airline_logo_again;
        new_flight_result.departure_token = departure_token;
        
        InfoCardStorage fav = new InfoCardStorage();
        fav.set_new_element(new_flight_result);
        Preferences pref = new Preferences("USD", "F", 250.0, 1);
        old_data.write_data(fav, pref);
        
        SaveData test_data = new SaveData();
        
        pref = test_data.get_preferences();
        fav = test_data.get_favorites();
        
        SearchResultCard test_flight_card = fav.get_by_dep_time().get(0);
        Flight test_flight = test_flight_card.flights.get(0);
        Layover test_layover = test_flight_card.layovers.get(0);
        Airport test_dep_airport = test_flight.getDepartureAirport();
        Airport test_arr_airport = test_flight.getArrivalAirport();
        
        assertEquals("airport1", test_dep_airport.name);
        assertEquals("123", test_dep_airport.id);
        assertEquals("2024-09-18 14:20", test_dep_airport.time);
        
        assertEquals("airport2", test_arr_airport.name);
        assertEquals("124", test_arr_airport.id);
        assertEquals("2024-09-18 15:30", test_arr_airport.time);
        
        assertEquals(130, test_flight.duration);
        assertEquals("boeing 737", test_flight.airplane);
        assertEquals("finnair", test_flight.airline);
        assertEquals("https//:someurlforimnage.something", test_flight.airline_logo);
        assertEquals("economy", test_flight.travel_class);
        assertEquals("123ABC", test_flight.flight_number);
        
        assertTrue(test_flight.extensions.isEmpty());
        assertTrue(test_flight.extensions.isEmpty());
        
        assertEquals(20, test_layover.duration);
        assertEquals("layover1", test_layover.name);
        assertEquals("456", test_layover.id);
        
        assertEquals(150, test_flight_card.total_duration);
        assertEquals(1000, test_flight_card.carbon_emission);
        assertEquals(199.95, test_flight_card.price);
        assertEquals("https//:someurlforimnage.something", test_flight_card.airline_logo);
        assertEquals("6789", test_flight_card.departure_token);
        assertEquals("sometype", test_flight_card.type);
        
        assertEquals("USD", pref.get_currency());
        assertEquals("F", pref.get_temperature_unit());
        assertEquals(250.0, pref.get_max_price());
        assertEquals(1, pref.get_leyovers());
    }
}
