
package fi.tuni.java5.flightweatherapp.settingManagement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests Preferences currency and tempereature unit String validation
 * @author Hirvijärvi
 */
public class PreferencesTest {
    
    private Preferences pref;
    
    public PreferencesTest() {
        pref = new Preferences("EUR", "C", -1.0, -1);
    }

    @Test
    public void testGet_currency_validation() {
        assertFalse(pref.set_currency("Dollar"));
        assertTrue(pref.set_currency("USD"));
        assertThrows(IllegalArgumentException.class,() -> new Preferences("usd", "C", -1.0, -1));
    }

    @Test
    public void test_temp_unit_validation() {
        assertFalse(pref.set_temperature_unit("Celsius"));
        assertTrue(pref.set_temperature_unit("F"));
        assertThrows(IllegalArgumentException.class,() -> new Preferences("USD", "Celsius", -1.0, -1));
    }

    
}
