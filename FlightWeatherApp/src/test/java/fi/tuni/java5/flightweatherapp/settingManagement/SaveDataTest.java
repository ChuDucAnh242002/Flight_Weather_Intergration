
package fi.tuni.java5.flightweatherapp.settingManagement;


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
        
        
    }
}
