
package fi.tuni.java5.flightweatherapp.settingManagement;

import fi.tuni.java5.flightweatherapp.weatherAPI.WeatherAPICall;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests Preferences currency and tempereature unit String validation
 * @author Hirvijärvi
 */
public class PreferencesTest {
    
    private Preferences pref;
    
    public PreferencesTest() {
        pref = new Preferences("EUR", WeatherAPICall.metricUnit, -1.0, -1);
    }


    @Test
    public void testGet_currency_validation() {

        pref.set_currency("USD");
        assertEquals("USD", pref.get_currency());

        assertThrows(IllegalArgumentException.class, () -> new Preferences("usd", WeatherAPICall.metricUnit, -1.0, -1));
    }

    @Test
    public void test_temp_unit_validation() {
        pref.set_currency("metric");
        assertEquals("metric", pref.get_currency());

        assertThrows(IllegalArgumentException.class, () -> new Preferences("USD", "Celsius", -1.0, -1));
    }

    
}
