
package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.Flight;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Favorites saved_data_f;
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
        Favorites new_fav = new Favorites();
        
        
        Flight new_flight = new Flight(
                1234, 
                "Tukholma", 
                "Helsinki", 
                100.00, 
                new ArrayList<String>(Arrays.asList("Turku")), 
                old_data.to_date("2024-10-20 00:30:00 EEST"),
                old_data.to_date("2024-10-20 02:00:00 EEST"),
                true,
                false);
        new_fav.set_new_favorite(new_flight);
        Preferences new_pref = new Preferences(Currency.EURO, 200.0, 1);
        
        old_data.write_data(new_fav, new_pref);
        
        
        SaveData data = new SaveData();
        Favorites fav = data.get_favorites();
                
        assertFalse(fav.get_favorite_flights_by_recent().isEmpty());
        Flight test = fav.get_favorite_flights_by_recent().first();
        assertEquals(1234, test.get_flight_id());
        assertEquals("Tukholma", test.get_departure_airport());
        assertEquals("Helsinki", test.get_destination_airport());
        assertEquals(100.00, test.get_price());
        assertEquals("Turku", test.get_layovers().get(0));
        assertEquals("2024-10-20 00:30:00 EEST", data.date_to_string(test.get_departure_time()));
        assertEquals("2024-10-20 02:00:00 EEST", data.date_to_string(test.get_arrival_time()));
        assertTrue(test.is_overnight());
        assertFalse(test.is_often_delayed());
        
        Preferences pref = data.get_preferences();
        assertEquals(Currency.EURO, pref.get_currency());
        assertEquals(200.00, pref.get_max_price());
        assertEquals(1, pref.get_leyovers());
        
    }
}
